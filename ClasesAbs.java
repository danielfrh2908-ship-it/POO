abstract class Figura{
    abstract double area();
}

class Cuadrado extends Figura{
    int lado;

    public Cuadrado(int lado){
        this.lado = lado;
    }

    public double area(){
        return lado * lado;
    }
}

public class Main{
    public static void main(String[] args){
        Cuadrado c = new Cuadrado(4);
        System.out.println("Area: " + c.area());
    }
}
