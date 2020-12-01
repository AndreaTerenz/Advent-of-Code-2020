def main():
    numbers = []

    #read the list from the input file
    with open("Day 1/input.txt", "r") as f:
        numbers = [int(l) for l in f]

    #for each number in the list, excluding the last one
    #(because it would have already been tested previously)
    for i in range(0, len(numbers)-1):
        n = numbers[i]
        target = 2020 - n

        j = -1
        try:
            #use the index() function to find the target value
            j = numbers[i+1:].index(target)
        except ValueError:
            pass
        else:
            #if no exception has been raised, the value has been found
            return (n * target)

    return 0

if __name__ == '__main__':
    print(main())
    