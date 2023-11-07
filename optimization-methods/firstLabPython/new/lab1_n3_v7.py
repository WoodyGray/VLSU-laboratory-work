
# Вариант 7

def maximize_funding(num_types, total_funding_range, costs, effects, min_counts, max_counts):
    print("p 9-10")
    m = num_types
    table = [[0] * (total_funding_range + 1) for _ in range(m + 1)]
    global x1, y1
    x1 = []
    y1 = []

    for i in range(1, m + 1):
        for j in range(1, total_funding_range + 1):
            for k in range(min_counts[i - 1], min(j // costs[i - 1], max_counts[i - 1]) + 1):
                table[i][j] = max(table[i][j], k * effects[i - 1] + table[i - 1][j - k * costs[i - 1]])

    for Y in range(60, total_funding_range+1):
        max_funding = table[m][Y]
        print()
        print(f"Y = {Y}: F: {max_funding}")
        y1.append(int(max_funding))
        x1.append(int(Y))
        funding_types = [0] * m
        i = m
        j = Y

        while i > 0 and j > 0:
            for k in range(min_counts[i - 1], min(j // costs[i - 1], max_counts[i - 1]) + 1):
                if table[i][j] == k * effects[i - 1] + table[i - 1][j - k * costs[i - 1]]:
                    funding_types[i - 1] = k
                    j -= k * costs[i - 1]
                    break
            i -= 1

        for i in range(m):
            print(f"x{i + 1}: {funding_types[i]}", end="; ")
        print()

    return table[m][total_funding_range]


# Исходные данные
num_types = 4
total_funding_range = 70
costs = [5, 6, 8, 9]
effects = [50, 70, 60, 79]
min_counts = [2, 1, 1, 1]
max_counts = [4, 2, 2, 3]

# Вызываем функцию для решения задачи
max_funding = maximize_funding(num_types, total_funding_range, costs, effects, min_counts, max_counts)

print('-'*30)
print(f"max F: {max_funding}")


def maximize_funding(num_types, total_funding_range, costs, effects, min_counts, max_counts):
    print()
    print("p 11")
    m = num_types
    table = [[0] * (total_funding_range + 1) for _ in range(m + 1)]
    global x2, y2
    x2 = []
    y2 = []

    for i in range(1, m + 1):
        for j in range(1, total_funding_range + 1):
            for k in range(min_counts[i - 1], min(j // costs[i - 1], max_counts[i - 1]) + 1):
                table[i][j] = max(table[i][j], k * effects[i - 1] + table[i - 1][j - k * costs[i - 1]])

    for Y in range(60, total_funding_range + 1):
        max_funding = table[m][Y]
        print()
        print(f"Y = {Y}: F: {max_funding}")
        y2.append(int(max_funding))
        x2.append(int(Y))
        funding_types = [0] * m
        i = m
        j = Y

        while i > 0 and j > 0:
            for k in range(min_counts[i - 1], min(j // costs[i - 1], max_counts[i - 1]) + 1):
                if table[i][j] == k * effects[i - 1] + table[i - 1][j - k * costs[i - 1]]:
                    funding_types[i - 1] = k
                    j -= k * costs[i - 1]
                    break
            i -= 1

        for i in range(m):
            print(f"x{i + 1}: {funding_types[i]}", end="; ")
        print()

    return table[m][total_funding_range]


# Исходные данные
num_types = 4
total_funding_range = 70
costs = [5, 6, 8, 9]
effects = [50, 70, 60, 79]
min_counts = [2, 1, 1, 1]
max_counts = [4, 2, 2, 3]

# Изменение минимального количества СрЗИ каждого типа
min_counts_changed = [count + 1 for count in min_counts]

# Вызываем функцию для решения задачи с измененными данными
max_funding = maximize_funding(num_types, total_funding_range, costs, effects, min_counts_changed, max_counts)

print('-'*30)
print(f"max F: {max_funding}")


global x1, y1, x2, y2

print(x1, y2)
print(x2, y2)




