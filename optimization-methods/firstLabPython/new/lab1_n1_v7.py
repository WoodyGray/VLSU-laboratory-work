import matplotlib.pyplot as plt

# Вариант 7

wfd = open("result(p1-3).txt", "w")



def maximize_funding(num_types, total_funding_range, costs, effects):
    print("Пункт 1-2")
    wfd.write('p 1-2\n')
    num_types = len(costs)
    table = [[0] * (total_funding_range + 1) for _ in range(num_types + 1)]
    global x1, y1
    y1 = []
    x1 = []

    for i in range(1, num_types + 1):
        for j in range(1, total_funding_range + 1):
            if costs[i - 1] <= j:
                table[i][j] = max(table[i - 1][j], effects[i - 1] + table[i][j - costs[i - 1]])
            else:
                table[i][j] = table[i - 1][j]

    for Y in range(15, total_funding_range + 1):
        max_funding = table[num_types][Y]
        print()
        print(f"Y = {Y}: F: {max_funding}")
        wfd.write(f"\nY = {Y} F = {max_funding}\n")
        y1.append(int(max_funding))
        x1.append(int(Y))
        funding_types = [0] * num_types
        i = num_types
        j = Y
        while i > 0 and j > 0:
            if table[i][j] != table[i - 1][j]:
                funding_types[i - 1] += 1
                j -= costs[i - 1]
            else:
                i -= 1
        for i in range(num_types):
            print(f"x{i + 1}: {funding_types[i]}", end="; ")
            wfd.write(f"x{i+1}: {funding_types[i]}; ")
        print()
        wfd.write('\n')

    return table[num_types][total_funding_range]

# Исходные данные
num_types = 4
total_funding_range = 42
costs = [6, 7, 8, 10]
effects = [22, 26, 28, 33]

# Вызываем функцию для решения задачи
max_funding = maximize_funding(num_types, total_funding_range, costs, effects)
print('-'*30)
print(f"max F: {max_funding}")
print()
print('-'*60)
print()
wfd.write('-'*30)
wfd.write("\n")
wfd.write(f"max F: {max_funding}\n\n")
wfd.write('-'*60)
wfd.write('\n')

def maximize_funding(num_types, total_funding_range, costs, effects):
    print("Пункт 3")
    wfd.write("p 3\n")
    num_types = len(costs)
    table = [[0] * (total_funding_range + 1) for _ in range(num_types + 1)]
    global x2, y2
    x2 = []
    y2 = []

    for i in range(1, num_types + 1):
        for j in range(1, total_funding_range + 1):
            if costs[i - 1] <= j:
                table[i][j] = max(table[i - 1][j], effects[i - 1] + table[i][j - costs[i - 1]])
            else:
                table[i][j] = table[i - 1][j]

    for Y in range(15, total_funding_range + 1):
        max_funding = table[num_types][Y]
        print()
        print(f"Y = {Y}: F: {max_funding}")
        wfd.write(f"\nY = {Y} F = {max_funding}\n")
        y2.append(int(max_funding))
        x2.append(int(Y))
        funding_types = [0] * num_types
        i = num_types
        j = Y
        while i > 0 and j > 0:
            if table[i][j] != table[i - 1][j]:
                funding_types[i - 1] += 1
                j -= costs[i - 1]
            else:
                i -= 1
        for i in range(num_types):
            print(f"x{i + 1}: {funding_types[i]}", end="; ")
            wfd.write(f"x{i+1}: {funding_types[i]}; ")
        print()
        wfd.write('\n')

    return table[num_types][total_funding_range]

# Исходные данные
num_types = 5
total_funding_range = 30
costs = [5-1, 6-1, 8-1, 10-1, 11-1]
effects = [50, 59, 70, 71, 65]

# Вызываем функцию для решения задачи
max_funding = maximize_funding(num_types, total_funding_range, costs, effects)
print('-'*30)
print(f"max F: {max_funding}")
print()
wfd.write('-'*30)
wfd.write('\n')
wfd.write(f"max F: {max_funding}\n\n")


def maximize_funding(num_types, total_funding_range, costs, effects):
    print("Пункт 3")
    wfd.write('p 3')
    num_types = len(costs)
    table = [[0] * (total_funding_range + 1) for _ in range(num_types + 1)]
    global x3, y3
    x3 = []
    y3 = []

    for i in range(1, num_types + 1):
        for j in range(1, total_funding_range + 1):
            if costs[i - 1] <= j:
                table[i][j] = max(table[i - 1][j], effects[i - 1] + table[i][j - costs[i - 1]])
            else:
                table[i][j] = table[i - 1][j]

    for Y in range(15, total_funding_range + 1):
        max_funding = table[num_types][Y]
        print()
        print(f"Y = {Y}: F: {max_funding}")
        wfd.write(f"\nY = {Y} F = {max_funding}\n")
        y3.append(int(max_funding))
        x3.append(int(Y))
        funding_types = [0] * num_types
        i = num_types
        j = Y
        while i > 0 and j > 0:
            if table[i][j] != table[i - 1][j]:
                funding_types[i - 1] += 1
                j -= costs[i - 1]
            else:
                i -= 1
        for i in range(num_types):
            print(f"x{i + 1}: {funding_types[i]}", end="; ")
            wfd.write(f"x{i+1}: {funding_types[i]}; ")
        print()
        wfd.write('\n')

    return table[num_types][total_funding_range]

# Исходные данные
num_types = 5
total_funding_range = 30
costs = [5-2, 6-2, 8-2, 10-2, 11-2]
effects = [50, 59, 70, 71, 65]

# Вызываем функцию для решения задачи
max_funding = maximize_funding(num_types, total_funding_range, costs, effects)
print('-'*30)
print(f"max F: {max_funding}")
wfd.write('-'*30)
wfd.write('\n')
wfd.write(f"max F: {max_funding}\n\n")

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


print('p 1-2', x1, y1)
print('p 3 - wi - 1', x2, y2)
print('p 3 - wi - 2', x3, y3)


