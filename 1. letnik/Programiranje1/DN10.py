def zapisi_ovire(ime_datoteke, ovire):
    with open(ime_datoteke, "w+") as datoteka:

        for i in ovire:
            datoteka.write(f"{i:03}:")
            for j in ovire[i]:
                datoteka.write(f"{j[0]:>4}-{j[1]:<4}")
            datoteka.write("\n")
    datoteka.close()

def preberi_ovire(ime_datoteke):
    potrebni_slovar = dict()

    with open(ime_datoteke) as f:
        
        slovar = f.readlines()

        a = []
        b = []

        for i in range(len(slovar)):
            if slovar[i] != "\n":
                a.append(int(slovar[i]))

            else:
                for k in range(1,len(a)-1, 2):
                    b.append((a[k],a[k+1]))

                potrebni_slovar[a[0]] = b

                a = []
                b = []

            if slovar[i] == slovar[-1]:
                for k in range(1,len(a)-1, 2):
                    b.append((a[k],a[k+1]))
                    potrebni_slovar[a[0]] = b

    f.close()

    return potrebni_slovar


import os
import warnings
from random import randint
from datetime import datetime
import unittest


class TestZapis(unittest.TestCase):
    def setUp(self):
        warnings.simplefilter("ignore", ResourceWarning)

        self.ovire = {4: [(5, 6), (9, 11)],
                      5: [(9, 11), (19, 20), (30, 34)],
                      13: [(5, 8), (9, 11), (17, 19), (22, 25), (90, 100)]}

        self.ovire2 = self.ovire | {randint(100, 200): [(1, 2)]}
        with open("ovire.txt", "wt") as f:
            lf = "\n"
            f.write("\n\n".join(fr"{y}{lf}{lf.join(fr'{x0}{lf}{x1}' for x0, x1 in xs)}" for y, xs in self.ovire2.items()))

    def test_01_obvezna_zapisi_ovire(self):
        ime_datoteke = f"ovire{datetime.now().strftime('%m-%d-%H-%M-%S')}.txt"
        zapisi_ovire(ime_datoteke, self.ovire)
        with open(ime_datoteke) as f:
            self.assertEqual("""
004:   5-6      9-11
005:   9-11    19-20    30-34
013:   5-8      9-11    17-19    22-25    90-100
""".strip("\n"), "\n".join(map(str.rstrip, f)))

        self.ovire[101] = self.ovire[5]
        zapisi_ovire(ime_datoteke, self.ovire)
        with open(ime_datoteke) as f:
            self.assertEqual("""
004:   5-6      9-11
005:   9-11    19-20    30-34
013:   5-8      9-11    17-19    22-25    90-100
101:   9-11    19-20    30-34
""".strip("\n"), "\n".join(map(str.rstrip, f)))

        os.remove(ime_datoteke)

    def test_02_dodatna_preberi_ovire(self):
        self.assertEqual(preberi_ovire("ovire.txt"), self.ovire2)


if __name__ == "__main__":
    unittest.main()
 