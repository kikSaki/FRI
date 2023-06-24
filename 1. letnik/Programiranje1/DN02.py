
teža = int(input("Vnesi težo tovora: "))
koraki = 0
gorivo = 0

while True:
    teža = teža // 3 - 2 
    
    koraki += 1

    if teža <= 0:
        break   

    gorivo += teža

print(f"Goriva je potrebnega {koraki} ton (po obveznem delu) ")
print(f"Goriva je potrebnega {gorivo} ton (po dodatnem delu) ")


