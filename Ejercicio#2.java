import java.util.ArrayList;

class EstudianteNotas {

    private String nombre;
    private ArrayList<Double> notas;

    public EstudianteNotas(String nombre) {
        this.nombre = nombre;
        this.notas = new ArrayList<>();
    }

    public void agregarNota(double nota) {
        this.notas.add(nota);
    }

    public double calcularPromedio() {
        if (notas.isEmpty())
            return 0;

        double suma = 0;
        for (double nota : notas) {
            suma += nota;
        }

        return suma / notas.size();
    }

    public boolean aprobo() {
        return calcularPromedio() >= 3.0;
    }

    public void mostrarResultado() {
        double promedio = calcularPromedio();
        String estado = aprobo() ? "APROBÓ" : "REPROBÓ";
        System.out.printf("%s: %.2f - %s%n", nombre, promedio, estado);
    }
}
