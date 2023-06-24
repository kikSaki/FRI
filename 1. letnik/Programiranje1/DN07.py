
A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, R, S, T, U, V = "ABCDEFGHIJKLMNOPRSTUV"

zemljevid = {
    (A, B): "gravel trava",
    (A, V): "pešci lonci",
    (B, C): "bolt lonci",
    (B, V): "",
    (C, R): "stopnice pešci lonci",
    (D, F): "stopnice pešci",
    (D, R): "pešci",
    (E, I): "trava lonci",
    (F, G): "trava črepinje",
    (G, H): "črepinje pešci",
    (G, I): "avtocesta",
    (H, J): "robnik bolt",
    (I, M): "avtocesta",
    (I, P): "gravel",
    (I, R): "stopnice robnik",
    (J, K): "",
    (J, L): "gravel bolt",
    (K, M): "stopnice bolt",
    (L, M): "robnik pešci",
    (M, N): "rodeo",
    (N, P): "gravel",
    (O, P): "gravel",
    (P, S): "",
    (R, U): "trava pešci",
    (R, V): "pešci lonci",
    (S, T): "robnik trava",
    (T, U): "gravel trava",
    (U, V): "robnik lonci trava"
}

mali_zemljevid = {(A, B): "robnik bolt",
                  (A, C): "bolt rodeo pešci",
                  (C, D): ""}

veščine = {
    "stopnice": "Spust po stopnicah",
    "pešci": "Divjanje med pešci",
    "lonci": "Slalom med cvetličnimi lonci",
    "bolt": "Slalom med odvrženimi skiroji",
    "robnik": "Skok na robnik pločnika",
    "gravel": "Vožnja po razsutem makadamu",
    "trava": "Oranje zelenic parkov",
    "avtocesta": "Vožnja po avtocesti",
    "črepinje": "Vožnja po razbiti steklovini",
    "rodeo": "Vožnja po kolesarski poti skozi Črnuče"
}


def mnozica_vescin(s):
    b=s.split()
    a=set()
    for neki in b:
        a.add(veščine[neki])
    return a

def dvosmerni_zemljevid(zemljevid):
    nov = {}
    for kluc in zemljevid:
        nov[kluc] = mnozica_vescin(zemljevid[kluc])
        nov[(kluc[1],kluc[0])] = mnozica_vescin(zemljevid[kluc])
    return nov

def mozna_pot(pot, zemljevid):
    for i in range(len(pot)):
        if len(pot) == 1:
            aa = True
            break
        if i == len(pot)-1:
            if (pot[i-1], pot[i]) in zemljevid  or  (pot[i], pot[i-1]) in zemljevid:
                aa = True
                break
            elif (pot[i-1], pot[i]) not in zemljevid:
                aa = False
                break
        elif (pot[i], pot[i+1]) in zemljevid  or  (pot[i+1], pot[i]) in zemljevid:
            aa = True
        elif (pot[i], pot[i+1]) not in zemljevid:
            aa = False
            break
    return aa

def potrebne_vescine(pot, zemljevid):
    s = set()
    for i in range(len(pot)):
        if i == len(pot)-1:
            if (pot[i-1], pot[i]) in zemljevid:
                s = s | mnozica_vescin(zemljevid[pot[i-1], pot[i]])
            elif (pot[i], pot[i-1]) in zemljevid:
                s = s | mnozica_vescin(zemljevid[pot[i], pot[i-1]])
        elif (pot[i], pot[i+1]) in zemljevid:
            s = s | mnozica_vescin(zemljevid[pot[i], pot[i+1]])
        elif (pot[i+1], pot[i]) in zemljevid:
            s = s | mnozica_vescin(zemljevid[pot[i+1], pot[i]])
    return s

def nepotrebne_vescine(pot, zemljevid, vescine):
    return vescine - potrebne_vescine(pot, zemljevid)



