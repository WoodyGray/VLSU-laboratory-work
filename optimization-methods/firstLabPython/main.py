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

# Пример использования функции
F = 100
resources_matrix = [
    [1, 6, 22],
    [2, 7, 26],
    [3, 8, 28],
    [4, 10, 33]
]

# 21 78 [0, 3, 0, 0]
result = find_most_efficient_resources(F, resources_matrix)
all_resources = set(res[0] for res in resources_matrix)

print(result)
# for res in all_resources:
#     found = False
#     for row in result:
#         if row[0] == res:
#             print(f"[{row[0]}, {row[1]}]")
#             found = True
#             break
#     if not found:
#         print(f"[{res}, 0]")
