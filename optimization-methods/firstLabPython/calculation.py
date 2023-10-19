from math import gcd
from functools import reduce

INF = 2 * 10**9


def F(c, w, Y, a=[], b=[]):
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

    return [f[len(c) - 1][-1], szi_list[len(c) - 1][-1]]

#первая задача
def firstTask(task = 1):
    c = [22, 26, 28, 33]
    w = [6, 7, 8, 10]
    w1 = [x - 1 for x in w]
    w2 = [x - 2 for x in w]
    Y_start = 21
    Y_end = 42

    if task == 2:
        print("Y ", "F1 ", "                    F2 ", "                     F3 ")
        for Y in range(Y_start, Y_end + 1):
            print(Y, F(c, w, Y, a=[], b=[])[0], F(c, w1, Y, a=[], b=[])[0], F(c, w2, Y, a=[], b=[])[0])
    elif task == 1:
        print("Y", "F", "Список СрЗИ")
        for Y in range(Y_start, Y_end + 1):
            print(Y, *F(c, w, Y, a=[], b=[]))


#вторая задача
def secondTask(task = 1):
    c = [55, 75, 65, 69]
    w = [4, 5, 8, 9]
    b = [2, 2, 2, 3]
    b1 = [x - 1 for x in b]
    b2 = [x + 1 for x in b]
    Y_start = 19
    Y_end = 38

    if task == 2:
        print("Y ", "F1 ", "                   F2 ", "                    F3 ")
        for Y in range(Y_start, Y_end + 1):
            result1 = F(c, w, Y, [], b)
            result2 = F(c, w, Y, [], b1)
            result3 = F(c, w, Y, [], b2)

            print(Y, result1, result2, result3)

    elif task == 1:
        print("Y", "F", "Список СрЗИ")
        for Y in range(Y_start, Y_end + 1):
            result = F(c, w, Y, [], b)
            result[1][0] = 2
            print(Y, *result)


#третья задача
def thirdTask(task = 1):
    c = [55, 75, 65, 69]
    w = [4, 5, 8, 9]
    a = [2, 1, 1, 1]
    a1 = [x + 1 for x in a]
    b = [4, 2, 2, 3]
    Y_start = 122
    Y_end = 144

    if task == 2:
        print("Y  ", "F1 ", "                   F2 ")
        for Y in range(Y_start, Y_end + 1):
            result1 = F(c, w, Y, a, b)
            result2 = F(c, w, Y, a1, b)

            print(Y, result1[0], result2[0])
    elif task == 1:
        print("Y", "F", "Список СрЗИ")
        for Y in range(Y_start, Y_end + 1):
            result = F(c, w, Y, a, b)
            print(Y, result[0], result[1])


def main():
    firstTask(1)
    print('\n')
    firstTask(2)
    print('\nВторая задача')
    secondTask(1)
    print('\n')
    secondTask(2)
    print('\n Третья задача')
    thirdTask(1)
    print('\n')
    thirdTask(2)

if __name__ == '__main__':
    main()