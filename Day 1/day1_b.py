import bisect

numbers = []

with open("Day 1/input.txt", "r") as f:
    numbers = [int(l) for l in f]

numbers.sort()

for i in range(0, len(numbers)-2):
    n1 = numbers[i]
    
    for j in range(i+1, len(numbers)-1):
        n2 = numbers[j]

        for n3 in numbers[j+1:]:
            if (n1+n2+n3 == 2020):
                print(n1*n2*n3)