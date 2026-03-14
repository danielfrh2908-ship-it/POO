interface Vehiculo{
    void arrancar();
}

class Carro implements Vehiculo{
    public void arrancar(){
        System.out.println("El carro arranca");
    }
}

class Moto implements Vehiculo{
    public void arrancar(){
        System.out.println("La moto arranca");
    }
}

public class Main{
    public static void main(String[] args){
        Vehiculo v = new Carro();
        v.arrancar();
    }
}
