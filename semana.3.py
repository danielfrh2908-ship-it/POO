class Docente:
    def __init__(self, nombre, titulo):
        self.nombre = nombre
        self.titulo = titulo
        # Asociación: los cursos existen independientemente del docente
        self.__cursos = []

    def asignar_curso(self, curso):
        # Validar que no supere 16 créditos semanales
        carga_actual = self.calcular_carga()

        if carga_actual + curso._Curso__creditos > 16:
            print(
                f'No se puede asignar: {self.nombre} superaría 16 créditos '
                f'(actual: {carga_actual}, curso: {curso._Curso__creditos})'
            )
        else:
            self.__cursos.append(curso)
            print(
                f'{self.nombre} asignado a {curso.nombre} '
                f'({curso._Curso__creditos} créditos)'
            )

    def calcular_carga(self):
        # Suma de créditos de todos los cursos asignados
        return sum(c._Curso__creditos for c in self.__cursos)

    def __str__(self):
        return (
            f'Docente: {self.nombre} | {self.titulo} | '
            f'Carga: {self.calcular_carga()} créditos'
        )


class Programa:
    def __init__(self, nombre, snies):
        self.nombre = nombre
        self.snies = snies

        # Composición: los cursos son creados y gestionados dentro del programa
        self.__cursos = []

        # Agregación: los docentes existen independientemente del programa
        self.__docentes = []

    def agregar_curso(self, curso):
        # Verifica que no esté duplicado por código
        if not any(c.codigo == curso.codigo for c in self.__cursos):
            self.__cursos.append(curso)
            print(f'Curso {curso.nombre} agregado al programa {self.nombre}')
        else:
            print(f'El curso {curso.codigo} ya existe en {self.nombre}')

    def vincular_docente(self, docente):
        # Agregación: el docente ya existe fuera del programa
        if docente not in self.__docentes:
            self.__docentes.append(docente)
            print(f'Docente {docente.nombre} vinculado a {self.nombre}')
        else:
            print(f'{docente.nombre} ya está vinculado a {self.nombre}')

    def total_estudiantes(self):
        """
        Suma los estudiantes únicos en todos los cursos del programa.
        Se usa un set para evitar contar duplicados.
        """
        codigos_unicos = set()

        for curso in self.__cursos:
            for e in curso._Curso__estudiantes:
                codigos_unicos.add(e.codigo)

        return len(codigos_unicos)

    def listar(self):
        print(f'\n--- Programa: {self.nombre} | SNIES: {self.snies} ---')

        print(f'  Cursos: {len(self.__cursos)}')
        for c in self.__cursos:
            print(f'    • {c.nombre} [{c.codigo}]')

        print(f'  Docentes: {len(self.__docentes)}')
        for d in self.__docentes:
            print(f'    • {d}')

        print(f'  Total estudiantes únicos: {self.total_estudiantes()}')

    def __str__(self):
        return (
            f'Programa: {self.nombre} | SNIES: {self.snies} | '
            f'Cursos: {len(self.__cursos)}'
        )


class Universidad:
    def __init__(self, nombre, ciudad):
        self.__nombre = nombre
        self.__ciudad = ciudad

        # Composición: los programas nacen dentro de la universidad
        self.__programas = []

    def crear_programa(self, nombre, snies):
        """
        Composición: el programa se crea dentro de la universidad
        """
        prog = Programa(nombre, snies)
        self.__programas.append(prog)

        print(f'Programa {nombre} creado en {self.__nombre}')
        return prog

    def listar_programas(self):
        print(f'\n=== Universidad: {self.__nombre} | {self.__ciudad} ===')
        print(f'Total de programas: {len(self.__programas)}')

        for prog in self.__programas:
            prog.listar()

    def buscar_programa(self, nombre):
        """
        Búsqueda funcional usando next() y comprensión de generador
        """
        return next(
            (p for p in self.__programas if p.nombre.lower() == nombre.lower()),
            None  # Valor por defecto si no se encuentra
        )

    def __str__(self):
        return f'Universidad: {self.__nombre} | Ciudad: {self.__ciudad}'
