ovire = [(1, 3, 7), (2, 4, 3), (6, 6, 7),(3, 4, 9), (6, 9, 5), (9, 10, 2), (9, 11, 8)]
x = 10

y = 0
x2 = 0

št_stolpec = 0
poz_y = 0
vrstica = 0

tabela = []

for i in ovire:
    if i[-1] > y:
        y = i[-1]
        višina = y + 1

for i in ovire:
    if i[1] > x2:
        x2 = i[1]
    if x >= i[0] and x <= i[1]:
        if i[-1] < y:
            y = i[-1]

print(f"(Dodatni del 1) Širina steze : {x2}")
#ne vem ali je nujno izpisati tudi širino steze
    
print(f"(Obvezni del) x: {x}    Vrstica s prvo oviro: {y}")

for i in range(1, x2+1):
    y = višina
    t = 0
    for j in ovire:
        
        if i >= j[0] and i <= j[1]:

            if j[-1] < y:
                vrstica = j[-1]
                y = vrstica

        if y == višina:
            stolpec = i
            t += 1
            if t == len(ovire):
                break 
    
    if t == len(ovire):
        break

    tabela.append(vrstica)

for i in range(len(tabela)):
    if poz_y < tabela[i]:
        poz_y = tabela[i]
        št_stolpec = i + 1

if t == len(ovire):
    print(f"(Dodatni del 2) {stolpec}, Zmaga!")
else:
    print(f"(Dodatni del 2) {št_stolpec}, {poz_y}")