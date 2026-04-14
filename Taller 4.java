package rpgmanager;

public class Principal {
    public static void main(String[] args) {

        // 1. Crear el gremio
        GremioRPG g = new GremioRPG("Los Inmortales");

        // 2. Instanciar 8 personajes
        Personaje p1 = new Personaje("Aragorn", 12, 150);
        Personaje p2 = new Personaje("Gandalf", 15, 200);
        Personaje p3 = new Personaje("Legolas", 9, 110);
        Personaje p4 = new Personaje("Frodo", 5, 80);
        Personaje p5 = new Personaje("Gimli", 8, 130);
        Personaje p6 = new Personaje("Boromir", 7, 100);
        Personaje p7 = new Personaje("Sam", 4, 70);
        Personaje p8 = new Personaje("Pippin", 3, 60);

        // 3. Unir los 8 al equipo (6 entran, 2 van a espera)
        g.unirseAlEquipo(p1);
        g.unirseAlEquipo(p2);
        g.unirseAlEquipo(p3);
        g.unirseAlEquipo(p4);
        g.unirseAlEquipo(p5);
        g.unirseAlEquipo(p6);
        g.unirseAlEquipo(p7); // va a lista de espera
        g.unirseAlEquipo(p8); // va a lista de espera

        // 4. Colocar 4 en el dungeon
        g.colocarEnDungeon(p1, 0, 0);
        g.colocarEnDungeon(p2, 0, 2);
        g.colocarEnDungeon(p3, 1, 1);
        g.colocarEnDungeon(p4, 2, 0);

        // 5. Simular batalla: 2 personajes caen
        p7.setHp(0);
        p8.setHp(0);

        // 6. Limpiar caídos
        g.limpiarCaidos();

        // 7. Reporte final
        g.reporteGremio();
    }
}
