import yaml
from operator import eq
WINDOWS_LINE_ENDING = b'\r\n'
UNIX_LINE_ENDING = b'\n'


# ex) feedback_question('어떤 상을 받았습니까?', 0)


def feedback_question(input_text, p_or_m):
	# 	p_or_m plus OR minus		 0 = plus , 1 = minus

	base_text = "  - "
	input_text = base_text + input_text + '\n'

	check_first = 1
	conversations = ""
	word = []
	text = []
	priority_value = []
	temp_line = ""
	count = 0
	with open("./chatterbot_corpus/data/korean/test.yml", "r", encoding="utf-8") as stream:
		if check_first:
			conversations = stream.readline()
			check_first == 0
		# 인덱싱을 위해 말뭉치 데이터 토큰화
		while True:
			temp_line = stream.readline()
			if not temp_line: break

			if (count % 3) == 0:
				word.append(temp_line)
			elif (count % 3) == 1:
				text.append(temp_line)
			elif (count % 3) == 2:
				priority_value.append(temp_line)

			count = count + 1

	search_index = 0
	text_index = 0

	# 일치하는 텍스트 데이터의 인덱스 찾기
	for search_text in text:
		if input_text == search_text:
			text_index = search_index
		else:
			search_index = search_index + 1

	temp_pv = priority_value[text_index]
	temp_pv = temp_pv.replace(" - ", "")
	temp_pv = int(temp_pv)

	if p_or_m == 0:
		temp_pv = temp_pv + 1
		if temp_pv > 100:
			temp_pv = 100
	elif p_or_m == 1:
		temp_pv = temp_pv - 1
		if temp_pv < 0:
			temp_pv = 0

	temp_pv = str(temp_pv)
	priority_value[text_index] = '  - ' + temp_pv + '\n'

	write_string = ""
	write_string = conversations

	write_index = 0
	while True:
		if write_index < len(word):
			write_string = write_string + word[write_index]
			write_string = write_string + text[write_index]
			write_string = write_string + priority_value[write_index]
			write_index = write_index + 1
		else:
			break

	content2 = write_string
	with open("./chatterbot_corpus/data/korean/test.yml", 'w', encoding="utf-8") as open_file:
		open_file.write(content2)
