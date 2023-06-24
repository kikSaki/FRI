import risar
import random


from PyQt5.QtCore import *
from PyQt5.QtGui import *
from PyQt5.QtWidgets import *
from PyQt5.QtMultimedia import *

#barva ozadja
barva = risar.barva(192,192,192)
risar.barva_ozadja(barva)


"""
za hitrejšo verzijo je spodaj komentar
"""
a = risar.besedilo(10, risar.maxY//2, str("*hitrejša verzija ima nekaj z AUDIO"), barva = risar.rdeca, velikost = 40)
b = risar.besedilo(10, risar.maxY//2 + 40, str("komentar v kodi"), barva = risar.rdeca, velikost = 40)
risar.cakaj(2)
risar.odstrani(a)
risar.odstrani(b)


class Ovire:
    def __init__(self):
        self.x0 = random.randint(0,risar.maxX-1-80)
        self.y0 = 0
        self.x1 = random.randint(self.x0+30, self.x0+80)
        self.y1 = 25

    def postavi(self):
        self.ovira = risar.pravokotnik(self.x0, 0, self.x1, 25, poln = True, zaobljen = 5)

    def premakni(self):
        self.ovira.setPos(self.ovira.x(), self.ovira.y()+1)
    
    def odstrani(self):
        if self.ovira.y() > risar.maxY:
            risar.odstrani(self.ovira)
            return True

class Kolesar:
    def __init__(self):
        self.srca = 3
        self.zivljenja = risar.besedilo(risar.maxX-40, 5, str(self.srca), barva = risar.crna, velikost = 50)
        self.točke = 0
        self.hs = risar.besedilo(5, 5, str(self.točke), barva = risar.crna, velikost = 50)

    def spawn(self):
        self.kolesar = risar.slika(risar.maxX/2 - 20, risar.maxY-150, "kolesar.png")

    def levi_premik(self):
        if self.kolesar.x() != 0:
            self.kolesar.setPos(self.kolesar.x()-1, self.kolesar.y())

    def desni_premik(self):
        if self.kolesar.x() < risar.maxX-40:
            self.kolesar.setPos(self.kolesar.x()+1, self.kolesar.y())

    def širina_kolo(self):
        return self.kolesar.x(), self.kolesar.x() + self.kolesar.boundingRect().width()

    def višina_kolo(self):
        return self.kolesar.y(), self.kolesar.y() + self.kolesar.boundingRect().height()

    def st_tock(self, tock):
        self.točke += tock
        risar.odstrani(self.hs)
        self.hs = risar.besedilo(5, 5, str(self.točke), barva = risar.crna, velikost = 50)

    def izguba_srca(self):
        self.srca -= 1
        risar.odstrani(self.zivljenja)
        self.zivljenja = risar.besedilo(risar.maxX-40, 5, str(self.srca), barva = risar.crna, velikost = 50)
        if self.srca == 0:
            self.konec()

    def konec(self):
        if self.srca == 0:
            return True

class Nagrade:

    def __init__(self):
        self.mozne = [("flowers.png", 1), ("bottle.png", 1), ("stones.png", 2), ("grass.png", 3), ("walker.png", 4), ("scooter.png", 2)]
        self.izbrana = self.mozne[random.randint(0, len(self.mozne)-1)]

    def postavi_nagrado(self):
        self.nagrada = risar.slika(random.randint(0, risar.maxX-30), 0, self.izbrana[0])

    def premakni_nagrado(self):
        self.nagrada.setPos(self.nagrada.x(), self.nagrada.y()+1)

    def širina_nagrade(self):
        return self.nagrada.x(), self.nagrada.x() + self.nagrada.boundingRect().width()

    def višina_nagrade(self):
        return self.nagrada.y(), self.nagrada.y() + self.nagrada.boundingRect().height()

    def odstrani_nagrado(self):
        if self.nagrada.y() > risar.maxY:
            risar.odstrani(self.nagrada)
            return True
    
    def score(self):
        risar.odstrani(self.nagrada)
        return self.izbrana[-1]


class Muzika:
    def __init__(self):
        pass

    def background_loop(self):
        """
        self.playlist = QMediaPlaylist()
        self.url = QUrl.fromLocalFile("arcade.wav")
        self.playlist.addMedia(QMediaContent(self.url))
        self.playlist.setPlaybackMode(QMediaPlaylist.Loop)

        self.player = QMediaPlayer()
        self.player.setPlaylist(self.playlist)
        self.player.setVolume(15)
        self.player.play()
        """
        #POSPEŠENA VEZIJA ^^^ ne vem tocno zakaj
        #QMediaPlayer nekaj naredu, da se vse premika hitreje
 
        self.audio = QSoundEffect()
        self.audio.setSource(QUrl.fromLocalFile("arcade.wav"))
        self.audio.setLoopCount(QSoundEffect.Infinite)
        self.audio.setVolume(0.25)
        self.audio.play()
        
    def ustavi_loop(self):
        self.audio.stop()
        #self.player.stop()  
        
        #^^za pospešeno verzijo je to za stop


    def kaching(self):
        self.plus = QSoundEffect()
        self.plus.setSource(QUrl.fromLocalFile("jump.wav"))
        self.plus.setVolume(1)
    
    def play_kaching(self):
        self.plus.play()

    def rebra(self):
        self.polomljena_rebra = QSoundEffect()
        self.polomljena_rebra.setSource(QUrl.fromLocalFile("explosion.wav"))
        self.polomljena_rebra.setVolume(1)
    
    def play_rebra(self):
        self.polomljena_rebra.play()


loop = Muzika()
loop.background_loop()


loop.rebra()
loop.kaching()


kolo = Kolesar()
kolo.spawn()

seznam_ovir = []
seznam_nagrad = []


while True:

    if risar.levo() == True:
        kolo.levi_premik()
    elif risar.desno() == True:
        kolo.desni_premik()

    if len(seznam_ovir) < 20 and random.random() < 0.02:
        a = Ovire()
        a.postavi()
        seznam_ovir.append(a)

    if len(seznam_nagrad) < 7 and random.random() < 0.02:
        b = Nagrade()
        b.postavi_nagrado()
        seznam_nagrad.append(b)

    for i in seznam_ovir:
        i.premakni()
        if i.odstrani():
            seznam_ovir.remove(i)

        if ((kolo.širina_kolo()[-1]-13) >= i.ovira.x() and (kolo.širina_kolo()[0]+13) <= i.x1) and (i.ovira.y() == (kolo.višina_kolo()[-1]-46)):
            kolo.izguba_srca()
            if kolo.konec():
                break
            loop.play_rebra()
            risar.cakaj(0.2)
  
    for i in seznam_nagrad:
        i.premakni_nagrado()
        if i.odstrani_nagrado():
            seznam_nagrad.remove(i)

        if (kolo.širina_kolo()[-1] >= i.širina_nagrade()[0] and kolo.širina_kolo()[0] <= i.širina_nagrade()[-1]) and (i.višina_nagrade()[0] >= kolo.višina_kolo()[0] and i.višina_nagrade()[0] <= kolo.višina_kolo()[-1]):
            k = i.score()
            seznam_nagrad.remove(i)
            kolo.st_tock(k)
            loop.play_kaching()

    risar.cakaj(0.002)
    
    if kolo.konec() == True:
        loop.ustavi_loop()
        loop.play_rebra()
        kolo.st_tock(0)
        break

risar.stoj()




