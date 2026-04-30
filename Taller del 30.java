import java.util.ArrayList;

// ============================================================
//  INTERFACES  (Seccion 4)
// ============================================================

interface Habilidoso {
    void usarHabilidadEspecial(Personaje objetivo);
    int getCostoHabilidad();
    String getNombreHabilidad();
}

interface Equipable {
    void equiparItem(String item);
    String getItemEquipado();
}

interface Sanador {
    void sanar(Personaje objetivo);
    int getPotenciaSanacion();
}

// ============================================================
//  CLASE ABSTRACTA PERSONAJE  (Seccion 1)
// ============================================================

abstract class Personaje {

    protected String nombre;
    protected int nivel;
    protected int puntosVida;
    protected int puntosVidaMax;

    public Personaje(String nombre, int nivel, int puntosVidaMax) {
        this.nombre       = nombre;
        this.nivel        = nivel;
        this.puntosVidaMax = puntosVidaMax;
        this.puntosVida   = puntosVidaMax;   // empieza con vida completa
    }

    // Metodo concreto: restar dano sin bajar de 0
    public void recibirDano(int dano) {
        puntosVida = Math.max(0, puntosVida - dano);
        System.out.println("  " + nombre + " recibe " + dano
                + " de dano → HP: " + puntosVida + "/" + puntosVidaMax);
    }

    // Metodo concreto: verificar si sigue vivo
    public boolean estaVivo() {
        return puntosVida > 0;
    }

    // Metodo concreto: restaurar vida (lo usan los Sanadores)
    public void restaurarVida(int cantidad) {
        puntosVida = Math.min(puntosVidaMax, puntosVida + cantidad);
    }

    public int getPuntosVida()    { return puntosVida; }
    public int getPuntosVidaMax() { return puntosVidaMax; }
    public String getNombre()     { return nombre; }

    // Metodos abstractos: cada subclase los implementa a su manera
    public abstract void atacar(Personaje objetivo);
    public abstract String getTipoPersonaje();

    @Override
    public String toString() {
        return String.format("[%-7s] %-8s Nv.%d | HP: %3d/%3d",
                getTipoPersonaje(), nombre, nivel, puntosVida, puntosVidaMax);
    }
}

// ============================================================
//  GUERRERO  (Seccion 2 + 4: Habilidoso + Equipable)
// ============================================================

class Guerrero extends Personaje implements Habilidoso, Equipable {

    private int fuerza;
    private int defensa;
    private String itemEquipado  = "Sin equipo";
    private int costoHabilidad   = 30;

    public Guerrero(String nombre, int nivel) {
        super(nombre, nivel, 100 + nivel * 10);
        this.fuerza  = 15 + nivel * 3;
        this.defensa = 10 + nivel * 2;
    }

    @Override
    public void atacar(Personaje objetivo) {
        int dano = fuerza;
        System.out.println(nombre + " golpea a " + objetivo.getNombre()
                + " con su espada!");
        objetivo.recibirDano(dano);
    }

    @Override
    public String getTipoPersonaje() { return "Guerrero"; }

    public void usarEscudo() {
        System.out.println(nombre + " bloquea con su escudo (defensa " + defensa + ").");
    }

    // --- Habilidoso ---
    @Override
    public void usarHabilidadEspecial(Personaje objetivo) {
        System.out.println(nombre + " usa [" + getNombreHabilidad()
                + "] sobre " + objetivo.getNombre() + "!");
        objetivo.recibirDano(50);
    }
    @Override public int    getCostoHabilidad() { return costoHabilidad; }
    @Override public String getNombreHabilidad() { return "Golpe Devastador"; }

    // --- Equipable ---
    @Override
    public void equiparItem(String item) {
        this.itemEquipado = item;
        System.out.println(nombre + " equipa: " + item);
    }
    @Override public String getItemEquipado() { return itemEquipado; }
}

// ============================================================
//  MAGO  (Seccion 2 + 4: Habilidoso + Sanador)
// ============================================================

class Mago extends Personaje implements Habilidoso, Sanador {

    private int mana;
    private int manaMax;

    public Mago(String nombre, int nivel) {
        super(nombre, nivel, 60 + nivel * 5);
        this.manaMax = 80 + nivel * 10;
        this.mana    = manaMax;
    }

    @Override
    public void atacar(Personaje objetivo) {
        if (mana >= 20) {
            int dano = 25 + nivel * 5;
            System.out.println(nombre + " lanza un hechizo sobre "
                    + objetivo.getNombre() + "! (mana: " + mana + " → " + (mana - 20) + ")");
            mana -= 20;
            objetivo.recibirDano(dano);
        } else {
            System.out.println(nombre + " no tiene mana suficiente para atacar.");
        }
    }

    @Override
    public String getTipoPersonaje() { return "Mago"; }

    public void recuperarMana(int cantidad) {
        mana = Math.min(manaMax, mana + cantidad);
        System.out.println(nombre + " recupera " + cantidad
                + " de mana → " + mana + "/" + manaMax);
    }

