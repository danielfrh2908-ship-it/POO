class Animal:
    def sonido(self):
        print("El animal hace un sonido")

class Perro(Animal):
    def sonido(self):
        print("El perro ladra")

class Gato(Animal):
    def sonido(self):
        print("El gato maulla")

a1 = Perro()
a2 = Gato()

a1.sonido()
a2.sonido()
