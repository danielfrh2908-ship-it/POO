// ==========================================================
// PARTE 1 - INTERFAZ PRESTABLE
// ==========================================================
// Define un contrato para objetos que se pueden prestar.
// Cualquier clase que implemente esta interfaz debe
// implementar prestar() y devolver()
// ==========================================================

interface Prestable {

    void prestar();
    void devolver();
}


// ==========================================================
// PARTE 2 - MODELO LIBRO
// ==========================================================

class Libro implements Prestable {

    int idLibro;
    String titulo;
    boolean disponible;

    public Libro(int idLibro, String titulo) {

        this.idLibro = idLibro;
        this.titulo = titulo;
        this.disponible = true;
    }

    // implementación de la interfaz

    public void prestar() {
        disponible = false;
    }

    public void devolver() {
        disponible = true;
    }
}


// ==========================================================
// MODELO USUARIO
// ==========================================================

import java.util.ArrayList;

class Usuario {

    int idUsuario;
    String nombre;

    ArrayList<Libro> librosPrestados;

    public Usuario(int idUsuario, String nombre) {

        this.idUsuario = idUsuario;
        this.nombre = nombre;

        librosPrestados = new ArrayList<>();
    }
}


// ==========================================================
// SERVICIO LIBRO
// ==========================================================

class LibroService {

    public boolean marcarPrestado(Libro libro) {

        if (libro.disponible) {
            libro.prestar();
            return true;
        }

        return false;
    }

    public void marcarDevuelto(Libro libro) {

        libro.devolver();
    }
}


// ==========================================================
// SERVICIO USUARIO
// ==========================================================

class UsuarioService {

    private static final int LIMITE_LIBROS = 3;

    public void prestarLibro(Usuario usuario, Libro libro, LibroService libroService) {

        // validación de regla de negocio

        if (usuario.librosPrestados.size() >= LIMITE_LIBROS) {

            System.out.println("ERROR: El usuario ya tiene 3 libros.");
            return;
        }

        if (!libro.disponible) {

            System.out.println("ERROR: El libro no está disponible.");
            return;
        }

        if (libroService.marcarPrestado(libro)) {

            usuario.librosPrestados.add(libro);

            System.out.println(usuario.nombre + " prestó " + libro.titulo);
        }
    }

    public void devolverLibro(Usuario usuario, Libro libro, LibroService libroService) {

        if (usuario.librosPrestados.contains(libro)) {

            usuario.librosPrestados.remove(libro);

            libroService.marcarDevuelto(libro);

            System.out.println(usuario.nombre + " devolvió " + libro.titulo);
        }
    }
}


// ==========================================================
// PROGRAMA PRINCIPAL
// ==========================================================

public class Main {

    public static void main(String[] args) {

        Libro libro1 = new Libro(1, "1984");
        Libro libro2 = new Libro(2, "El principito");
        Libro libro3 = new Libro(3, "Don Quijote");
        Libro libro4 = new Libro(4, "Cien años de soledad");

        Usuario usuario = new Usuario(1, "Carlos");

        LibroService libroService = new LibroService();
        UsuarioService usuarioService = new UsuarioService();

        usuarioService.prestarLibro(usuario, libro1, libroService);
        usuarioService.prestarLibro(usuario, libro2, libroService);
        usuarioService.prestarLibro(usuario, libro3, libroService);

        // fallará por límite de libros
        usuarioService.prestarLibro(usuario, libro4, libroService);
    }
}
