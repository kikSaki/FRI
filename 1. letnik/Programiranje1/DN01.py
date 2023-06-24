import math

g = 9.807 

v = int(input("Vpiši hitrost strela: "))
fi = int(input("Vpiši kot strela: "))

s = (v**2 * math.sin(2 * math.radians(fi))) / g

print(f"Krogla leti {s} metrov.")