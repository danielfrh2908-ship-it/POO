from abc import ABC, abstractmethod

class Figura(ABC):

    @abstractmethod
    def area(self):
        pass

class Cuadrado(Figura):
    def __init__(self, lado):
        self.lado = lado

    def area(self):
        return self.lado * self.lado

c = Cuadrado(4)
print("Area:", c.area())
