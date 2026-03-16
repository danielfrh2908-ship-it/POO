# ==========================================
# MODELO: CLASE LIBRO
# Representa un libro del sistema
# SOLO contiene datos (no lógica de préstamo)
# ==========================================

class Libro:

    def __init__(self, id_libro, titulo):
        self.id_libro = id_libro
        self.titulo = titulo
        self.disponible = True   # Estado del libro

    def __str__(self):
        return f"{self.titulo} - Disponible: {self.disponible}"


# ==========================================
# MODELO: CLASE USUARIO
# Representa a una persona que usa la biblioteca
# SOLO contiene datos
# ==========================================

class Usuario:

    def __init__(self, id_usuario, nombre):
        self.id_usuario = id_usuario
        self.nombre = nombre
        self.libros_prestados = []  # Lista de libros prestados

    def __str__(self):
        return f"Usuario: {self.nombre}"


# ==========================================
# SERVICIO: LibroService
# Se encarga de manejar el estado de los libros
# ==========================================

class LibroService:

    def marcar_prestado(self, libro):
        if libro.disponible:
            libro.disponible = False
            return True
        return False

    def marcar_devuelto(self, libro):
        libro.disponible = True


# ==========================================
# SERVICIO: UsuarioService
# Contiene la lógica del préstamo
# ==========================================

class UsuarioService:

    def prestar_libro(self, usuario, libro, libro_service):

        if libro_service.marcar_prestado(libro):
            usuario.libros_prestados.append(libro)
            print(f"{usuario.nombre} tomó prestado '{libro.titulo}'")
        else:
            print("El libro no está disponible")

    def devolver_libro(self, usuario, libro, libro_service):

        if libro in usuario.libros_prestados:
            usuario.libros_prestados.remove(libro)
            libro_service.marcar_devuelto(libro)
            print(f"{usuario.nombre} devolvió '{libro.titulo}'")
        else:
            print("El usuario no tiene este libro")


# ==========================================
# PROGRAMA PRINCIPAL
# Aquí se ejecuta el sistema
# ==========================================

libro1 = Libro(1, "Cien años de soledad")
usuario1 = Usuario(1, "Carlos")

libro_service = LibroService()
usuario_service = UsuarioService()

usuario_service.prestar_libro(usuario1, libro1, libro_service)
usuario_service.devolver_libro(usuario1, libro1, libro_service)
