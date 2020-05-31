import cv2
import numpy as np
import dlib
import sys
from math import hypot

#동영상 이름
cap = cv2.VideoCapture("test.mp4")
Vf = cap.get(cv2.CAP_PROP_FRAME_COUNT)
fps = cap.get(cv2.CAP_PROP_FPS)
Vlength = Vf/ fps

detector = dlib.get_frontal_face_detector()
predictor = dlib.shape_predictor("shape_predictor_68_face_landmarks.dat")

def midpoint(p1 ,p2):
    return int((p1.x + p2.x)/2), int((p1.y + p2.y)/2)

font = cv2.FONT_HERSHEY_PLAIN

def get_blinking_ratio(eye_points, facial_landmarks):
    left_point = (facial_landmarks.part(eye_points[0]).x, facial_landmarks.part(eye_points[0]).y)
    right_point = (facial_landmarks.part(eye_points[3]).x, facial_landmarks.part(eye_points[3]).y)
    center_top = midpoint(facial_landmarks.part(eye_points[1]), facial_landmarks.part(eye_points[2]))
    center_bottom = midpoint(facial_landmarks.part(eye_points[5]), facial_landmarks.part(eye_points[4]))

    #hor_line = cv2.line(frame, left_point, right_point, (0, 255, 0), 2)
    #ver_line = cv2.line(frame, center_top, center_bottom, (0, 255, 0), 2)

    hor_line_length = hypot((left_point[0] - right_point[0]), (left_point[1] - right_point[1]))
    ver_line_length = hypot((center_top[0] - center_bottom[0]), (center_top[1] - center_bottom[1]))

    ratio = hor_line_length / ver_line_length
    return ratio

def get_gaze_ratio(eye_points, facial_landmarks):
    # detect Gaze
    left_eye_region = np.array([(facial_landmarks.part(eye_points[0]).x, facial_landmarks.part(eye_points[0]).y),
                                (facial_landmarks.part(eye_points[1]).x, facial_landmarks.part(eye_points[1]).y),
                                (facial_landmarks.part(eye_points[2]).x, facial_landmarks.part(eye_points[2]).y),
                                (facial_landmarks.part(eye_points[3]).x, facial_landmarks.part(eye_points[3]).y),
                                (facial_landmarks.part(eye_points[4]).x, facial_landmarks.part(eye_points[4]).y),
                                (facial_landmarks.part(eye_points[5]).x, facial_landmarks.part(eye_points[5]).y)], np.int32)
    # cv2.polylines(frame, [left_eye_region], True, (0, 0 ,255), 2)

    height, width, _ = frame.shape
    mask = np.zeros((height, width), np.uint8)

    cv2.polylines(mask, [left_eye_region], True, 255, 2)
    cv2.fillPoly(mask, [left_eye_region], 255)
    eye = cv2.bitwise_and(gray, gray, mask=mask)

    min_x = np.min(left_eye_region[:, 0])
    max_x = np.max(left_eye_region[:, 0])
    min_y = np.min(left_eye_region[:, 1])
    max_y = np.max(left_eye_region[:, 1])

    # eye = frame[min_y: max_y, min_x: max_x]
    # gray_eye = cv2.cvtColor(eye, cv2.COLOR_BGR2GRAY)
    gray_eye = eye[min_y: max_y, min_x: max_x]
    _, threshold_eye = cv2.threshold(gray_eye, 70, 255, cv2.THRESH_BINARY)
    height, width = threshold_eye.shape
    left_side_threshold = threshold_eye[0: height, 0: int(width / 2)]
    left_side_white = cv2.countNonZero(left_side_threshold)

    right_side_threshold = threshold_eye[0: height, int(width / 2): width]
    right_side_white = cv2.countNonZero(right_side_threshold)

    if left_side_white == 0:
        gaze_ratio = 0
    elif right_side_white == 0:
        gaze_ratio = 5
    else:
        gaze_ratio = left_side_white / right_side_white

    return  gaze_ratio

no_center = 0
blink_count = 0
while (cap.isOpened()):
    ret, frame = cap.read()
    if ret == False:
        break

    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)


    faces = detector(gray)
    for face in faces:
        x, y = face.left(), face.top()
        x1, y1 = face.right(), face.bottom()
        #cv2.rectangle(frame, (x, y), (x1, y1), (0, 255, 0), 2)

        landmarks = predictor(gray, face)

        #detect Blinking
        left_eye_ratio = get_blinking_ratio([36,37,38,39,40,41], landmarks)
        right_eye_ratio = get_blinking_ratio([42,43,44,45,46,47], landmarks)
        blinking_eye_ratio = (left_eye_ratio + right_eye_ratio) / 2

        if blinking_eye_ratio > 5.65:
            blink_count = blink_count + 1

        #Gaze detect
        gaze_ratio_left_eye = get_gaze_ratio([36,37,38,39,40,41], landmarks)
        gaze_ratio_right_eye = get_gaze_ratio([42,43,44,45,46,47], landmarks)
        gaze_ratio = (gaze_ratio_left_eye + gaze_ratio_right_eye) / 2

        # showing
        new_frame = np.zeros((500, 500, 3), np.uint8)
        #cv2.putText(frame, str(gaze_ratio), (50, 100), font, 2, (0, 0, 255), 3)

        if 1 <= gaze_ratio <= 3:
            no_center = no_center + 0
            #cv2.putText(frame, "Center", (50, 150), font, 2, (0, 0, 255), 3)
        else:
            no_center = no_center + 1
            #cv2.putText(frame, "Others", (50, 200), font, 2, (0, 0, 255), 3)
            #cv2.putText(frame, str(no_center), (50, 150), font, 2, (0, 0, 255), 3)
            #cv2.putText(frame, str(int(no_center / 15)), (50, 200), font, 2, (0, 0, 255), 3)

        #cv2.putText(frame, str(right_side_white), (50, 150), font, 2, (0, 0, 255), 3)
        '''
        threshold_eye = cv2.resize(threshold_eye, None, fx=5, fy=5)
        eye = cv2.resize(gray_eye, None, fx = 5, fy = 5)
        #cv2.imshow("Eye", eye)
        cv2.imshow("Threshold", threshold_eye)
        cv2.imshow("left", left_side_threshold)
        cv2.imshow("right", right_side_threshold)
        #cv2.imshow("Left eye", left_eye)
        '''

    #cv2.imshow("Frame", frame)

    key = cv2.waitKey(1)
    if key == 27:
        break

no_center = no_center / fps
Vrank = no_center / Vlength
temp = sys.argv
#f = open("C:/Users/user/PycharmProjects/untitled/"+ temp + "_rank.txt",'w')

if Vrank > 0.5:
    #f.write("매우 나쁨")
    print("badest")
elif Vrank > 0.3:
    #f.write("나쁨")
    print("bad")
elif Vrank > 0.1:
    #f.write("보통")
    print("normal")
elif Vrank > 0.05:
    #f.write("좋음")
    print("good")
elif Vrank > 0.01:
    #f.write("매우 좋음")
    print("goodest")
#f.close()

cap.release()
cv2.destroyAllWindows()