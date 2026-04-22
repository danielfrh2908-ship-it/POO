# ==============================================================
# RPG SOCIAL MANAGER - VERSION TKINTER MEJORADA
# Con efectos especiales:
# ✔ Flash al atacar
# ✔ Sacudida enemigo
# ✔ Curación verde
# ✔ Barras animadas
# ✔ Interfaz gamer neon
# ✔ Log estilo consola
# ==============================================================

import tkinter as tk
from tkinter import ttk, messagebox
import random

# ==============================================================
# CLASE PERSONAJE
# ==============================================================

class Personaje:
    def __init__(self, nombre, hp, dano):
        self.nombre = nombre
        self.hp = hp
        self.hp_max = hp
        self.dano = dano

    def vivo(self):
        return self.hp > 0

    def recibir_dano(self, dmg):
        self.hp = max(0, self.hp - dmg)


# ==============================================================
# JUEGO
# ==============================================================

class Juego:

    def __init__(self, root):

        self.root = root
        self.root.title("⚔ RPG SOCIAL MANAGER ⚔")
        self.root.geometry("1250x760")
        self.root.config(bg="#050505")

        # -----------------------------------
        # PERSONAJES
        # -----------------------------------
        self.est = Personaje("👨‍🎓 Estudiantes", 160, 24)
        self.obr = Personaje("👷 Obreros", 190, 28)
        self.bur = Personaje("🎩 Burguesía", 145, 20)

        self.heroes = [self.est, self.obr, self.bur]

        self.enemy = Personaje("👑 El Caudillo", 500, 35)

        self.crear_ui()
        self.actualizar()

    # ==========================================================
    # UI
    # ==========================================================

    def crear_ui(self):

        titulo = tk.Label(
            self.root,
            text="⚔ RPG SOCIAL MANAGER ⚔",
            font=("Arial", 28),
            fg="#00ffff",
            bg="#2B00E9"
        )
        titulo.pack(pady=10)

        # PANEL PRINCIPAL
        self.main = tk.Frame(self.root, bg="#050505")
        self.main.pack()

        # ------------------------------------------------------
        # HEROES
        # ------------------------------------------------------
        self.panel_heroes = tk.Frame(self.main, bg="#050505")
        self.panel_heroes.grid(row=0, column=0, padx=25)

        self.barras = {}

        for h in self.heroes:

            card = tk.Frame(
                self.panel_heroes,
                bg="#111",
                bd=3,
                relief="ridge",
                padx=15,
                pady=15
            )
            card.pack(pady=15)

            tk.Label(
                card,
                text=h.nombre,
                fg="white",
                bg="#111",
                font=("Arial", 16, "bold")
            ).pack()

            barra = ttk.Progressbar(
                card,
                length=280,
                maximum=h.hp_max
            )
            barra.pack(pady=10)

            txt = tk.Label(
                card,
                text="",
                fg="#00ff00",
                bg="#111",
                font=("Arial", 11)
            )
            txt.pack()

            self.barras[h.nombre] = (barra, txt)

        # ------------------------------------------------------
        # ENEMIGO
        # ------------------------------------------------------
        self.enemy_frame = tk.Frame(
            self.main,
            bg="#220000",
            bd=5,
            relief="ridge",
            padx=40,
            pady=40
        )
        self.enemy_frame.grid(row=0, column=1, padx=50)

        self.enemy_label = tk.Label(
            self.enemy_frame,
            text="👑 EL CAUDILLO 👑",
            fg="red",
            bg="#220000",
            font=("Arial Black", 24)
        )
        self.enemy_label.pack()

        self.enemy_bar = ttk.Progressbar(
            self.enemy_frame,
            length=360,
            maximum=self.enemy.hp_max
        )
        self.enemy_bar.pack(pady=15)

        self.enemy_txt = tk.Label(
            self.enemy_frame,
            text="",
            fg="white",
            bg="#220000",
            font=("Arial", 12)
        )
        self.enemy_txt.pack()

        # ------------------------------------------------------
        # BOTONES
        # ------------------------------------------------------
        btn_frame = tk.Frame(self.root, bg="#050505")
        btn_frame.pack(pady=15)

        self.boton(
            btn_frame, "⚔ Ataque", self.atacar, 0
        )

        self.boton(
            btn_frame, "✨ Especial", self.especial, 1
        )

        self.boton(
            btn_frame, "💚 Curar", self.curar, 2
        )

        self.boton(
            btn_frame, "🔄 Reiniciar", self.reiniciar, 3
        )

        # ------------------------------------------------------
        # LOG
        # ------------------------------------------------------
        self.log = tk.Text(
            self.root,
            height=14,
            bg="black",
            fg="#00ff00",
            font=("Consolas", 11)
        )
        self.log.pack(fill="both", padx=20, pady=12)

    # ==========================================================
    # BOTON BONITO
    # ==========================================================

    def boton(self, frame, texto, comando, col):

        btn = tk.Button(
            frame,
            text=texto,
            width=16,
            height=2,
            bg="#111",
            fg="white",
            font=("Arial", 11, "bold"),
            command=comando,
            activebackground="#00ffff"
        )

        btn.grid(row=0, column=col, padx=8)

        btn.bind("<Enter>", lambda e: btn.config(bg="#00aaaa"))
        btn.bind("<Leave>", lambda e: btn.config(bg="#111"))

    # ==========================================================
    # LOG
    # ==========================================================

    def escribir(self, txt):
        self.log.insert(tk.END, txt + "\n")
        self.log.see(tk.END)

    # ==========================================================
    # ACTUALIZAR
    # ==========================================================

    def actualizar(self):

        for h in self.heroes:
            barra, txt = self.barras[h.nombre]
            barra["value"] = h.hp
            txt.config(text=f"{h.hp}/{h.hp_max}")

        self.enemy_bar["value"] = self.enemy.hp
        self.enemy_txt.config(
            text=f"{self.enemy.hp}/{self.enemy.hp_max}"
        )

    # ==========================================================
    # FLASH COLOR
    # ==========================================================

    def flash_enemy(self, color):

        original = self.enemy_frame["bg"]

        self.enemy_frame.config(bg=color)
        self.enemy_label.config(bg=color)
        self.enemy_txt.config(bg=color)

        self.root.after(
            180,
            lambda: self.enemy_frame.config(bg=original)
        )

        self.root.after(
            180,
            lambda: self.enemy_label.config(bg=original)
        )

        self.root.after(
            180,
            lambda: self.enemy_txt.config(bg=original)
        )

    # ==========================================================
    # SACUDIDA
    # ==========================================================

    def shake_enemy(self):

        x = self.enemy_frame.winfo_x()

        for i in range(6):
            move = -10 if i % 2 == 0 else 10
            self.enemy_frame.place(x=x + move, y=0)
            self.root.update()

        self.enemy_frame.place_forget()
        self.enemy_frame.grid(row=0, column=1, padx=50)

    # ==========================================================
    # ATAQUE
    # ==========================================================

    def atacar(self):

        for h in self.heroes:
            if h.vivo():

                dmg = random.randint(h.dano - 5, h.dano + 5)

                self.enemy.recibir_dano(dmg)

                self.escribir(
                    f"{h.nombre} golpea por {dmg}"
                )

        self.flash_enemy("red")
        self.shake_enemy()

        self.turno_enemy()
        self.verificar()
        self.actualizar()

    # ==========================================================
    # ESPECIAL
    # ==========================================================

    def especial(self):

        total = random.randint(90, 130)

        self.enemy.recibir_dano(total)

        self.escribir(
            f"✨ ATAQUE COMBINADO causa {total} daño"
        )

        self.flash_enemy("purple")
        self.shake_enemy()

        self.turno_enemy()
        self.verificar()
        self.actualizar()

    # ==========================================================
    # CURAR
    # ==========================================================

    def curar(self):

        herido = min(self.heroes, key=lambda x: x.hp)

        heal = 45
        herido.hp = min(herido.hp_max, herido.hp + heal)

        self.escribir(
            f"💚 Burguesía cura a {herido.nombre} +{heal}"
        )

        self.flash_enemy("green")

        self.turno_enemy()
        self.actualizar()

    # ==========================================================
    # TURNO ENEMIGO
    # ==========================================================

    def turno_enemy(self):

        vivos = [h for h in self.heroes if h.vivo()]

        if vivos and self.enemy.vivo():

            obj = random.choice(vivos)

            dmg = random.randint(20, 38)

            obj.recibir_dano(dmg)

            self.escribir(
                f"👑 El Caudillo ataca a {obj.nombre} ({dmg})"
            )

    # ==========================================================
    # VERIFICAR
    # ==========================================================

    def verificar(self):

        if not self.enemy.vivo():

            messagebox.showinfo(
                "VICTORIA",
                "🎉 ¡Ganaste la batalla!"
            )

        elif all(not h.vivo() for h in self.heroes):

            messagebox.showerror(
                "DERROTA",
                "💀 Tu equipo fue destruido."
            )

    # ==========================================================
    # REINICIAR
    # ==========================================================

    def reiniciar(self):

        self.root.destroy()
        nuevo = tk.Tk()
        Juego(nuevo)
        nuevo.mainloop()


# ==============================================================
# MAIN
# ==============================================================

if __name__ == "__main__":

    root = tk.Tk()
    app = Juego(root)
    root.mainloop()
