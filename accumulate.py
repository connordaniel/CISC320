readIn = input("")
k = open(readIn,'r')
lines = k.readlines()
lines = list(map(int, lines))
sum = 0
if lines[0] == -999:
    print("EMPTY")
amt = lines[0]
for i in range(1, amt):
    if lines[i] == -999:
        break
    elif lines[i] >= 0:
        sum += lines[i]
if sum == 0:
    print('EMPTY')
else:
    print(sum)