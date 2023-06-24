def vrstica(s):
    tabela = []
    ovire = []
    temp = []
    druga = s.split(": ")
    y = int(druga[0])
    tabela.extend(druga[1].split(", "))
    for i in tabela:
        temp = i.split("-")
        ovire.append((int(temp[0]),int(temp[1]),y))
    
    return ovire

def preberi(s):
    tabela = s.splitlines()
    ovire = []
    for i in tabela:
        ovire.extend(vrstica(i))

    return ovire

def intervali(xs):
    intervali = []
    for i in xs:
        niz = str(i[0]) + "-" + str(i[1])
        intervali.append(niz)
 
    return intervali

def zapisi_vrstico(y, xs):
    interval = intervali(xs)
    niz = str(y) + ": "
    for i in interval:
        if(i == interval[-1]):
            niz += i
            break
        niz += i + ", "
    
    return niz

def zapisi(ovire):
    nov = []
    nov.extend(ovire)
    nov.sort(key = lambda a: (a[-1], a[0]))
    y = nov[0][-1]
    tabela = []
    niz = ""
    for i in range(len(nov)):
        if nov[i][-1] != y:
            niz += zapisi_vrstico(y, tabela) +"\n"
            y = nov[i][-1]
            tabela = []
            tabela.append((nov[i][0],nov[i][1]))
        else:
            tabela.append((nov[i][0],nov[i][1]))
            if nov[i] == nov[-1]:
                niz += zapisi_vrstico(y, tabela)
    return niz


import unittest


class Obvezna(unittest.TestCase):
    def test_vrstica(self):
        self.assertEqual([(1, 3, 4), (5, 11, 4), (15, 33, 4)], vrstica("4: 1-3, 5-11, 15-33"))
        self.assertEqual([(989, 1300, 1234)], vrstica("1234: 989-1300"))

    def test_preberi(self):
        self.assertEqual([(5, 6, 4),
                          (90, 100, 13), (5, 8, 13), (9, 11, 13),
                          (9, 11, 5), (19, 20, 5), (30, 34, 5),
                          (9, 11, 4),
                          (22, 25, 13), (17, 19, 13)], preberi(
"""4: 5-6
13: 90-100, 5-8, 9-11
5: 9-11, 19-20, 30-34
4: 9-11
13:  22-25, 17-19
"""))

    def test_intervali(self):
        self.assertEqual(["6-10", "12-16", "20-22", "98-102"], intervali([(6, 10), (12, 16), (20, 22), (98, 102)]))

    def test_zapisi_vrstico(self):
        self.assertEqual("5: 6-10, 12-16", zapisi_vrstico(5, [(6, 10), (12, 16)]).rstrip("\n"))
        self.assertEqual("8: 6-10, 12-16, 20-22, 98-102", zapisi_vrstico(8, [(6, 10), (12, 16), (20, 22), (98, 102)]).rstrip("\n"))
        self.assertEqual("8: 6-10, 12-16, 20-22, 98-102", zapisi_vrstico(8, [(6, 10), (12, 16), (20, 22), (98, 102)]).rstrip("\n"))


class Dodatna(unittest.TestCase):
    def test_zapisi(self):
        ovire = [(5, 6, 4),
          (90, 100, 13), (5, 8, 13), (9, 11, 13),
          (9, 11, 5), (19, 20, 5), (30, 34, 5),
          (9, 11, 4),
          (22, 25, 13), (17, 19, 13)]
        kopija_ovir = ovire.copy()
        self.assertEqual("""4: 5-6, 9-11
5: 9-11, 19-20, 30-34
13: 5-8, 9-11, 17-19, 22-25, 90-100""", zapisi(ovire).rstrip("\n"))
        self.assertEqual(ovire, kopija_ovir)


if __name__ == "__main__":
    unittest.main()