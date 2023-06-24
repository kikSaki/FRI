zemljevid = ['......', '..##..', '.##.#.', '...###', '###.##']

tabela_x1 = []
tabela_x2 = []
tabela_y = []
x1 = 0
x2 = 0
y = 0
tupl = []

for k in zemljevid:
    k += "."
    y += 1
    if "#" in k:        
        
        for i in range(len(k)):
            
            if k[i] == "#":
                
                if i == 0:
                    x1 = i + 1
                    tabela_x1.append(x1)
                    tabela_y.append(y)
                elif k[i-1] == ".":
                    x1 = i + 1
                    tabela_x1.append(x1)
                    tabela_y.append(y)
                    
                if k[i+1] == ".":
                    x2 = i+1
                    tabela_x2.append(x2)
    k = k[:-1]

for prvi,drugi,tretji in zip(tabela_x1, tabela_x2,tabela_y):
    tupl.append((prvi,drugi,tretji))

print(tupl)

#ovire = [(3, 4, 2), (2, 3, 3), (5, 5, 3), (4, 6, 4), (1, 3, 5), (5, 6, 5)]

#lahk bi sicer dal ze prej namest tabele tupl tabelo ovire
ovire = tupl



ovire.sort(key = lambda a: (a[-1], a[0]))
#print(ovire)

#širina steze (x2)
x2 = 0 
for i in ovire:
    if i[1] > x2:
        x2 = i[1]
#print(x2)

#dolžina steze (y)
y = 0
for k in ovire:
    if k[-1] > y:
        y = k[-1]
#print(y)

tabela = []
nov_zemljevid = []
for i in range(1, y+1):
    nov_zemljevid.append("")
    for prvi, drugi, tretji in ovire:
        if i == tretji:
            tabela.append(i)

for i in range(1, y+1):

    if i not in tabela:
        nov_zemljevid[i-1] += ("." * x2)
    for k in range(len(ovire)):  
        
        if i == ovire[k][-1]:

            if ovire[k-1][-1] == ovire[k][-1]:
                nov_zemljevid[i-1] += "." * (ovire[k][0] - ovire[k-1][1] - 1)
            else:
                nov_zemljevid[i-1] += "." * (ovire[k][0]-1)

            nov_zemljevid[i-1] += "#"*(ovire[k][1]-ovire[k][0]+1)

            if ovire[k] == ovire[-1]:
                if ovire[k][1] < x2:
                    nov_zemljevid[i-1] += "." * (x2-ovire[k][1])
                    
            elif ovire[k+1][-1] != i: 
                if ovire[k][1] < x2:
                    nov_zemljevid[i-1] += "." * (x2-ovire[k][1])

print(nov_zemljevid)