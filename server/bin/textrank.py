#-*- coding:utf-8 -*-

from konlpy.tag import Kkma
from konlpy.tag import Okt
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.preprocessing import normalize
import numpy
import sys

class SentenceTokenizer(object):
    def __init__(self):
        #use Korean languege NLP API
        self.kkma = Kkma()
        self.okt = Okt()
        #set a stopword list
        self.stopwords = ['아', '야', '휴', '나', '우리', '저', '저희', '을', '를', '의', '에', '가',]

    # doing tokenization with " "
    def text2sentences(self, text):
        #devide text to sentence
        sentences = self.kkma.sentences(text)
        for idx in range(0, len(sentences)):
            if len(sentences[idx]) <= 10:
                sentences[idx-1] += (' ' + sentences[idx])
                sentences[idx] = ''
        return sentences

    # NLP processing
    # devide token with morpheme
    def get_nouns(self, sentences):
        nouns = []
        for sentence in sentences:
            if sentence is not '':
                nouns.append(' '.join([noun for noun in self.okt.nouns(str(sentence))if noun not in self.stopwords and len(noun) > 1]))
        return nouns

class GraphMatrix(object):
    def __init__(self):
        #make TF-IDF vector
        self.tfidf = TfidfVectorizer()
        # make count vector
        self.cnt_vec = CountVectorizer()
        self.graph_sentence = []

    #make TF-IDF modeling
    #make sentence_graph
    def build_sent_graph(self, sentence):
        tfidf_mat = self.tfidf.fit_transform(sentence).toarray()
        self.graph_sentence = numpy.dot(tfidf_mat, tfidf_mat.T)
        return self.graph_sentence

    # make word_graph
    def build_words_graph(self, sentence):
        cnt_vec_mat = normalize(self.cnt_vec.fit_transform(sentence).toarray().astype(float), axis=0)
        vocab = self.cnt_vec.vocabulary_
        return numpy.dot(cnt_vec_mat.T, cnt_vec_mat), {vocab[word] : word for word in vocab}

class Rank(object):
    # apply textrank algorithm
    def get_ranks(self, graph, d=0.85):
        A = graph
        matrix_size = A.shape[0]
        for id in range(matrix_size):
            A[id, id] = 0
            link_sum = numpy.sum(A[:,id])
            if link_sum != 0:
                    A[:, id] /= link_sum
            A[:, id] *= -d
            A[id, id] = 1
        B = (1-d) * numpy.ones((matrix_size, 1))
        ranks = numpy.linalg.solve(A, B) # 연립방정식 Ax = B
        #return the idx: weight dictionary
        return {idx: r[0] for idx, r in enumerate(ranks)}

class TextRank(object):
    def __init__(self, text):
        #create SentenceTokenizer object
        self.sent_tokenize = SentenceTokenizer()
        #doing tokenization with " "
        self.sentences = self.sent_tokenize.text2sentences(text)
        #NLP processing
        #devide token with morpheme
        self.nouns = self.sent_tokenize.get_nouns(self.sentences)
        # create GraphMatrix object
        # make TF-IDF vector and count vector
        self.graph_matrix = GraphMatrix()
        # make sentence_graph
        self.sent_graph = self.graph_matrix.build_sent_graph(self.nouns)
        # make word_graph
        self.words_graph, self.idx2word = self.graph_matrix.build_words_graph(self.nouns)
        # create Rank object
        self.rank = Rank()
        #apply textrank algorithm
        self.sent_rank_idx = self.rank.get_ranks(self.sent_graph)
        #sort by weight
        self.sorted_sent_rank_idx = sorted(self.sent_rank_idx, key=lambda k: self.sent_rank_idx[k], reverse=True) # 문장 정렬

    #sent_num is number of get important sentence count
    def summarize(self, sent_num=8):
        summary = []
        index=[]
        #get a high rank sentence
        for idx in self.sorted_sent_rank_idx[:sent_num]:
            index.append(idx)
        index.sort()
        for idx in index:
            summary.append(self.sentences[idx])
        return summary

    # word_num is number of get important keyword count
    def keywords(self, word_num=50):
        # create Rank object
        rank = Rank()
        # apply textrank algorithm for keyword
        rank_idx = rank.get_ranks(self.words_graph)
        # sort by weight
        sorted_rank_idx = sorted(rank_idx, key=lambda k: rank_idx[k], reverse=True)
        #get high rank keyword in this case 1~50
        keywords = []
        index=[]
        for idx in sorted_rank_idx[:word_num]:
            index.append(idx)
        for idx in index:
            keywords.append(self.idx2word[idx])
        return keywords

#open a data file
#opne a keyword file
#opne a summarize file
fData = open('./data/data.txt', 'r', encoding='UTF-8')
fKeywords = open('./data/keywords.txt', 'w', encoding='UTF-8')
fSummarize = open('./data/summarize.txt', 'w', encoding='UTF-8')

#read text in data file
data = fData.read()
#doing textrank
textrank = TextRank(data)

print('Keywords:') # for debug
for keyword in textrank.keywords():
    print(keyword) # for debug
    fKeywords.write(keyword)
    fKeywords.write('\n')
print() # for debug

print('Summarize:\n') # for debug
for row in textrank.summarize():
    print(row) # for debug
    fSummarize.write(row)
    fSummarize.write('\n')
print() # for debug

#flush buffer
fKeywords.flush()
fSummarize.flush()

#close file
fData.close()
fSummarize.close()
fKeywords.close()
