# ==========================================================
# PARTE 1 - MODELOS DEL SISTEMA
# ==========================================================
# Estas clases representan entidades del sistema.
# SOLO contienen datos, no lógica de negocio.
# Esto aplica el principio de RESPONSABILIDAD ÚNICA (SRP).
# ==========================================================


class Libro:
    """
    MODELO: Libro
    Representa un libro dentro de la biblioteca.
    Contiene únicamente datos del libro.
    """

    def __init__(self, id_libro, titulo):
        self.id_libro = id_libro
        self.titulo = titulo
        self.disponible = True  # indica si el libro está prestado o no

    def __str__(self):
        return self.titulo


class Usuario:
    """
    MODELO: Usuario
    Representa una persona que usa la biblioteca.
    """

    def __init__(self, id_usuario, nombre):
        self.id_usuario = id_usuario
        self.nombre = nombre
        self.libros_prestados = []  # lista de libros que tiene prestados


# ==========================================================
# PARTE 2 - INTERFAZ PRESTABLE
# ==========================================================
# Una interfaz define un contrato de comportamiento.
# En Python se simula con una clase base.
# Cualquier objeto que pueda prestarse debe implementar
# prestar() y devolver()
# ==========================================================


class Prestable:

    def prestar(self):
        raise NotImplementedError

    def devolver(self):
        raise NotImplementedError


# ==========================================================
# IMPLEMENTACIÓN DE LA INTERFAZ EN LIBRO
# ==========================================================


class LibroPrestable(Libro, Prestable):

    def prestar(self):
        self.disponible = False

    def devolver(self):
        self.disponible = True


# ==========================================================
# PARTE 3 - SERVICIOS
# ==========================================================
# Aquí está la lógica del sistema.
# Separamos el MODELO de la LÓGICA.
# Esto hace el sistema más mantenible.
# ==========================================================


class LibroService:
    """
    Servicio encargado de manejar el estado de los libros
    """

    def marcar_prestado(self, libro):

        if libro.disponible:
            libro.disponible = False
            return True

        return False

    def marcar_devuelto(self, libro):
        libro.disponible = True


class UsuarioService:
    """
    Servicio encargado de manejar las acciones del usuario.
    Aquí está la lógica del préstamo.
    """

    LIMITE_LIBROS = 3

    def prestar_libro(self, usuario, libro, libro_service):

        # ===============================
        # VALIDACIÓN REGLA DE NEGOCIO
        # ===============================

        if len(usuario.libros_prestados) >= self.LIMITE_LIBROS:
            print("ERROR: El usuario ya tiene 3 libros prestados.")
            return

        if not libro.disponible:
            print("ERROR: El libro no está disponible.")
            return

        # ===============================
        # REALIZAR EL PRÉSTAMO
        # ===============================

        if libro_service.marcar_prestado(libro):
            usuario.libros_prestados.append(libro)

            print(usuario.nombre, "prestó", libro.titulo)

    def devolver_libro(self, usuario, libro, libro_service):

        if libro in usuario.libros_prestados:

            usuario.libros_prestados.remove(libro)
            libro_service.marcar_devuelto(libro)

            print(usuario.nombre, "devolvió", libro.titulo)


# ==========================================================
# PROGRAMA PRINCIPAL
# ==========================================================

libro1 = LibroPrestable(1, "1984")
libro2 = LibroPrestable(2, "El principito")
libro3 = LibroPrestable(3, "Don Quijote")
libro4 = LibroPrestable(4, "Cien años de soledad")

usuario = Usuario(1, "Carlos")

libro_service = LibroService()
usuario_service = UsuarioService()

usuario_service.prestar_libro(usuario, libro1, libro_service)
usuario_service.prestar_libro(usuario, libro2, libro_service)
usuario_service.prestar_libro(usuario, libro3, libro_service)

# este préstamo fallará porque supera el límite
usuario_service.prestar_libro(usuario, libro4, libro_service)
