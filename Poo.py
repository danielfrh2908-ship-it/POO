print('=== VERSIÓN IMPERATIVA ===')

# Estudiante y sus notas
nombre_estudiante = 'Juan'
nota1 = 4.5
nota2 = 3.8
nota3 = 4.2

# Calcular promedio
suma = nota1 + nota2 + nota3
promedio = suma / 3

# Determinar si aprobó
if promedio >= 3.0:
    estado = 'APROBÓ'
else:
    estado = 'REPROBÓ'

print(f'{nombre_estudiante}: {promedio:.2f} - {estado}')

print('\n=== VERSIÓN OOP ===')

class Estudiante:
    def __init__(self, nombre):
        self.nombre = nombre
        self.notas = []

    def agregar_nota(self, nota):
        self.notas.append(nota)

    def calcular_promedio(self):
        if len(self.notas) == 0:
            return 0
        return sum(self.notas) / len(self.notas)

    def aprobo(self):
        return self.calcular_promedio() >= 3.0

    def mostrar_resultado(self):
        promedio = self.calcular_promedio()
        estado = 'APROBÓ' if self.aprobo() else 'REPROBÓ'
        print(f'{self.nombre}: {promedio:.2f} - {estado}')


# Usar la clase
estudiante = Estudiante('Juan')
estudiante.agregar_nota(4.5)
estudiante.agregar_nota(3.8)
estudiante.agregar_nota(4.2)

estudiante.mostrar_resultado()

print('\n=== MÚLTIPLES ESTUDIANTES ===')

# Crear varios estudiantes
maria = Estudiante('María')
maria.agregar_nota(4.8)
maria.agregar_nota(4.5)
maria.agregar_nota(4.9)

pedro = Estudiante('Pedro')
pedro.agregar_nota(2.5)
pedro.agregar_nota(3.1)
pedro.agregar_nota(2.8)

# Mostrar resultados
maria.mostrar_resultado()
pedro.mostrar_resultado()
