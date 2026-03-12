class Animal {

    void sonido() {
        System.out.println("El animal hace un sonido");
    }

    void comer() {
        System.out.println("El animal está comiendo");
    }

    void dormir() {
        System.out.println("El animal está durmiendo");
    }
}

class Perro extends Animal {
    void sonido() {
        System.out.println("El perro ladra");
    }
}

class Gato extends Animal {
    @Override
    void sonido() {
        System.out.println("El gato maúlla");
    }
}

class Vaca extends Animal {
    @Override
    void sonido() {
        System.out.println("La vaca muge");
    }
}

class Pato extends Animal {
    @Override
    void sonido() {
        System.out.println("El pato grazna");
    }
}

class Caballo extends Animal {
    @Override
    void sonido() {
        System.out.println("El caballo relincha");
    }
}

public class Main {
    public static void main(String[] args) {

        Perro perro = new Perro();
        Gato gato = new Gato();
        Vaca vaca = new Vaca();
        Pato pato = new Pato();
        Caballo caballo = new Caballo();

        perro.sonido();
        gato.sonido();
        vaca.sonido();
        pato.sonido();
        caballo.sonido();

        perro.comer();
        gato.dormir();
    }
}
