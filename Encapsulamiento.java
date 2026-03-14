class Persona {
    private String nombre;
    private int edad;

    public Persona(String nombre, int edad){
        this.nombre = nombre;
        this.edad = edad;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
}

public class Main{
    public static void main(String[] args){
        Persona p = new Persona("Daniel",18);
        System.out.println(p.getNombre());

        p.setNombre("Felipe");
        System.out.println(p.getNombre());
    }
}
