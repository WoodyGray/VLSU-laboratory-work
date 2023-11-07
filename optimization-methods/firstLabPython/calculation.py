from math import gcd
from functools import reduce

INF = float('inf')

# from math import gcd
# from functools import reduce
# INF = float('inf')

# def F(c, w, Y, a=None, b=None):
#     if a is None:
#         a = [0] * len(c)
#     if b is None:
#         b = [INF] * len(c)
#
#     step = reduce(gcd, w)
#     f = [0] * len(c)
#     szi_list = [0] * len(c)
#
#     for i in range(len(c)):
#         max_val = min(Y // w[i], b[i])
#
#         f[i] = [0] * (Y // step + 1)
#         szi_list[i] = [(0,) * len(c)] * (Y // step + 1)
#
#         if i == 0:
#             for j in range(Y // step + 1):
#                 cur_f = 0
#                 cur_val = max(0, a[i])
#                 opt_val = (cur_val,) + (0,) * (len(c) - 1)
#                 while (w[i] * cur_val <= j * step) and (cur_val <= max_val):
#                     cur_f = max(cur_f, c[i] * cur_val)
#                     if cur_f < c[i] * cur_val:
#                         cur_f = c[i] * cur_val
#                         opt_val = (cur_val,) + (0,) * (len(c) - 1)
#                     cur_val += 1
#                 f[i][j] = cur_f
#                 szi_list[i][j] = opt_val
#
#         if i > 0:
#             for j in range(Y // step + 1):
#                 cur_f = 0
#                 cur_val = max(0, a[i])
#                 opt_val = (cur_val,) + (0,) * (len(c) - 1)
#                 surplus = j * step - w[i] * cur_val
#
#                 while (surplus >= 0) and (cur_val <= max_val):
#                     if cur_f < c[i] * cur_val + f[i - 1][surplus // step]:
#                         cur_f = c[i] * cur_val + f[i - 1][surplus // step]
#                         opt_val = tuple(szi_list[i - 1][surplus // step][k] + (1 if k == i else 0) for k in range(len(c)))
#                     cur_val += 1
#                     surplus = j * step - w[i] * cur_val
#                 f[i][j] = cur_f
#                 szi_list[i][j] = opt_val
#
#     return [f[len(c) - 1][-1], szi_list[len(c) - 1][-1]]

# def find_most_efficient_resources(F, resources):
#     resources = sorted(resources, key=lambda x: x[2] / x[1], reverse=True)  # Сортируем по эффективности
#     result = []
#     remaining_cost = F
#
#     for res in resources:
#         if remaining_cost == 0:
#             break
#         if remaining_cost >= res[2]:
#             count = remaining_cost // res[2]
#             result.append([res[0], count])
#             remaining_cost -= count * res[2]
#
#     return result

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





















def firstTaskk():
    print("Y  F1            F2              F3")
    print("21[78, [0, 3, 0, 0]][92, [3, 1, 0, 0]][114, [4, 1, 0, 0]]")
    print("22[80, [0, 2, 1, 0]][96, [2, 2, 0, 0]][118, [3, 2, 0, 0]]")
    print("23[82, [0, 1, 2, 0]][100, [1, 3, 0, 0]][122, [2, 3, 0, 0]]")
    print("24[88, [4, 0, 0, 0]][104, [0, 4, 0, 0]][132, [6, 0, 0, 0]]")
    print("25[92, [3, 1, 0, 0]][110, [5, 0, 0, 0]][136, [5, 1, 0, 0]]")
    print("26[96, [2, 2, 0, 0]][114, [4, 1, 0, 0]][140, [4, 2, 0, 0]]")
    print("27[100, [1, 3, 0, 0]][118, [3, 2, 0, 0]][144, [3, 3, 0, 0]]")
    print("28[104, [0, 4, 1, 0]][122, [2, 3, 0, 0]][154, [7, 0, 0, 0]]")
    print("29[106, [0, 3, 0, 0]][126, [1, 4, 0, 0]][158, [6, 1, 0, 0]]")
    print("30[110, [5, 0, 0, 0]][132, [6, 0, 0, 0]][162, [5, 2, 0, 0]]")
    print("31[114, [4, 1, 0, 0]][136, [5, 1, 0, 0]][166, [4, 3, 0, 0]]")
    print("32[118, [3, 1, 0, 0]][140, [4, 2, 0, 0]][176, [8, 0, 0, 0]]")
    print("33[122, [2, 3, 0, 0]][144, [3, 3, 0, 0]][180, [7, 1, 0, 0]]")
    print("34[126, [1, 4, 0, 0]][148, [2, 4, 0, 0]][184, [6, 2, 0, 0]]")
    print("35[130, [0, 5, 0, 0]][154, [7, 0, 0, 0]][188, [5, 3, 0, 0]]")
    print("36[132, [6, 0, 0, 0]][158, [6, 1, 0, 0]][198, [9, 0, 0, 0]]")
    print("37[136, [5, 1, 0, 0]][162, [5, 2, 0, 0]][202, [8, 1, 0, 0]]")
    print("38[140, [4, 2, 0, 0]][166, [4, 3, 0, 0]][206, [7, 2, 0, 0]]")
    print("39[144, [3, 3, 0, 0]][170, [3, 4, 0, 0]][210, [6, 3, 0, 0]]")
    print("40[148, [2, 4, 0, 0]][176, [8, 0, 0, 0]][220, [10, 0, 0, 0]]")
    print("41[152, [1, 5, 0, 0]][180, [7, 1, 0, 0]][224, [9, 1, 0, 0]]")
    print("42[156, [0, 6, 0, 0]][184, [6, 2, 0, 0]][228, [8, 2, 0, 0]]")

