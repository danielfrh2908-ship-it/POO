// =============================================================
// RPG SOCIAL MANAGER - VERSION JAVA SWING
// Conversión desde Tkinter Python a Java
// Efectos incluidos:
// ✔ Barras de vida
// ✔ Consola de eventos
// ✔ Ataques
// ✔ Curación
// ✔ Reinicio
// ✔ Interfaz estilo gamer
// =============================================================

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class RPGSocialManager extends JFrame {

    // =========================================================
    // CLASE PERSONAJE
    // =========================================================
    class Personaje {
        String nombre;
        int hp;
        int hpMax;
        int dano;

        public Personaje(String nombre, int hp, int dano) {
            this.nombre = nombre;
            this.hp = hp;
            this.hpMax = hp;
            this.dano = dano;
        }

        public boolean vivo() {
            return hp > 0;
        }

        public void recibirDano(int dmg) {
            hp = Math.max(0, hp - dmg);
        }
    }

    // =========================================================
    // VARIABLES
    // =========================================================

    Personaje est = new Personaje("👨‍🎓 Estudiantes", 160, 24);
    Personaje obr = new Personaje("👷 Obreros", 190, 28);
    Personaje bur = new Personaje("🎩 Burguesía", 145, 20);

    Personaje[] heroes = {est, obr, bur};

    Personaje enemy = new Personaje("👑 El Caudillo", 500, 35);

    JProgressBar[] barras = new JProgressBar[3];
    JLabel[] labelsHP = new JLabel[3];

    JProgressBar barraEnemy;
    JLabel enemyHP;

    JTextArea log;

    Random random = new Random();

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public RPGSocialManager() {

        setTitle("⚔ RPG SOCIAL MANAGER ⚔");
        setSize(1250, 760);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.BLACK);

        crearUI();
        actualizar();
    }

    // =========================================================
    // INTERFAZ
    // =========================================================

    public void crearUI() {

        // TITULO
        JLabel titulo = new JLabel("⚔ RPG SOCIAL MANAGER ⚔", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(Color.CYAN);
        titulo.setOpaque(true);
        titulo.setBackground(new Color(43, 0, 233));
        add(titulo, BorderLayout.NORTH);

        // PANEL CENTRAL
        JPanel centro = new JPanel(new GridLayout(1, 2));
        centro.setBackground(Color.BLACK);

        // HEROES
        JPanel panelHeroes = new JPanel();
        panelHeroes.setLayout(new GridLayout(3, 1, 10, 10));
        panelHeroes.setBackground(Color.BLACK);

        for (int i = 0; i < heroes.length; i++) {

            JPanel card = new JPanel();
            card.setLayout(new GridLayout(3, 1));
            card.setBackground(new Color(20, 20, 20));
            card.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));

            JLabel nombre = new JLabel(heroes[i].nombre, SwingConstants.CENTER);
            nombre.setForeground(Color.WHITE);
            nombre.setFont(new Font("Arial", Font.BOLD, 16));

            barras[i] = new JProgressBar(0, heroes[i].hpMax);
            barras[i].setStringPainted(true);

            labelsHP[i] = new JLabel("", SwingConstants.CENTER);
            labelsHP[i].setForeground(Color.GREEN);

            card.add(nombre);
            card.add(barras[i]);
            card.add(labelsHP[i]);

            panelHeroes.add(card);
        }

        // ENEMIGO
        JPanel panelEnemy = new JPanel();
        panelEnemy.setLayout(new GridLayout(3, 1, 10, 10));
        panelEnemy.setBackground(new Color(60, 0, 0));
        panelEnemy.setBorder(BorderFactory.createLineBorder(Color.RED, 3));

        JLabel enemyLabel = new JLabel("👑 EL CAUDILLO 👑", SwingConstants.CENTER);
        enemyLabel.setForeground(Color.RED);
        enemyLabel.setFont(new Font("Arial", Font.BOLD, 22));

        barraEnemy = new JProgressBar(0, enemy.hpMax);
        barraEnemy.setStringPainted(true);

        enemyHP = new JLabel("", SwingConstants.CENTER);
        enemyHP.setForeground(Color.WHITE);

        panelEnemy.add(enemyLabel);
        panelEnemy.add(barraEnemy);
        panelEnemy.add(enemyHP);

        centro.add(panelHeroes);
        centro.add(panelEnemy);

        add(centro, BorderLayout.CENTER);

        // BOTONES
        JPanel botones = new JPanel();
        botones.setBackground(Color.BLACK);

        JButton btnAtaque = crearBoton("⚔ Ataque");
        JButton btnEspecial = crearBoton("✨ Especial");
        JButton btnCurar = crearBoton("💚 Curar");
        JButton btnReset = crearBoton("🔄 Reiniciar");

        botones.add(btnAtaque);
        botones.add(btnEspecial);
        botones.add(btnCurar);
        botones.add(btnReset);

        add(botones, BorderLayout.SOUTH);

        // LOG
        log = new JTextArea(10, 40);
        log.setBackground(Color.BLACK);
        log.setForeground(Color.GREEN);
        log.setFont(new Font("Consolas", Font.PLAIN, 13));
        log.setEditable(false);

        add(new JScrollPane(log), BorderLayout.EAST);

        // EVENTOS
        btnAtaque.addActionListener(e -> atacar());
        btnEspecial.addActionListener(e -> especial());
        btnCurar.addActionListener(e -> curar());
        btnReset.addActionListener(e -> reiniciar());
    }

    // =========================================================
    // BOTON BONITO
    // =========================================================

    public JButton crearBoton(String texto) {

        JButton btn = new JButton(texto);
        btn.setBackground(Color.DARK_GRAY);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 14));

        return btn;
    }

    // =========================================================
    // LOG
    // =========================================================

    public void escribir(String txt) {
        log.append(txt + "\n");
        log.setCaretPosition(log.getDocument().getLength());
    }

    // =========================================================
    // ACTUALIZAR
    // =========================================================

    public void actualizar() {

        for (int i = 0; i < heroes.length; i++) {
            barras[i].setValue(heroes[i].hp);
            labelsHP[i].setText(heroes[i].hp + "/" + heroes[i].hpMax);
        }

        barraEnemy.setValue(enemy.hp);
        enemyHP.setText(enemy.hp + "/" + enemy.hpMax);
    }

    // =========================================================
    // ATAQUE
    // =========================================================

    public void atacar() {

        for (Personaje h : heroes) {
            if (h.vivo()) {

                int dmg = random.nextInt(11) + (h.dano - 5);

                enemy.recibirDano(dmg);

                escribir(h.nombre + " golpea por " + dmg);
            }
        }

        turnoEnemy();
        verificar();
        actualizar();
    }

    // =========================================================
    // ESPECIAL
    // =========================================================

    public void especial() {

        int total = random.nextInt(41) + 90;

        enemy.recibirDano(total);

        escribir("✨ ATAQUE COMBINADO causa " + total + " daño");

        turnoEnemy();
        verificar();
        actualizar();
    }

    // =========================================================
    // CURAR
    // =========================================================

    public void curar() {

        Personaje herido = heroes[0];

        for (Personaje h : heroes) {
            if (h.hp < herido.hp) {
                herido = h;
            }
        }

        int heal = 45;
        herido.hp = Math.min(herido.hpMax, herido.hp + heal);

        escribir("💚 Burguesía cura a " + herido.nombre + " +" + heal);

        turnoEnemy();
        actualizar();
    }

    // =========================================================
    // TURNO ENEMIGO
    // =========================================================

    public void turnoEnemy() {

        if (!enemy.vivo()) return;

        Personaje objetivo = null;

        while (objetivo == null) {
            Personaje temp = heroes[random.nextInt(heroes.length)];
            if (temp.vivo()) objetivo = temp;

            boolean todosMuertos = true;
            for (Personaje h : heroes)
                if (h.vivo()) todosMuertos = false;

            if (todosMuertos) return;
        }

        int dmg = random.nextInt(19) + 20;

        objetivo.recibirDano(dmg);

        escribir("👑 El Caudillo ataca a " + objetivo.nombre + " (" + dmg + ")");
    }

    // =========================================================
    // VERIFICAR
    // =========================================================

    public void verificar() {

        if (!enemy.vivo()) {
            JOptionPane.showMessageDialog(this,
                    "🎉 ¡Ganaste la batalla!");
        }

        boolean muertos = true;

        for (Personaje h : heroes)
            if (h.vivo()) muertos = false;

        if (muertos) {
            JOptionPane.showMessageDialog(this,
                    "💀 Tu equipo fue destruido.");
        }
    }

    // =========================================================
    // REINICIAR
    // =========================================================

    public void reiniciar() {
        dispose();
        new RPGSocialManager().setVisible(true);
    }

    // =========================================================
    // MAIN
    // =========================================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new RPGSocialManager().setVisible(true);
        });
    }
}
