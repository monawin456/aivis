from chatterbot import ChatBot
from chatterbot import comparisons
from chatterbot import response_selection

# 챗봇 객체 생성
bot = ChatBot(
    'Terminal',

    storage_adapter='chatterbot.storage.SQLStorageAdapter',
    # storage_adapter = SQL
    logic_adapters=[
        {
            "import_path": "chatterbot.logic.BestMatch",
            # logic adaptor = Bestmatch adpator
            "statement_comparison_function": comparisons.JaccardSimilarity,
            # 유사도 비교방법은 jaccard index 사용
            "response_selection_method": response_selection.get_priority_response,
            # response_list 중 응답을 고르는 방법은 우선순위에 따라 응답
        }
    ],

    database_uri='sqlite:///database.db'
    # 사용하고자하는 db 경로 연결
)


fKeywords = open('../data/keywords.txt', 'r', encoding='UTF-8')
fQuestions = open('../data/questions.txt', 'w', encoding='UTF-8')

keywords = fKeywords.readlines()

for keyword in keywords:
    bot_response = bot.get_response(keyword)
    if not bot_response.text == 'error':
        fQuestions.write(bot_response.text)
        fQuestions.write('\n')

fQuestions.flush()

fKeywords.close()
fQuestions.close()
