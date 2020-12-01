def main():
    numbers = []

    #read the list from the input file
    with open("Day 1/input.txt", "r") as f:
        numbers = [int(l) for l in f]

    #WAY less elegant solution 
    #for each number except the last two...
    for i in range(0, len(numbers)-2):
        n1 = numbers[i]
        
        #for each successive number except the last...
        for j in range(i+1, len(numbers)-1):
            n2 = numbers[j]

            #for each successive number
            #check if n1+n2+n3 is 2020
            for n3 in numbers[j+1:]:
                if (n1+n2+n3 == 2020):
                    return (n1*n2*n3)
    
    return 0

if __name__ == '__main__':
    print(main())