def koncna_tocka(pot, zemljevid, vescine):
    for i in range(len(pot)):
        if i == len(pot)-1:
            if (pot[i-1], pot[i]) in zemljevid:
                potrebne = mnozica_vescin(zemljevid[pot[i-1], pot[i]])
                if (potrebne - vescine):
                    return (pot[i-1], potrebne - vescine)
                elif not (potrebne - vescine):
                    return (pot[i-1], potrebne - vescine)

            elif (pot[i], pot[i-1]) in zemljevid:
                potrebne = mnozica_vescin(zemljevid[pot[i], pot[i-1]])
                if (potrebne - vescine):
                    return (pot[i], potrebne - vescine)
                elif not (potrebne - vescine):
                    return (pot[i], potrebne - vescine)

        elif (pot[i], pot[i+1]) in zemljevid:
            potrebne = mnozica_vescin(zemljevid[pot[i], pot[i+1]])
            if (potrebne - vescine):
                return (pot[i], potrebne - vescine)

        elif (pot[i+1], pot[i]) in zemljevid:
            potrebne = mnozica_vescin(zemljevid[pot[i+1], pot[i]])
            if (potrebne - vescine):
                return (pot[i], potrebne - vescine)


import unittest
import ast


class TestObvezna(unittest.TestCase):
    def test_1_mnozica_vescin(self):
        class Visitor(ast.NodeVisitor):
            allowed_functions = set("mnozica_vescin dvosmerni_zemljevid mozna_pot potrebne_vescine "
                                    "nepotrebne_vescine koncna_tocka".split())

            @staticmethod
            def visit_ClassDef(node):
                if node.name not in ("TestObvezna", "TestDodatna"):
                    self.fail("Ni razloga, da bi tule definiral svoje razrede, ne? ({node.name})")

            def visit_FunctionDef(this, node):
                if node.name not in Visitor.allowed_functions:
                    self.fail(f"Dodatne funkcije so tokrat prepovedane: {node.name}")
                if node.name == "mnozica_vescin":
                    return this.generic_visit(node)

            @staticmethod
            def visit_If(node):
                if not isinstance(node.test.left, ast.Constant) or node.test.left.value != "__main__":
                    self.fail(f"Razmisli, kako napisati `mnozice_vescine` brez pogojnih stavkov")

            visit_IfDef = visit_If

            @staticmethod
            def visit_While(_):
                self.fail(f"Razmisli, kako napisati `mnozice_vescine` brez pogojnih stavkov oz. zanke while")

        with open(__file__, encoding="utf8") as f:
            tree = ast.parse(f.read(), "<string>")
            Visitor().generic_visit(tree)

        self.assertEqual({'Skok na robnik pločnika',
                          'Slalom med odvrženimi skiroji',
                          'Spust po stopnicah'},
                         mnozica_vescin("robnik bolt stopnice"))
        self.assertEqual({'Vožnja po kolesarski poti skozi Črnuče'},
                         mnozica_vescin("rodeo"))
        self.assertEqual(set(), mnozica_vescin(""))

    def test_2_dvosmerni_zemljevid(self):
        kopija = mali_zemljevid.copy()

        self.assertEqual({('A', 'B'): {'Slalom med odvrženimi skiroji', 'Skok na robnik pločnika'},
                          ('A', 'C'): {'Divjanje med pešci',
                                       'Slalom med odvrženimi skiroji',
                                       'Vožnja po kolesarski poti skozi Črnuče'},
                          ('B', 'A'): {'Slalom med odvrženimi skiroji', 'Skok na robnik pločnika'},
                          ('C', 'A'): {'Divjanje med pešci',
                                       'Slalom med odvrženimi skiroji',
                                       'Vožnja po kolesarski poti skozi Črnuče'},
                          ('C', 'D'): set(),
                          ('D', 'C'): set()},
                         dvosmerni_zemljevid(mali_zemljevid))
        self.assertEqual(mali_zemljevid, kopija, "Ne spreminjaj zemljevida, temveč sestavi novega!")

    def test_3_mozna_pot(self):
        self.assertTrue(mozna_pot("ACD", mali_zemljevid))
        self.assertTrue(mozna_pot("ABACD", mali_zemljevid))
        self.assertTrue(mozna_pot("AB", mali_zemljevid))
        self.assertFalse(mozna_pot("ABD", mali_zemljevid))

        self.assertTrue(mozna_pot("ABCRVRIEIPNM", zemljevid))
        self.assertTrue(mozna_pot("HJKMLJH", zemljevid))
        self.assertFalse(mozna_pot("AC", zemljevid))
        self.assertFalse(mozna_pot("ABCRVRIEPNM", zemljevid))
        self.assertTrue(mozna_pot("A", zemljevid))

    def test_4_potrebne_vescine(self):
        self.assertEqual({'Divjanje med pešci',
                          'Slalom med odvrženimi skiroji',
                          'Vožnja po kolesarski poti skozi Črnuče'},
                         potrebne_vescine("AC", mali_zemljevid))

        self.assertEqual({'Divjanje med pešci',
                          'Slalom med odvrženimi skiroji',
                          'Vožnja po kolesarski poti skozi Črnuče'},
                         potrebne_vescine("ACD", mali_zemljevid))

        self.assertEqual({'Divjanje med pešci',
                          'Skok na robnik pločnika',
                          'Slalom med odvrženimi skiroji',
                          'Vožnja po kolesarski poti skozi Črnuče'},
                         potrebne_vescine("ABACD", mali_zemljevid))

        self.assertEqual({'Skok na robnik pločnika',
                          'Spust po stopnicah',
                           'Vožnja po razsutem makadamu',
                          'Oranje zelenic parkov'},
                          potrebne_vescine("RIPSTUT", zemljevid))

        self.assertEqual({'Divjanje med pešci',
                          'Oranje zelenic parkov',
                          'Slalom med cvetličnimi lonci',
                          'Slalom med odvrženimi skiroji',
                          'Spust po stopnicah',
                          'Vožnja po razsutem makadamu'},
                         potrebne_vescine("ABCRVR", zemljevid))

        self.assertEqual({'Divjanje med pešci',
                          'Oranje zelenic parkov',
                          'Skok na robnik pločnika',
                          'Slalom med cvetličnimi lonci',
                          'Slalom med odvrženimi skiroji',
                          'Spust po stopnicah',
                          'Vožnja po kolesarski poti skozi Črnuče',
                          'Vožnja po razsutem makadamu'},
                         potrebne_vescine("ABCRVRIEIPNM", zemljevid))

        self.assertEqual({'Divjanje med pešci',
                          'Skok na robnik pločnika',
                          'Slalom med odvrženimi skiroji',
                          'Spust po stopnicah',
                          'Vožnja po razsutem makadamu'},
                         potrebne_vescine("HJKMLJH", zemljevid))

        self.assertEqual(set(), potrebne_vescine("BVBVBVB", zemljevid))

    def test_5_nepotrebne_vescine(self):
        vescine = {'Divjanje med pešci',
                   'Skok na robnik pločnika',
                   'Spust po stopnicah',
                   'Vožnja po razsutem makadamu',
                   'Slalom med odvrženimi skiroji',
                   'Vožnja po kolesarski poti skozi Črnuče'}
        kopija = vescine.copy()
        self.assertEqual({'Spust po stopnicah', 'Vožnja po razsutem makadamu'},
                         nepotrebne_vescine("ABACD", mali_zemljevid, vescine))
        self.assertEqual(vescine, kopija, "Se mi prav zdi, da je funkcija nepotrebne_vescine spremenila "
                                          "vrednost svojega argumenta `vescine`? Fail, fail!")

        vescine = {'Spust po stopnicah',
                   'Vožnja po razsutem makadamu',
                   'Slalom med odvrženimi skiroji',
                   'Vožnja po kolesarski poti skozi Črnuče'}
        self.assertEqual({'Spust po stopnicah', 'Slalom med odvrženimi skiroji'},
                         nepotrebne_vescine("IPNMNPO", zemljevid, vescine))

        vescine = {'Vožnja po razsutem makadamu',
                   'Vožnja po kolesarski poti skozi Črnuče'}
        self.assertEqual(set(), nepotrebne_vescine("IPNMNPO", zemljevid, vescine))


class TestDodatna(unittest.TestCase):
    def test_1_koncna_tocka(self):
        vescine = {'Divjanje med pešci',
                   'Skok na robnik pločnika',
                   'Slalom med odvrženimi skiroji',
                   'Spust po stopnicah',
                   'Vožnja po razsutem makadamu'}
        self.assertEqual(("H", {'Vožnja po razbiti steklovini'}), koncna_tocka("HJKMLJHGFD", zemljevid, vescine))
        self.assertEqual(("M", {'Vožnja po kolesarski poti skozi Črnuče'}), koncna_tocka("HJKMNPIG", zemljevid, vescine))
        self.assertEqual(("B", {'Slalom med cvetličnimi lonci', 'Slalom med odvrženimi skiroji'}),
                         koncna_tocka("ABCRVB", zemljevid, {"Vožnja po razsutem makadamu", "Oranje zelenic parkov"}))


if "__main__" == __name__:
    unittest.main()