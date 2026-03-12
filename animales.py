class Animal:

    def sonido(self):
        print("El animal hace un sonido")

    def comer(self):
        print("El animal está comiendo")

    def dormir(self):
        print("El animal está durmiendo")


class Perro(Animal):
    def sonido(self):
        print("El perro ladra")


class Gato(Animal):
    def sonido(self):
        print("El gato maúlla")


class Vaca(Animal):
    def sonido(self):
        print("La vaca muge")


class Pato(Animal):
    def sonido(self):
        print("El pato grazna")


class Caballo(Animal):
    def sonido(self):
        print("El caballo relincha")


# Programa principal
perro = Perro()
gato = Gato()
vaca = Vaca()
pato = Pato()
caballo = Caballo()

perro.sonido()
gato.sonido()
vaca.sonido()
pato.sonido()
caballo.sonido()

perro.comer()
gato.dormir()
