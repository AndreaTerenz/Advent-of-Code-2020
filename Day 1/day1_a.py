import bisect

numbers = []

with open("Day 1/input.txt", "r") as f:
    numbers = [int(l) for l in f]

numbers.sort()

for count in range(0, len(numbers)-1):
    n1 = numbers[count]
    target = 2020 - n1

    i = -1
    try:
        i = numbers[count+1:].index(target)
    except ValueError:
        pass
    else:
         print(n1 * target)