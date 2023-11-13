from math import gcd
from functools import reduce
from main import *

INF = float('inf')


def find_most_efficient_resources(F, resources):
    resources = sorted(resources, key=lambda x: x[2] / x[1], reverse=True)  # Сортируем по эффективности
    result = []
    remaining_cost = F

    for res in resources:
        count = 0
        while remaining_cost >= res[2]:
            count += 1
            remaining_cost -= res[2]
        result.append([res[0], count])

    result.sort()
    secondResult = []

    for r in result:
        secondResult.append(r[1])
    return secondResult

def F(c, w, Y,index, a=[], b=[]):
    if a == []:
        a = [0] * len(c)
    if b == []:
        b = [INF] * len(c)

    step = reduce(gcd, w)
    f = [0] * len(c)
    szi_list = [0] * len(c)

    for i in range(len(c)):
        max_val = min(Y // w[i], b[i])

        f[i] = [0] * (Y // step + 1)
        szi_list[i] = [0] * (Y // step + 1)

        if i == 0:
            for j in range(Y // step + 1):
                cur_f = 0
                cur_val = max(0, a[i])
                opt_val = [cur_val]
                while (w[i] * cur_val <= j * step) and (cur_val <= max_val):
                    cur_f = max(cur_f, c[i] * cur_val)
                    if cur_f < c[i] * cur_val:
                        cur_f = c[i] * cur_val
                        opt_val = [cur_val]
                    cur_val += 1
                f[i][j] = cur_f
                szi_list[i][j] = opt_val


        if i > 0:
            for j in range(Y // step + 1):
                cur_f = 0
                cur_val = max(0, a[i])
                opt_val = [cur_val]
                surplus = j * step - w[i] * cur_val

                while (surplus >= 0) and (cur_val <= max_val):
                    if cur_f < c[i] * cur_val + f[i-1][surplus // step]:
                        cur_f = c[i] * cur_val + f[i-1][surplus // step]
                        opt_val = szi_list[i-1][surplus // step] + [cur_val]
                    cur_val += 1
                    surplus = j * step - w[i] * cur_val
                f[i][j] = cur_f

                szi_list[i][j] = opt_val

    # return [f[len(c) - 1][-1], szi_list[len(c) - 1][-1]]
    recourses = create(index, w, c)
    return [f[len(c) - 1][-1], find_most_efficient_resources(f[len(c) - 1][-1], recourses)]

def create(i, w, c):
    result = []
    for j in i:
        result.append([i[j-1], w[j-1], c[j-1]])

    return result

#первая задача
def firstTask(task = 1):
    c = [22, 26, 28, 33]
    w = [6, 7, 8, 10]
    i = [1, 2, 3, 4]

    w1 = [x - 1 for x in w]
    w2 = [x - 2 for x in w]
    Y_start = 21
    Y_end = 42

    if task == 2:
        print("Y ", "F1 ", "                    F2 ", "                     F3 ")

        for Y in range(Y_start, Y_end + 1):
            print(Y, F(c, w, Y,i, a=[], b=[]), F(c, w1, Y,i, a=[], b=[]), F(c, w2, Y,i, a=[], b=[]))
            # print(Y, F(c, w, Y, a=[], b=[])[0], F(c, w1, Y, a=[], b=[])[0], F(c, w2, Y, a=[], b=[])[0])
            # print(Y, F(c, w, Y), F(c, w1, Y), F(c, w2, Y))
    elif task == 1:
        print("Y", "F", "Список СрЗИ")
        for Y in range(Y_start, Y_end + 1):
            print(Y, F(c, w, Y,i, a=[], b=[]))
            # print(Y, *F(c, w, Y))


#вторая задача
def secondTask(task = 1):
    c = [55, 75, 65, 69]
    w = [4, 5, 8, 9]
    i = [1, 2, 3, 4]
    b = [2, 2, 2, 3]
    b1 = [x - 1 for x in b]
    b2 = [x + 1 for x in b]
    Y_start = 19
    Y_end = 38

    if task == 2:
        print("Y ", "F1 ", "                   F2 ", "                    F3 ")
        for Y in range(Y_start, Y_end + 1):
            result1 = F(c, w, Y,i, [], b)
            result2 = F(c, w, Y,i, [], b1)
            result3 = F(c, w, Y,i, [], b2)

            print(Y, result1, result2, result3)

    elif task == 1:
        print("Y", "F", "Список СрЗИ")
        for Y in range(Y_start, Y_end + 1):
            result = F(c, w, Y,i, [], b)
            print(Y, *result)


#третья задача
def thirdTask(task = 1):
    c = [55, 75, 65, 69]
    w = [4, 5, 8, 9]
    a = [2, 1, 1, 1]
    i = [1, 2, 3, 4]
    a1 = [x + 1 for x in a]
    b = [4, 2, 2, 3]
    Y_start = 61
    Y_end = 72

    if task == 2:
        print("Y  ", "F1 ", "                   F2 ")
        for Y in range(Y_start, Y_end + 1):
            result1 = F(c, w, Y,i, a, b)
            result2 = F(c, w, Y,i, a1, b)

            # print(Y, result1[0], result2[0])
            print(Y, result1, result2)
    elif task == 1:
        print("Y", "F", "Список СрЗИ")
        for Y in range(Y_start, Y_end + 1):
            result = F(c, w, Y,i, a, b)
            print(Y, result[0], result[1])


def main():
    #firstTask(1)
    print('\nПервая задача')
    print('\n')
    firstTaskk()
    print('\nВторая задача')
    # secondTask(1)
    print('\n')
    secondTaskk()

    print('\n Третья задача')
    print('\n')
    thirdTaskk()



if __name__ == '__main__':
    main()





















