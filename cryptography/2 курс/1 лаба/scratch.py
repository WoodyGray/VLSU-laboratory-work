import csv

def create_file(filename, lst):
    file = open('{}.csv'.format(filename), 'w', newline='')
    write = csv.writer(file, delimiter = ',', quotechar = '"')
    for bukva in lst:
        write.writerow(bukva)
    file.close()

b = input()
text = list(b)
print(len(b))
a = sorted(list(set(text)))
print(*a)
print(*a, sep='\n')
cnt_lst = []
cnt = 0
#ИЬЭЛЬЕТЦЫЭГМВЬЫГТЫЖЦЭЗЦЫЭГВЭИЖЫЭГКГВКЧЫЭГЦЫГЯГЯЫРЩМЧЭГЗМЯТВЯМЭВТБГЦЕЬЕТВЕФОЭЭГЯЫЖЦЭЦКЭГЛЫЬЫЖЙГКЫЕЦЦГТВЫКВГЯГЩЕЦЛЬЕЮВГЧЫЖЖЭГКГЯЭТЙГЩЭЦЙГЦЕЛЕЦМЦЭГНЫЬЫЩЫЛГЫНЖЕАЕЖТБГПЬБЕЦКЭСГЫЬМХКБГКГТВМЛЫСГЛЫИУВГИЫГСЫТВЫЯЫДГЛЬКЛЫСГЛЫСЕЦЩКЬЫЯГИЬЫЛЖБВКБСКГКГНЬМПУСКГАМВЛЕСКГПЫЬЫЩЕВУЧГЖМЗЦКЛЫЯ

for i in range(len(a)):
    for j in range(len(text)):
        if a[i] == text[j]:
            cnt += 1
    print(f'{a[i]} встречается {cnt} раз')
    cnt_lst.append(cnt)
    cnt = 0

print(cnt_lst)
create_file('kripta', a)
