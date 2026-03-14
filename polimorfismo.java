class Animal{
    public void sonido(){
        System.out.println("El animal hace un sonido");
    }
}

class Perro extends Animal{
    public void sonido(){
        System.out.println("El perro ladra");
    }
}

class Gato extends Animal{
    public void sonido(){
        System.out.println("El gato maulla");
    }
}

public class Main{
    public static void main(String[] args){
        Animal a1 = new Perro();
        Animal a2 = new Gato();

        a1.sonido();
        a2.sonido();
    }
}
