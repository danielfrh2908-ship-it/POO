import java.util.ArrayList;

public class Estudiante {

    // Atributos privados (encapsulación)
    private String nombre;
    private int edad;
    private String carrera;
    private ArrayList<String> materias;

    // Constructor
    public Estudiante(String nombre, int edad, String carrera) {
        this.nombre = nombre;
        this.edad = edad;
        this.carrera = carrera;
        this.materias = new ArrayList<>();
    }

    // Método para inscribir materia
    public void inscribirMateria(String materia) {
        this.materias.add(materia);
        System.out.println(nombre + " se inscribió en " + materia);
    }

    // Método estudiar
    public void estudiar(int horas) {
        System.out.println(nombre + " estudió " + horas + " horas");
    }

    // Método para presentarse
    public void presentarse() {
        System.out.println("Hola, soy " + nombre);
        System.out.println("Tengo " + edad + " años");
        System.out.println("Estudio " + carrera);
        System.out.println("Materias inscritas: " + materias.size());
    }

    // Método main para probar
    public static void main(String[] args) {

        // Crear objetos
        Estudiante est1 = new Estudiante("Daniel Riaño", 18, "Ing. Telematica");
        Estudiante est2 = new Estudiante("Carlos López", 22, "Ing. Sistemas");

        // Usar métodos
        est1.presentarse();
        System.out.println("---");

        est1.inscribirMateria("POO");
        est1.inscribirMateria("Bases de Datos");
        System.out.println("---");

        est2.presentarse();
        est2.inscribirMateria("POO");

        System.out.println("\n--- Estudiante con mis datos ---");

        // Crear estudiante con tus datos (puedes cambiar el nombre si quieres)
        Estudiante yo = new Estudiante("Tu Nombre", 20, "Ing. Sistemas");

        yo.presentarse();
        yo.inscribirMateria("Programación");
        yo.inscribirMateria("Calculo Integral");
        yo.inscribirMateria("Base de Datos");

        yo.estudiar(3);
    }
}
