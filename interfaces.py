from abc import ABC, abstractmethod

class Vehiculo(ABC):

    @abstractmethod
    def arrancar(self):
        pass

class Carro(Vehiculo):
    def arrancar(self):
        print("El carro arranca")

class Moto(Vehiculo):
    def arrancar(self):
        print("La moto arranca")

v1 = Carro()
v1.arrancar()
