import pyodbc
import numpy as np
import matplotlib.pyplot as plt
from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg

from tkinter import *
from tkinter.ttk import *


ConnectionString = 'DSN=employees;UID=pb;PWD=pbvaje'

c = pyodbc.connect(ConnectionString)

cursor = c.cursor()

cursor.execute("""SELECT d.dept_no, YEAR(e.from_date) AS letoOD, MONTH(e.from_date) AS mesecOD, COUNT(e.emp_no) AS steviloZaposlenih
                  FROM departments d JOIN dept_emp e USING(dept_no)
                  GROUP BY d.dept_no, letoOD, mesecOD
                  ;""")
hire = cursor.fetchall()


departments = {}


for x in hire:   

    if x[0] not in departments:
        plus = 0
        departments[x[0]] = {}

    if x[1] not in departments[x[0]]:
        departments[x[0]][x[1]] = {}

    for i in range(1,13):
        if i == x[2]:
            plus += x[-1]
            departments[x[0]][x[1]][x[2]] = plus


        elif i not in departments[x[0]][x[1]]:
            departments[x[0]][x[1]][i] = plus
        elif x[1] == 2002 and i >= 8:
            departments[x[0]][x[1]][i] = plus

cursor.execute("""SELECT d.dept_no, YEAR(e.to_date) AS letoDO, MONTH(e.to_date) AS mesecDO, COUNT(e.emp_no) AS steviloZaposlenih
                  FROM departments d JOIN dept_emp e USING(dept_no)
                  GROUP BY d.dept_no, letoDO, mesecDO
                  ;""")
fire = cursor.fetchall()

#print(fire)

skip = 0

leto = 1985

temp_minus = 0

for y in fire:

    if leto < y[1]:
        minus = 0
        minus += temp_minus
        leto += 1
        skip = 0
        #print(temp_minus)

    #if y[1] == 9999 and skip == 0:
       # for j in range(8,13):
           # departments[y[0]][2002][j] -= temp_minus
            #print(departments[y[0]][2002][j])
        #skip = 1

    if y[1] > 1985 and skip == 0 and y[1] != 9999:
        for j in range(1,13):
            departments[y[0]][y[1]][j] -= temp_minus
        skip = 1

    for i in range(y[2],13):

 
        if y[1] == 9999:
            minus = y[-1]
            for j in range(8,13):
                departments[y[0]][2002][j] -= minus
                #if departments[y[0]][2002][j] < 0:
                   # departments[y[0]][2002][j] = 0

            minus = 0
            leto = 1985
            temp_minus = 0
            break

        elif i == y[2]:
            temp_minus += y[-1]
            minus = y[-1]
            departments[y[0]][y[1]][y[2]] -= minus
        else:
            departments[y[0]][y[1]][i] -= minus

c.close()

#___________________________________________________________________________________________________________________


def dobiPodatke(slovar, depart):
    podatki = []

    for x in range(1,13):
        for i in slovar[depart]:
            podatki.append(slovar[depart][i][x])
    return podatki

def vsiPodatki(slovar):
    p = np.array(dobiPodatke(slovar, "d001"))
    d = np.array(dobiPodatke(slovar, "d002"))
    t = np.array(dobiPodatke(slovar, "d003"))
    c = np.array(dobiPodatke(slovar, "d004"))
    e = np.array(dobiPodatke(slovar, "d005"))
    s = np.array(dobiPodatke(slovar, "d006"))
    m = np.array(dobiPodatke(slovar, "d007"))
    o = np.array(dobiPodatke(slovar, "d008"))
    i = np.array(dobiPodatke(slovar, "d009"))
    
    return p+d+t+c+e+s+m+o+i


def vsi():
    fig = plt.figure(figsize=(12,12))
    ax1 = fig.add_subplot(111, projection='3d')

    _x = np.arange(1985, 2003)
    _y = np.arange(1,13)
    _xx, _yy = np.meshgrid(_x, _y)
    x, y = _xx.ravel(), _yy.ravel()

    width = depth = 1
    top =  vsiPodatki(departments)
    bottom = np.zeros_like(top)

    ax1.bar3d(x, y, bottom, width, depth, top, shade=True, color="pink")
    ax1.set_title("Zaposleni v podjetju")
    plt.show() 

def poskus(slovar, oddelek):
    fig = plt.figure(figsize=(12,12))
    ax1 = fig.add_subplot(111, projection='3d')

    _x = np.arange(1985, 2003)
    _y = np.arange(1,13)
    _xx, _yy = np.meshgrid(_x, _y)
    x, y = _xx.ravel(), _yy.ravel()

    width = depth = 1
    top = dobiPodatke(slovar, oddelek)
    bottom = np.zeros_like(top)

    ax1.bar3d(x, y, bottom, width, depth, top, shade=True, color="yellow")
    ax1.set_title("Zaposleni v " + oddelek)
    ax1.set_xlabel('Leta')
    ax1.set_ylabel('Meseci')
    ax1.set_zlabel('Št. zaposlenih')
    plt.show()



root = Tk()
root.geometry("800x400")


 
# Create label
l = Label(root, text = "Zaposleni v podjetju", font =
               ('calibri', 20, 'bold'),
                    borderwidth = '4')



style = Style()
 
style.configure('TButton', font =
               ('calibri', 20, 'bold'),
                    borderwidth = '4')

style.map('TButton', foreground = [('active', '!disabled', 'green')],
                     background = [('active', 'black')])
l.grid(row = 0, column = 3, pady = 10, padx = 100)

my_button = Button(root, text="d001", command= lambda: poskus(departments, "d001"))
my_button.grid(row = 1, column = 3, pady = 10, padx = 100)

my_button2 = Button(root, text="d002", command=lambda: poskus(departments, "d002"))
my_button2.grid(row = 2, column = 3, pady = 10, padx = 100)

my_button3 = Button(root, text="d003", command=lambda: poskus(departments, "d003"))
my_button3.grid(row = 3, column = 3, pady = 10, padx = 100)

my_button4 = Button(root, text="d004", command=lambda: poskus(departments, "d004"))
my_button4.grid(row = 4, column = 3, pady = 10, padx = 100)

my_button5 = Button(root, text="d005", command=lambda: poskus(departments, "d005"))
my_button5.grid(row = 5, column = 3, pady = 10, padx = 100)

my_button6 = Button(root, text="d006", command=lambda: poskus(departments, "d006"))
my_button6.grid(row = 1, column = 4, pady = 10, padx = 100)

my_button7 = Button(root, text="d007", command=lambda: poskus(departments, "d007"))
my_button7.grid(row = 2, column = 4, pady = 10, padx = 100)

my_button8 = Button(root, text="d008", command=lambda: poskus(departments, "d008"))
my_button8.grid(row = 3, column = 4, pady = 10, padx = 100)

my_button9 = Button(root, text="d009", command=lambda: poskus(departments, "d009"))
my_button9.grid(row = 4, column = 4, pady = 10, padx = 100)

my_buttonVse = Button(root, text="celo podjetje", command=vsi)
my_buttonVse.grid(row = 5, column = 4, pady = 10, padx = 100)


root.mainloop()