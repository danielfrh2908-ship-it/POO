class Persona:
    def __init__(self, nombre, edad):
        self.__nombre = nombre   # atributo privado
        self.__edad = edad

    # getter
    def get_nombre(self):
        return self.__nombre

    # setter
    def set_nombre(self, nombre):
        self.__nombre = nombre

persona1 = Persona("Daniel", 18)

print(persona1.get_nombre())

persona1.set_nombre("Felipe")
print(persona1.get_nombre())
