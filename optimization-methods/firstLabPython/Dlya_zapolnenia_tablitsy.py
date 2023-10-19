C = 270

out = {}

for i in range(0, C + 1, 20):
    out[i] = 0

first = [0,
44,
81,
119,
155,
191,
227,
259,
294,
328

]

second = [0,
42,
71,
106,
135,
161,
176,
191,
201,
202

]

len_column = len(first)

for i in range(len_column):
    print('--------')
    max = 0
    for j in range(i + 1):
        if first[j] + second[i-j] > max:
            max = first[j] + second[i-j]
            print(first[j], second[i-j])
    out[i*20] = max

print(out.values())