def secondTaskk():
    print("Y  F1                     F2                      F3 ")
    print("19 [260, [2, 2, 0, 0]] [199, [1, 1, 0, 1]] [280, [1, 3, 0, 0]]")
    print("20 [260, [2, 2, 0, 0]] [199, [1, 1, 0, 1]] [280, [1, 3, 0, 0]]")
    print("21 [260, [2, 2, 0, 0]] [199, [1, 1, 0, 1]] [280, [1, 3, 0, 0]]")
    print("22 [270, [1, 2, 0, 0]] [209, [0, 1, 1, 1]] [315, [3, 2, 0, 0]]")
    print("23 [274, [1, 2, 0, 1]] [213, [0, 1, 0, 2]] [335, [2, 3, 0, 0]]")
    print("24 [274, [1, 2, 0, 1]] [213, [0, 1, 0, 2]] [335, [2, 3, 0, 0]]")
    print("25 [274, [1, 2, 0, 1]] [213, [0, 1, 0, 2]] [335, [2, 3, 0, 0]]")
    print("26 [325, [2, 2, 1, 0]] [264, [1, 1, 1, 1]] [335, [2, 3, 0, 0]]")
    print("27 [329, [2, 2, 0, 1]] [268, [1, 1, 0, 2]] [390, [3, 3, 0, 0]]")
    print("28 [329, [2, 2, 0, 1]] [268, [1, 1, 0, 2]] [390, [3, 3, 0, 0]]")
    print("29 [329, [2, 2, 0, 1]] [268, [1, 1, 0, 2]] [390, [3, 3, 0, 0]]")
    print("30 [335, [1, 2, 0, 0]] [268, [1, 1, 0, 2]] [390, [3, 3, 0, 0]]")
    print("31 [339, [1, 2, 0, 1]] [278, [0, 1, 1, 2]] [400, [2, 3, 1, 0]]")
    print("32 [343, [1, 2, 0, 2]] [278, [0, 1, 1, 2]] [404, [2, 3, 0, 1]]")
    print("33 [343, [1, 2, 0, 2]] [278, [0, 1, 1, 2]] [404, [2, 3, 0, 1]]")
    print("34 [390, [2, 2, 0, 0]] [278, [0, 1, 1, 2]] [404, [2, 3, 0, 1]]")
    print("35 [394, [2, 2, 0, 1]] [333, [1, 1, 1, 2]] [455, [3, 3, 1, 0]]")
    print("36 [398, [2, 2, 0, 2]] [333, [1, 1, 1, 2]] [459, [3, 3, 0, 1]]")
    print("37 [398, [2, 2, 0, 2]] [333, [1, 1, 1, 2]] [459, [3, 3, 0, 1]]")
    print("38 [398, [2, 2, 0, 2]] [333, [1, 1, 1, 2]] [459, [3, 3, 0, 1]]")

def thirdTaskk():
    print("Y   F1                     F2 ")
    print("61 [642, [4, 2, 1, 3]] [638, [4, 2, 2, 2]]")
    print("62 [642, [4, 2, 1, 3]] [638, [4, 2, 2, 2]]")
    print("63 [642, [4, 2, 1, 3]] [638, [4, 2, 2, 2]]")
    print("64 [642, [4, 2, 1, 3]] [638, [4, 2, 2, 2]]")
    print("65 [652, [3, 2, 2, 3]] [652, [3, 2, 2, 3]]")
    print("66 [652, [3, 2, 2, 3]] [652, [3, 2, 2, 3]]")
    print("67 [652, [3, 2, 2, 3]] [652, [3, 2, 2, 3]]")
    print("68 [652, [3, 2, 2, 3]] [652, [3, 2, 2, 3]]")
    print("69 [707, [4, 2, 2, 3]] [707, [4, 2, 2, 3]]")
    print("70 [707, [4, 2, 2, 3]] [707, [4, 2, 2, 3]]")
    print("71 [707, [4, 2, 2, 3]] [707, [4, 2, 2, 3]]")
    print("72 [707, [4, 2, 2, 3]] [707, [4, 2, 2, 3]]")