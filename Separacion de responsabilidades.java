// ==========================================
// MODELO: CLASE LIBRO
// Representa un libro
// SOLO contiene datos
// ==========================================

class Libro {

    int idLibro;
    String titulo;
    boolean disponible;

    public Libro(int idLibro, String titulo) {
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.disponible = true;
    }
}


// ==========================================
// MODELO: CLASE USUARIO
// Representa un usuario del sistema
// ==========================================

import java.util.ArrayList;

class Usuario {

    int idUsuario;
    String nombre;
    ArrayList<Libro> librosPrestados;

    public Usuario(int idUsuario, String nombre) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.librosPrestados = new ArrayList<>();
    }
}


// ==========================================
// SERVICIO: LibroService
// Maneja el estado de los libros
// ==========================================

class LibroService {

    public boolean marcarPrestado(Libro libro) {

        if (libro.disponible) {
            libro.disponible = false;
            return true;
        }

        return false;
    }

    public void marcarDevuelto(Libro libro) {
        libro.disponible = true;
    }
}


// ==========================================
// SERVICIO: UsuarioService
// Contiene la lógica de préstamo
// ==========================================

class UsuarioService {

    public void prestarLibro(Usuario usuario, Libro libro, LibroService libroService) {

        if (libroService.marcarPrestado(libro)) {
            usuario.librosPrestados.add(libro);
            System.out.println(usuario.nombre + " tomó prestado " + libro.titulo);
        } else {
            System.out.println("El libro no está disponible");
        }
    }

    public void devolverLibro(Usuario usuario, Libro libro, LibroService libroService) {

        if (usuario.librosPrestados.contains(libro)) {
            usuario.librosPrestados.remove(libro);
            libroService.marcarDevuelto(libro);
            System.out.println(usuario.nombre + " devolvió " + libro.titulo);
        } else {
            System.out.println("El usuario no tiene ese libro");
        }
    }
}


// ==========================================
// PROGRAMA PRINCIPAL
// Aquí se ejecuta el sistema
// ==========================================

public class Main {

    public static void main(String[] args) {

        Libro libro1 = new Libro(1, "Cien años de soledad");
        Usuario usuario1 = new Usuario(1, "Carlos");

        LibroService libroService = new LibroService();
        UsuarioService usuarioService = new UsuarioService();

        usuarioService.prestarLibro(usuario1, libro1, libroService);
        usuarioService.devolverLibro(usuario1, libro1, libroService);
    }
}
