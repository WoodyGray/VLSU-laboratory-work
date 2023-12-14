import nltk
from nltk import ngrams
from collections import Counter

text = "цзалтсущхварзтбпхбяк_хбызлушнвбэтуоцздфцтсашцмупкигузсакхиу_цхщцзъещйюуьезфкпгчцжржцивяюхгуьзсхынъщшхсдэгаубнобнннфктсещшюэкквхцрйфф_лэкоиубифупцоърхввщллхшыхпкцхуюмцлжжвбшзсмючяскхгвыиеьцщбухзфцщнмуыифюывхбфзсшухсющсвькэсящмрбфзтбьъияу"

bigrams = ngrams(text, 2)
trigrams = ngrams(text, 3)

bigrams_count = Counter(bigrams)
trigrams_count = Counter(trigrams)

top_10_bigrams = bigrams_count.most_common(10)
top_10_trigrams = trigrams_count.most_common(10)

print("10 самых частых биграмм:")
for bigram, count in top_10_bigrams:
    print(' '.join(bigram), count)

print("\n10 самых частых триграмм:")
for trigram, count in top_10_trigrams:
    print(' '.join(trigram), count)


'''
stroka = ""
for i in range(0, len(text), 4):
    stroka += text[i]
print(stroka)'''