    // --- Habilidoso ---
    @Override
    public void usarHabilidadEspecial(Personaje objetivo) {
        if (mana >= getCostoHabilidad()) {
            System.out.println(nombre + " lanza [" + getNombreHabilidad()
                    + "] sobre " + objetivo.getNombre() + "!");
            mana -= getCostoHabilidad();
            objetivo.recibirDano(40);
        } else {
            System.out.println(nombre + " no tiene mana para " + getNombreHabilidad() + ".");
        }
    }
    @Override public int    getCostoHabilidad() { return 20; }
    @Override public String getNombreHabilidad() { return "Bola de Fuego"; }

    // --- Sanador ---
    @Override
    public void sanar(Personaje objetivo) {
        int curacion = getPotenciaSanacion();
        objetivo.restaurarVida(curacion);
        System.out.println(nombre + " sana a " + objetivo.getNombre()
                + " por " + curacion + " HP → " + objetivo.getPuntosVida()
                + "/" + objetivo.getPuntosVidaMax());
    }
    @Override public int getPotenciaSanacion() { return 25; }
}

// ============================================================
//  ARQUERO  (Seccion 2 + 4: Equipable)
// ============================================================

class Arquero extends Personaje implements Equipable {

    private int flechas;
    private int alcance;
    private String itemEquipado = "Arco basico";

    public Arquero(String nombre, int nivel) {
        super(nombre, nivel, 75 + nivel * 7);
        this.flechas = 10 + nivel * 2;
        this.alcance = 30;
    }

    @Override
    public void atacar(Personaje objetivo) {
        if (flechas > 0) {
            int dano = 12 + nivel * 4 + (itemEquipado.equals("Arco basico") ? 0 : 5);
            System.out.println(nombre + " dispara una flecha a "
                    + objetivo.getNombre() + "! (flechas restantes: " + (flechas - 1) + ")");
            flechas--;
            objetivo.recibirDano(dano);
        } else {
            System.out.println(nombre + " no le quedan flechas!");
        }
    }

    @Override
    public String getTipoPersonaje() { return "Arquero"; }

    public void recargarFlechas(int cantidad) {
        flechas += cantidad;
        System.out.println(nombre + " recarga " + cantidad
                + " flechas → " + flechas + " total.");
    }

    // --- Equipable ---
    @Override
    public void equiparItem(String item) {
        this.itemEquipado = item;
        System.out.println(nombre + " equipa: " + item);
    }
    @Override public String getItemEquipado() { return itemEquipado; }
}

// ============================================================
//  MAIN  —  Batalla Completa  (Secciones 3 + 5)
// ============================================================

public class RPGManager {

    public static void main(String[] args) {

        // ---- Seccion 2: crear personajes y mostrarlos ----
        System.out.println("========================================");
        System.out.println("       RPG MANAGER — Estado inicial     ");
        System.out.println("========================================");
        Guerrero thorin  = new Guerrero("Thorin",  3);
        Mago     gandalf = new Mago    ("Gandalf", 5);
        Arquero  legolas = new Arquero ("Legolas", 4);
        System.out.println(thorin);
        System.out.println(gandalf);
        System.out.println(legolas);

        // ---- Seccion 5 — Fase 1: equipar ----
        System.out.println("\n--- Fase 1: Equipando heroes ---");
        thorin.equiparItem("Espada Legendaria");
        legolas.equiparItem("Arco Elfico");

        // ---- Seccion 3 + 5 — Fase 2: batalla por turnos ----
        System.out.println("\n--- Fase 2: Batalla por turnos ---");
        ArrayList<Personaje> heroes = new ArrayList<>();
        heroes.add(thorin);
        heroes.add(gandalf);
        heroes.add(legolas);

        Personaje orco = new Guerrero("Orco", 1);
        System.out.println("Enemigo: " + orco);
        System.out.println();

        int turno = 1;
        while (orco.estaVivo()) {
            System.out.println("=== Turno " + turno + " ===");
            for (Personaje h : heroes) {
                if (!orco.estaVivo()) break;

                // En el turno 2 los Habilidosos usan su habilidad especial
                if (turno == 2 && h instanceof Habilidoso) {
                    ((Habilidoso) h).usarHabilidadEspecial(orco);
                } else {
                    h.atacar(orco);
                }
            }
            turno++;
        }

        System.out.println("\nEl Orco fue derrotado en " + (turno - 1) + " turno(s).");

        // ---- Seccion 5 — Fase 3: sanacion post batalla ----
        System.out.println("\n--- Fase 3: Sanacion post batalla ---");

        // Encontrar al heroe con menos vida
        Personaje masDebil = heroes.get(0);
        for (Personaje h : heroes) {
            if (h.getPuntosVida() < masDebil.getPuntosVida()) {
                masDebil = h;
            }
        }
        System.out.println("Heroe con menos vida: " + masDebil.getNombre()
                + " (" + masDebil.getPuntosVida() + " HP)");

        for (Personaje h : heroes) {
            if (h instanceof Sanador) {
                ((Sanador) h).sanar(masDebil);
            }
        }

        // ---- Estado final ----
        System.out.println("\n========================================");
        System.out.println("          Estado final del equipo       ");
        System.out.println("========================================");
        for (Personaje h : heroes) {
            System.out.println(h);
        }
        System.out.println(orco);
    }
}
