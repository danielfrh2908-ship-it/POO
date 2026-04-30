from abc import ABC, abstractmethod

# ============================================================
# INTERFACES (simuladas con clases base)
# ============================================================

class Habilidoso(ABC):
    @abstractmethod
    def usar_habilidad_especial(self, objetivo):
        pass

    @abstractmethod
    def get_costo_habilidad(self):
        pass

    @abstractmethod
    def get_nombre_habilidad(self):
        pass


class Equipable(ABC):
    @abstractmethod
    def equipar_item(self, item):
        pass

    @abstractmethod
    def get_item_equipado(self):
        pass


class Sanador(ABC):
    @abstractmethod
    def sanar(self, objetivo):
        pass

    @abstractmethod
    def get_potencia_sanacion(self):
        pass


# ============================================================
# CLASE ABSTRACTA PERSONAJE
# ============================================================

class Personaje(ABC):

    def __init__(self, nombre, nivel, puntos_vida_max):
        self.nombre = nombre
        self.nivel = nivel
        self.puntos_vida_max = puntos_vida_max
        self.puntos_vida = puntos_vida_max

    def recibir_dano(self, dano):
        self.puntos_vida = max(0, self.puntos_vida - dano)
        print(f"  {self.nombre} recibe {dano} de daño → HP: {self.puntos_vida}/{self.puntos_vida_max}")

    def esta_vivo(self):
        return self.puntos_vida > 0

    def restaurar_vida(self, cantidad):
        self.puntos_vida = min(self.puntos_vida_max, self.puntos_vida + cantidad)

    @abstractmethod
    def atacar(self, objetivo):
        pass

    @abstractmethod
    def get_tipo_personaje(self):
        pass

    def __str__(self):
        return f"[{self.get_tipo_personaje():7}] {self.nombre:8} Nv.{self.nivel} | HP: {self.puntos_vida:3}/{self.puntos_vida_max:3}"


# ============================================================
# GUERRERO
# ============================================================

class Guerrero(Personaje, Habilidoso, Equipable):

    def __init__(self, nombre, nivel):
        super().__init__(nombre, nivel, 100 + nivel * 10)
        self.fuerza = 15 + nivel * 3
        self.defensa = 10 + nivel * 2
        self.item_equipado = "Sin equipo"
        self.costo_habilidad = 30

    def atacar(self, objetivo):
        dano = self.fuerza
        print(f"{self.nombre} golpea a {objetivo.nombre} con su espada!")
        objetivo.recibir_dano(dano)

    def get_tipo_personaje(self):
        return "Guerrero"

    def usar_escudo(self):
        print(f"{self.nombre} bloquea con su escudo (defensa {self.defensa}).")

    # Habilidoso
    def usar_habilidad_especial(self, objetivo):
        print(f"{self.nombre} usa [{self.get_nombre_habilidad()}] sobre {objetivo.nombre}!")
        objetivo.recibir_dano(50)

    def get_costo_habilidad(self):
        return self.costo_habilidad

    def get_nombre_habilidad(self):
        return "Golpe Devastador"

    # Equipable
    def equipar_item(self, item):
        self.item_equipado = item
        print(f"{self.nombre} equipa: {item}")

    def get_item_equipado(self):
        return self.item_equipado


# ============================================================
# MAGO
# ============================================================

