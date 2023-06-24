def stevilo_ovir(ovire):
    return len(ovire)

def dolzina_ovir(ovire):
    dolzina = 0
    for i in ovire:
        dolzina += i[1] - i[0] + 1
    return dolzina

def sirina(ovire):
    x2 = 0 
    for i in ovire:
        if i[1] > x2:
            x2 = i[1]
    return x2

def pretvori_vrstico(vrstica):
    vrstica += "."
    tabela_x1 = []
    tabela_x2 = []
    x1 = 0
    x2 = 0
    tupl = []
    for i in range(len(vrstica)):
        if vrstica[i] == "#":
            if i == 0:
                x1 = i + 1
                tabela_x1.append(x1)
            elif vrstica[i-1] == ".":
                x1 = i + 1
                tabela_x1.append(x1)                
            if vrstica[i+1] == ".":
                x2 = i+1
                tabela_x2.append(x2)               
    vrstica = vrstica[:-1]
    for prvi,drugi in zip(tabela_x1, tabela_x2):
        tupl.append((prvi,drugi))
    return tupl

def dodaj_vrstico(bloki, y):
    nova = []
    for i in bloki:
        nova.append((i[0],i[1],y))
    return nova

def pretvori_zemljevid(zemljevid):
    ovire = []
    for i in range(len(zemljevid)):
        if pretvori_vrstico(zemljevid[i]):
            ovire += (dodaj_vrstico(pretvori_vrstico(zemljevid[i]),i+1))
    return ovire

def globina(ovire, x):
    y = 0    
    for k in ovire:
        if k[-1] > y:
            y = k[-1] + 1
    for i in ovire:
            if x >= i[0] and x <= i[1]:
                if i[-1] < y:
                    y = i[-1]
    if y == 10:
        return None
    else:
        return y

def naj_stolpec(ovire):
    t = 0
    x2 = sirina(ovire)
    št_stolpec = 0
    poz_y = 0
    vrstica = 0
    tabela = []
    y = 0
    for k in ovire:
        if k[-1] > y:
            y = k[-1]
            temp = y + 1

    for i in range(1, x2+1):
        y = temp
        t = 0
        for j in ovire:
            
            if i >= j[0] and i <= j[1]:

                if j[-1] < y:
                    vrstica = j[-1]
                    y = vrstica

            if y == temp:
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
        return (stolpec, None)
    else:
        return (št_stolpec, poz_y)

def senca(ovire):
    tabela = []
    x2 = sirina(ovire)
    for i in range(1,x2+1):
        for j in ovire:
            if i >= j[0] and i <= j[1]:
                neki = False
                break
            else:
                neki = True
        tabela.append(neki)
    return tabela


import unittest

ovire1 = [(1, 3, 6), (2, 4, 3), (4, 6, 7),
          (3, 4, 9), (6, 9, 5), (9, 10, 2), (9, 10, 8)]

ovire2 = [(1, 3, 6), (2, 4, 3), (4, 6, 7),
          (3, 4, 9), (9, 10, 2), (9, 10, 8)]

ovire3 = [(1, 3, 6), (2, 4, 3),
          (3, 4, 9), (9, 10, 2), (9, 10, 8)]


class Test(unittest.TestCase):
    def test_stevilo_ovir(self):
        self.assertEqual(7, stevilo_ovir(ovire1))
        self.assertEqual(6, stevilo_ovir(ovire2))
        self.assertEqual(0, stevilo_ovir([]))

    def test_dolzina_ovir(self):
        self.assertEqual(19, dolzina_ovir(ovire1))
        self.assertEqual(15, dolzina_ovir(ovire2))
        self.assertEqual(0, dolzina_ovir([]))

    def test_sirina(self):
        self.assertEqual(10, sirina(ovire1))
        self.assertEqual(9, sirina(ovire1[:-2]))
        self.assertEqual(6, sirina(ovire1[:-3]))
        self.assertEqual(3, sirina(ovire1[:1]))

    def test_pretvori_vrstico(self):
        self.assertEqual([(3, 5)], pretvori_vrstico("..###."))
        self.assertEqual([(3, 5), (7, 7)], pretvori_vrstico("..###.#."))
        self.assertEqual([(1, 2), (5, 7), (9, 9)], pretvori_vrstico("##..###.#."))
        self.assertEqual([(1, 1), (4, 6), (8, 8)], pretvori_vrstico("#..###.#."))
        self.assertEqual([(1, 1), (4, 6), (8, 8)], pretvori_vrstico("#..###.#"))
        self.assertEqual([], pretvori_vrstico("..."))
        self.assertEqual([], pretvori_vrstico(".."))
        self.assertEqual([], pretvori_vrstico("."))

    def test_dodaj_vrstico(self):
        self.assertEqual([(3, 4, 3), (6, 8, 3), (11, 11, 3)], dodaj_vrstico([(3, 4), (6, 8), (11, 11)], 3))

    def test_pretvori_zemljevid(self):
        zemljevid = [
            "......",
            "..##..",
            ".##.#.",
            "...###",
            "###.##",
        ]
        self.assertEqual([(3, 4, 2), (2, 3, 3), (5, 5, 3), (4, 6, 4), (1, 3, 5), (5, 6, 5)], pretvori_zemljevid(zemljevid))

        global pretvori_vrstico
        pretvori = pretvori_vrstico
        try:
            def pretvori_vrstico(vrstica):
                return [(i, i) for i, c in enumerate(vrstica) if c == "#"]
            self.assertEqual([(2, 2, 2), (3, 3, 2), (1, 1, 3), (2, 2, 3), (4, 4, 3), (3, 3, 4), (4, 4, 4),
                              (5, 5, 4), (0, 0, 5), (1, 1, 5), (2, 2, 5), (4, 4, 5), (5, 5, 5)],
                             pretvori_zemljevid(zemljevid),
                             "Funkcija pretvori_zemljevid naj kar lepo uporabi pretvori_vrstico")
        finally:
            pretvori_vrstico = pretvori

        global dodaj_vrstico
        dodaj = dodaj_vrstico
        try:
            def dodaj_vrstico(ovire, vrstica):
                return [(*o, 2 * vrstica) for o in ovire]

            self.assertEqual([(3, 4, 4), (2, 3, 6), (5, 5, 6), (4, 6, 8), (1, 3, 10), (5, 6, 10)],
                             pretvori_zemljevid(zemljevid),
                             "Funkcija pretvori_zemljevid naj kar lepo uporabi dodaj_vrstico")
        finally:
            dodaj_vrstico = dodaj

    def test_globina(self):
        self.assertEqual(3, globina(ovire1, 3))
        self.assertEqual(5, globina(ovire1, 6))
        self.assertEqual(7, globina(ovire2, 6))
        self.assertIsNone(globina(ovire3, 6))

    def test_naj_stolpec(self):
        self.assertEqual((5, 7), naj_stolpec(ovire1))
        self.assertEqual((7, None), naj_stolpec(ovire2))
        self.assertEqual((5, None), naj_stolpec(ovire3))

    def test_senca(self):
        self.assertEqual([False] * 10, senca(ovire1))
        self.assertEqual([False, False, False, False, False, False, True, True, False, False], senca(ovire2))
        self.assertEqual([False, False, False, False, True, True, True, True, False, False], senca(ovire3))
        self.assertEqual([False] * 6, senca(ovire2[:-3]))
        self.assertEqual([False] * 3, senca(ovire3[:1]))


if __name__ == "__main__":
    unittest.main()