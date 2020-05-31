from statistics import mode

import cv2
from keras.models import load_model
import numpy as np

from utils.datasets import get_labels
from utils.inference import detect_faces
from utils.inference import draw_text
from utils.inference import draw_bounding_box
from utils.inference import apply_offsets
from utils.inference import load_detection_model
from utils.preprocessor import preprocess_input

# parameters for loading data and images
detection_model_path = 'trained_models/detection_models/haarcascade_frontalface_default.xml'
emotion_model_path = 'trained_models/emotion_models/fer2013_mini_XCEPTION.107-0.66.hdf5'
emotion_labels = get_labels('fer2013')

# hyper-parameters for bounding boxes shape
frame_window = 10
emotion_offsets = (20, 40)

# loading models
face_detection = load_detection_model(detection_model_path)
emotion_classifier = load_model(emotion_model_path, compile=False)

# getting input model shapes for inference
emotion_target_size = emotion_classifier.input_shape[1:3]

# starting lists for calculating modes
emotion_window = []

# starting video streaming
# cv2.namedWindow('window_frame')
video_capture = cv2.VideoCapture("../data/video.mp4")

emotion_count = [0, 0, 0, 0, 0, 0, 0, 0]
# 0: 'angry', 1: 'disgust', 2: 'fear', 3: 'happy',
# 4: 'sad', 5: 'surprise', 6: 'neutral', 7: nothing

while True:
    bgr_image = video_capture.read()[1]
    if bgr_image is None:
        break
    else:
        height, width, channel = bgr_image.shape
        matrix = cv2.getRotationMatrix2D((width / 2, height / 2), 0, 1)
        bgr_image = cv2.warpAffine(bgr_image, matrix, (width, height))

    gray_image = cv2.cvtColor(bgr_image, cv2.COLOR_BGR2GRAY)
    rgb_image = cv2.cvtColor(bgr_image, cv2.COLOR_BGR2RGB)
    faces = detect_faces(face_detection, gray_image)

    if faces is None:
        emotion_count[7] += 1

    for face_coordinates in faces:

        x1, x2, y1, y2 = apply_offsets(face_coordinates, emotion_offsets)
        gray_face = gray_image[y1:y2, x1:x2]
        try:
            gray_face = cv2.resize(gray_face, (emotion_target_size))
        except:
            continue

        gray_face = preprocess_input(gray_face, True)
        gray_face = np.expand_dims(gray_face, 0)
        gray_face = np.expand_dims(gray_face, -1)
        emotion_prediction = emotion_classifier.predict(gray_face)
        emotion_probability = np.max(emotion_prediction)
        emotion_label_arg = np.argmax(emotion_prediction)
        emotion_text = emotion_labels[emotion_label_arg]
        emotion_window.append(emotion_text)

        if len(emotion_window) > frame_window:
            emotion_window.pop(0)
        try:
            emotion_mode = mode(emotion_window)
        except:
            continue

        if emotion_text == 'angry':
            color = emotion_probability * np.asarray((255, 0, 0))
            emotion_count[0] += 1
        elif emotion_text == 'sad':
            color = emotion_probability * np.asarray((0, 0, 255))
            emotion_count[4] += 1
        elif emotion_text == 'happy':
            color = emotion_probability * np.asarray((255, 255, 0))
            emotion_count[3] += 1
        elif emotion_text == 'surprise':
            color = emotion_probability * np.asarray((0, 255, 255))
            emotion_count[5] += 1
        elif emotion_text == 'disgust':
            color = emotion_probability * np.asarray((0, 255, 0))
            emotion_count[1] += 1
        elif emotion_text == 'fear':
            color = emotion_probability * np.asarray((0, 255, 0))
            emotion_count[2] += 1
        elif emotion_text == 'neutral':
            color = emotion_probability * np.asarray((0, 255, 0))
            emotion_count[6] += 1

        color = color.astype(int)
        color = color.tolist()

        draw_bounding_box(face_coordinates, rgb_image, color)
        draw_text(face_coordinates, rgb_image, emotion_mode,
                  color, 0, -45, 1, 1)

    bgr_image = cv2.cvtColor(rgb_image, cv2.COLOR_RGB2BGR)
#   cv2.imshow('window_frame', bgr_image)
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

video_capture.release()
cv2.destroyAllWindows()
print('angry : ', emotion_count[0], '\ndisgust : ', emotion_count[1], '\nfear : ', emotion_count[2],
      '\nhappy : ', emotion_count[3], '\nsad : ', emotion_count[4], '\nsurprise : ', emotion_count[5],
      '\nneutral : ', emotion_count[6], '\nnothing : ', emotion_count[7])
emotion_sum = 0
for i in emotion_count:
    emotion_sum = emotion_sum + i

bad_emotion = 0
bad_emotion = emotion_count[0] + emotion_count[1] + emotion_count[2] + emotion_count[4] + emotion_count[5]

emotion_ratio = bad_emotion / emotion_sum * 100

f = open("../data/" + "emotion.txt", 'w')
print(emotion_ratio)
if (emotion_ratio >= 0) and (emotion_ratio < 25):
    f.write("good")
    print('good')
elif (emotion_ratio >= 25) and (emotion_ratio < 50):
    f.write("warn")
    print('warn')
elif emotion_ratio >= 50:
    f.write("bad")
    print('bad')
f.close()

# 0: 'angry', 1: 'disgust', 2: 'fear', 3: 'happy',
# 4: 'sad', 5: 'surprise', 6: 'neutral', 7: nothing