class Mago(Personaje, Habilidoso, Sanador):

    def __init__(self, nombre, nivel):
        super().__init__(nombre, nivel, 60 + nivel * 5)
        self.mana_max = 80 + nivel * 10
        self.mana = self.mana_max

    def atacar(self, objetivo):
        if self.mana >= 20:
            dano = 25 + self.nivel * 5
            print(f"{self.nombre} lanza un hechizo sobre {objetivo.nombre}! (mana: {self.mana} → {self.mana - 20})")
            self.mana -= 20
            objetivo.recibir_dano(dano)
        else:
            print(f"{self.nombre} no tiene mana suficiente.")

    def get_tipo_personaje(self):
        return "Mago"

    def recuperar_mana(self, cantidad):
        self.mana = min(self.mana_max, self.mana + cantidad)
        print(f"{self.nombre} recupera {cantidad} de mana → {self.mana}/{self.mana_max}")

    # Habilidoso
    def usar_habilidad_especial(self, objetivo):
        if self.mana >= self.get_costo_habilidad():
            print(f"{self.nombre} lanza [{self.get_nombre_habilidad()}] sobre {objetivo.nombre}!")
            self.mana -= self.get_costo_habilidad()
            objetivo.recibir_dano(40)
        else:
            print(f"{self.nombre} no tiene mana para {self.get_nombre_habilidad()}.")

    def get_costo_habilidad(self):
        return 20

    def get_nombre_habilidad(self):
        return "Bola de Fuego"

    # Sanador
    def sanar(self, objetivo):
        curacion = self.get_potencia_sanacion()
        objetivo.restaurar_vida(curacion)
        print(f"{self.nombre} sana a {objetivo.nombre} por {curacion} HP → {objetivo.puntos_vida}/{objetivo.puntos_vida_max}")

    def get_potencia_sanacion(self):
        return 25


# ============================================================
# ARQUERO
# ============================================================

class Arquero(Personaje, Equipable):

    def __init__(self, nombre, nivel):
        super().__init__(nombre, nivel, 75 + nivel * 7)
        self.flechas = 10 + nivel * 2
        self.alcance = 30
        self.item_equipado = "Arco basico"

    def atacar(self, objetivo):
        if self.flechas > 0:
            dano = 12 + self.nivel * 4 + (0 if self.item_equipado == "Arco basico" else 5)
            print(f"{self.nombre} dispara una flecha a {objetivo.nombre}! (flechas restantes: {self.flechas - 1})")
            self.flechas -= 1
            objetivo.recibir_dano(dano)
        else:
            print(f"{self.nombre} no tiene flechas!")

    def get_tipo_personaje(self):
        return "Arquero"

    def recargar_flechas(self, cantidad):
        self.flechas += cantidad
        print(f"{self.nombre} recarga {cantidad} flechas → {self.flechas}")

    # Equipable
    def equipar_item(self, item):
        self.item_equipado = item
        print(f"{self.nombre} equipa: {item}")

    def get_item_equipado(self):
        return self.item_equipado


# ============================================================
# MAIN
# ============================================================

if __name__ == "__main__":

    print("========================================")
    print("       RPG MANAGER — Estado inicial     ")
    print("========================================")

    thorin = Guerrero("Thorin", 3)
    gandalf = Mago("Gandalf", 5)
    legolas = Arquero("Legolas", 4)

    print(thorin)
    print(gandalf)
    print(legolas)

    # Fase 1
    print("\n--- Fase 1: Equipando heroes ---")
    thorin.equipar_item("Espada Legendaria")
    legolas.equipar_item("Arco Elfico")

    # Fase 2
    print("\n--- Fase 2: Batalla ---")
    heroes = [thorin, gandalf, legolas]

    orco = Guerrero("Orco", 1)
    print("Enemigo:", orco)
    print()

    turno = 1
    while orco.esta_vivo():
        print(f"=== Turno {turno} ===")
        for h in heroes:
            if not orco.esta_vivo():
                break

            if turno == 2 and isinstance(h, Habilidoso):
                h.usar_habilidad_especial(orco)
            else:
                h.atacar(orco)

        turno += 1

    print(f"\nEl Orco fue derrotado en {turno - 1} turno(s).")

    # Fase 3
    print("\n--- Fase 3: Sanacion ---")

    mas_debil = min(heroes, key=lambda h: h.puntos_vida)

    print(f"Heroe con menos vida: {mas_debil.nombre} ({mas_debil.puntos_vida} HP)")

    for h in heroes:
        if isinstance(h, Sanador):
            h.sanar(mas_debil)

    print("\n========================================")
    print("Estado final del equipo")
    print("========================================")

    for h in heroes:
        print(h)

    print(orco)
