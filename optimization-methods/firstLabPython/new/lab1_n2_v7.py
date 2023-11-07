#import matplotlib.pyplot as plt

# Вариант 7

wfd = open("result(p5-7).txt", "w")


def maximize_funding(num_types, total_funding_range, costs, effects, max_counts):
    print("p 5-6")
    wfd.write("p 5-6\n")
    m = num_types
    table = [[0] * (total_funding_range + 1) for _ in range(m + 1)]
    global x1, y1
    x1 = []
    y1 = []

    for i in range(1, m + 1):
        for j in range(1, total_funding_range + 1):
            for k in range(min(j // costs[i - 1], max_counts[i - 1]) + 1):
                table[i][j] = max(table[i][j], k * effects[i - 1] + table[i - 1][j - k * costs[i - 1]])

    for Y in range(22, total_funding_range + 1):
        max_funding = table[m][Y]
        print()
        print(f"Y = {Y}: F: {max_funding}")
        wfd.write(f"\nY = {Y}: F: {max_funding}\n")
        y1.append(int(max_funding))
        x1.append(int(Y))
        funding_types = [0] * m
        i = m
        j = Y

        while i > 0 and j > 0:
            for k in range(min(j // costs[i - 1], max_counts[i - 1]), -1, -1):
                if table[i][j] == k * effects[i - 1] + table[i - 1][j - k * costs[i - 1]]:
                    funding_types[i - 1] = k
                    j -= k * costs[i - 1]
                    break
            i -= 1

        for i in range(m):
            print(f"x{i + 1}: {funding_types[i]}", end="; ")
            wfd.write(f"x{i + 1}: {funding_types[i]}; ")
        print()
        wfd.write("\n")

    return table[m][total_funding_range]


# Исходные данные
num_types = 4
total_funding_range = 38
costs = [4, 5, 8, 9]
effects = [55, 75, 65, 69]
max_counts = [2, 2, 2, 3]

# Вызываем функцию для решения задачи
max_funding = maximize_funding(num_types, total_funding_range, costs, effects, max_counts)

print('-'*30)
print(f"max F: {max_funding}")
wfd.write("-"*30 + "\n")
wfd.write(f"max F: {max_funding}\n")


def maximize_funding(num_types, total_funding_range, costs, effects, max_counts):
    print()
    print()
    print()
    print("p 7 - bi -= 1")
    wfd.write("\n\n\np 7 - bi -= 1\n")
    m = num_types
    table = [[0] * (total_funding_range + 1) for _ in range(m + 1)]
    global x2, y2
    x2 = []
    y2 = []

    for i in range(1, m + 1):
        for j in range(1, total_funding_range + 1):
            for k in range(min(j // costs[i - 1], max_counts[i - 1]) + 1):
                table[i][j] = max(table[i][j], k * effects[i - 1] + table[i - 1][j - k * costs[i - 1]])

    for Y in range(22, total_funding_range + 1):
        max_funding = table[m][Y]
        print()
        print(f"Y = {Y}: F: {max_funding}")
        wfd.write(f"\nY = {Y}: F: {max_funding}\n")
        y2.append(int(max_funding))
        x2.append(int(Y))
        funding_types = [0] * m
        i = m
        j = Y

        while i > 0 and j > 0:
            for k in range(min(j // costs[i - 1], max_counts[i - 1]), -1, -1):
                if table[i][j] == k * effects[i - 1] + table[i - 1][j - k * costs[i - 1]]:
                    funding_types[i - 1] = k
                    j -= k * costs[i - 1]
                    break
            i -= 1

        for i in range(m):
            print(f"x{i + 1}: {funding_types[i]}", end="; ")
            wfd.write(f"x{i + 1}: {funding_types[i]}; ")
        print()
        wfd.write("\n")

    return table[m][total_funding_range]


# Исходные данные
num_types = 4
total_funding_range = 44
costs = [5, 6, 8, 9]
effects = [50, 70, 60, 79]
max_counts = [4, 2, 2, 3]

# Применяем изменения в максимальном количестве СрЗИ каждого типа
for i in range(num_types):
    max_counts[i] -= 1

# Вызываем функцию для решения задачи
max_funding = maximize_funding(num_types, total_funding_range, costs, effects, max_counts)

print('-'*30)
print(f"max F: {max_funding}")
wfd.write("-"*30+"\n")
wfd.write(f"max F: {max_funding}\n")


def maximize_funding(num_types, total_funding_range, costs, effects, max_counts):
    print()
    print()
    print()
    print("p 7 - bi += 1")
    wfd.write("\n\n\np 7 - bi += 1\n")
    m = num_types
    table = [[0] * (total_funding_range + 1) for _ in range(m + 1)]
    global x3, y3
    x3 = []
    y3 = []

    for i in range(1, m + 1):
        for j in range(1, total_funding_range + 1):
            for k in range(min(j // costs[i - 1], max_counts[i - 1]) + 1):
                table[i][j] = max(table[i][j], k * effects[i - 1] + table[i - 1][j - k * costs[i - 1]])

    for Y in range(22, total_funding_range + 1):
        max_funding = table[m][Y]
        print()
        print(f"Y = {Y}: F: {max_funding}")
        wfd.write(f"\nY = {Y}: F: {max_funding}\n")
        y3.append(int(max_funding))
        x3.append(int(Y))
        funding_types = [0] * m
        i = m
        j = Y

        while i > 0 and j > 0:
            for k in range(min(j // costs[i - 1], max_counts[i - 1]), -1, -1):
                if table[i][j] == k * effects[i - 1] + table[i - 1][j - k * costs[i - 1]]:
                    funding_types[i - 1] = k
                    j -= k * costs[i - 1]
                    break
            i -= 1

        for i in range(m):
            print(f"x{i + 1}: {funding_types[i]}", end="; ")
            wfd.write(f"x{i + 1}: {funding_types[i]}; ")
        print()
        wfd.write("\n")

    return table[m][total_funding_range]


# Исходные данные
num_types = 4
total_funding_range = 44
costs = [5, 6, 8, 9]
effects = [50, 70, 60, 79]
max_counts = [4, 2, 2, 3]

# Применяем изменения в максимальном количестве СрЗИ каждого типа
for i in range(num_types):
    max_counts[i] += 1

# Вызываем функцию для решения задачи
max_funding = maximize_funding(num_types, total_funding_range, costs, effects, max_counts)

print('-'*30)
print(f"max F: {max_funding}")
wfd.write('-'*30 + "\n")
wfd.write(f"max F: {max_funding}\n")

wfd.close()


global x3, y3, x1, y1, x2, y2
#plt.plot(x3, y3)
#print(x3, y3)
#plt.plot(x2, y2)
#print(x2, y2)
#plt.plot(x1, y1)
#print(x1, y1)
#plt.xlabel("Y")
#plt.ylabel("Объем финансирования")
#plt.show()

