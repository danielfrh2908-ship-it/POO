import java.util.ArrayList; //Sirve para crear lostas dinamicas 
import java.util.List; // Define operaciones basicas de las listas 
import java.util.Scanner; //Permite leer datos del usuario
import java.util.Queue; //Representa una cola de datos 
import java.util.LinkedList;  //Crea listas enlazadas y tambien puede funcionar 

//Interfaz 
interface vendible{
    String getNombre();
    double getPrecio();
    void mostrarDetalle();
    boolean estaDisponible();  
}

//Clase abstracta 
abstract class producto implements vendible {
    protected string nombre; //Guarda el nombre del producto 
    protected double precio; //Guarda el precio del producto 
    protected int stock; //Guarda la cantidad disponible del producto 
    protected String vendedor; //Guarda el nombre del vendedor del producto

    public Producto(String nombre, double precio, //Constructor de de la clase producto 
                     int stock, String vendedor) {

        this.nombre = nombre; //Inicializa el nombre del producto
        this.precio = precio; //Inicializa el precio del producto
        this.stock = stock; //Inicializa el stock del producto
        this.vendedor = vendedor; //Inicializa el vendedor del producto
    }
       @Override //Implementacion de los metodos de la interfaz vendible
    public String getNombre() {//Retorna el nombre del producto
        return nombre;
    }
    @Override
    public double getPrecio() {//Retorna el precio del producto 
        return precio;
    }
    @Override
    public boolean estaDisponible() { //Verifica si el producto esta disponible
        return stock > 0;
    }
    // Reducir stock cuando se compra
    public void reducirStock() { //Reduce el stock del producto en 1 si hay stock disponible
        if (stock > 0) { //Verifica si hay stock disponible
            stock--;
        }
    }
    public double calcularPrecioConDescuento(double porcentaje) { //Calcula el precio con descuento 
        return precio - (precio * porcentaje / 100);// Retorna el precio con descuento
    }
    @Override // metodo para mostrar detalles del producto.
    public abstract void mostrarDetalle();

//Clase electronico 
//La clase Electronico hereda las características de Producto, por lo que puede usar datos como nombre, 
// precio, stock y vendedor. Además, agrega información propia de los productos electrónicos, como la 
// marca y la garantía.
//El constructor se encarga de inicializar todos los datos del producto, utilizando super() para enviar la 
// información básica a la clase padre. Finalmente, el método mostrarDetalle() muestra en pantalla toda la 
// información del producto electrónico de forma organizada.

    class Electronico extends Producto {
    private String marca;
    private int garantiaMeses;
    public Electronico(String nombre,
                       double precio,
                       int stock,
                       String vendedor,
                       String marca,
                       int garantiaMeses) {

        super(nombre, precio, stock, vendedor);
        this.marca = marca;
        this.garantiaMeses = garantiaMeses;
    }
    @Override
    public void mostrarDetalle() {
        System.out.println("\n ELECTRONICO ");
        System.out.println("Producto   : " + nombre);
        System.out.println("Marca      : " + marca);
        System.out.println("Precio     : $" + precio);
        System.out.println("Garantia   : "
                + garantiaMeses + " meses");
        System.out.println("Stock      : " + stock);
        System.out.println("Vendedor   : " + vendedor);
    }
}
//Clase Ropa 
class Ropa extends Producto {
    private String talla;
    private String color;
    public Ropa(String nombre,
                double precio,
                int stock,
                String vendedor,
                String talla,
                String color) {
        super(nombre, precio, stock, vendedor);
        this.talla = talla;
        this.color = color;
    }
    @Override
    public void mostrarDetalle() {
        System.out.println("\n=== ROPA ===");
        System.out.println("Producto   : " + nombre);
        System.out.println("Talla      : " + talla);
        System.out.println("Color      : " + color);
        System.out.println("Precio     : $" + precio);
        System.out.println("Stock      : " + stock);
        System.out.println("Vendedor   : " + vendedor);
    }
}
// Clase Usuario
class Usuario {
    private String nombre;
    private String correo;
    private String password;
    public Usuario(String nombre,
                   String correo,
                   String password) {
        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
    }
    public String getNombre() {
        return nombre;
    }
    public String getCorreo() {
        return correo;
    }
    // Login simple
    public boolean login(String correo, //Verifica si el correo y la contraseña coinciden con los datos del usuario
                         String password) {
        return this.correo.equals(correo) // retorna true si el correo y la contraseña son correctos.
                && this.password.equals(password);
    }
    public void mostrarUsuario() {
        System.out.println("\n USUARIO "); // Muestra los datos del usuario
        System.out.println("Nombre : " + nombre); // Muestra el nombre del usuario 
        System.out.println("Correo : " + correo); // Muestra el correo del usuario 
    }
}
//Clase lista de espera
class ListaEspera {
    private Queue<Usuario> cola = new LinkedList<>(); // Cola para manejar la lista de espera
    // Agregar usuario
    public void agregarUsuario(Usuario u) {
        cola.offer(u);
        System.out.println(u.getNombre()//Obtiene el nombre del usuario.
                + " fue agregado a la lista de espera.");
    }
    // Atender siguiente usuario
    public void atenderUsuario() {
        if (cola.isEmpty()) { //Verifica si la cola esta vacia
            System.out.println("No hay usuarios en espera.");
        } else {
            Usuario u = cola.poll();

            System.out.println("Atendiendo a: "
                    + u.getNombre());
        }
    }
    public void mostrarLista() { //Muestra la lista de espera 
        System.out.println("\n LISTA DE ESPERA ");
        if (cola.isEmpty()) {
            System.out.println("No hay usuarios en espera.");
            return;
        }
        for (Usuario u : cola) { //Itera sobre la cola y muestra el nombre de cada usuario en espera
            System.out.println("- " + u.getNombre());
        }
        System.out.println("\nTotal personas esperando: " // Muestra el total de personas en espera
                + cola.size());
    }
}
// Clase carrito 
class Carrito { // Maneja los productos que el cliente desea comprar
    private List<Producto> productos = new ArrayList<>();
    // HISTORIAL
    private List<String> historial = new ArrayList<>();
    private String clienteNombre;
    public Carrito(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }
    // Agregar producto
    public void agregar(Producto p) {
        if (p.estaDisponible()) {
            productos.add(p);
            historial.add(p.getNombre());
            p.reducirStock();
            System.out.println("'" + p.getNombre()
                    + "' agregado al carrito.");
        } else {
            System.out.println("No hay stock disponible.");
        }
    }
    // Eliminar producto
    public void eliminarProducto(int indice) { // Elimina un producto del carrito por su indice
        if (indice >= 0
                && indice < productos.size()) {
            Producto eliminado = productos.remove(indice);
            System.out.println(eliminado.getNombre()
                    + " eliminado del carrito.");
        } else {
            System.out.println("Indice invalido.");
        }
    }
    // Calcular total
    public double calcularTotal() { // Calcula el total del carrito sumando el precio de cada producto
        double total = 0;
        for (Producto p : productos) {
            total += p.getPrecio();
        }
        return total;
    }
    // Mostrar carrito
    public void mostrarResumen() { // Muestra el resumen 
        System.out.println("\n CARRITO DE "
                + clienteNombre + " ");
        if (productos.isEmpty()) {
            System.out.println("El carrito esta vacio.");
            return;
        }
        for (int i = 0; i < productos.size(); i++) { // Siclo para mostar el prodcuto.
            System.out.println("\nPOSICION "
                    + (i + 1));
            productos.get(i).mostrarDetalle();
            System.out.println("---------------------");
        }
        System.out.println("TOTAL: $"
                + calcularTotal());
    }
    // Mostrar historial
    public void mostrarHistorial() { // Muestra el historial de compras del cliente
        System.out.println("\n HISTORIAL ");
        if (historial.isEmpty()) {
            System.out.println("No hay compras.");
            return;
        }
        for (String producto : historial) {
            System.out.println("- " + producto);
        }
    }
}
// Clase principal
public class Marketplace { // Clase principal que contiene el método main para ejecutar el programa
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); // Scanner para leer datos del usuario
        // Estructuras de datos para usuarios, productos y lista de espera
        List<Usuario> usuarios = new ArrayList<>(); // Lista
        List<Producto> catalogo = new ArrayList<>(); // Lista
        ListaEspera espera = new ListaEspera(); // Lista de espera 
        // Productos 
        catalogo.add(new Electronico( //Agrega un producto electrónico al catálogo
                "Samsung Galaxy A55",
                850000,
                10,
                "TechStore Colombia",
                "Samsung",
                12
        ));
        catalogo.add(new Ropa(
                "Camiseta Nike Dri-Fit",
                95000,
                5,
                "SportZone",
                "M",
                "Negro"
        ));
        catalogo.add(new Electronico(
                "Lenovo IdeaPad 3",
                2300000,
                3,
                "TechStore Colombia",
                "Lenovo",
                24
        ));
        // Registro
        System.out.println("REGISTRO "); // Solicita al usuario que ingrese sus datos para registrarse
        System.out.print("Ingrese nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Ingrese correo: ");
        String correo = sc.nextLine();
        System.out.print("Ingrese contraseña: ");
        String password = sc.nextLine();
        Usuario usuario = new Usuario(
                nombre,
                correo,
                password
        );
        usuarios.add(usuario);
        System.out.println("\nUsuario registrado.");
        // Login 
        System.out.println("\n LOGIN ");
        System.out.print("Correo: ");
        String loginCorreo = sc.nextLine();
        System.out.print("Contraseña: ");
        String loginPassword = sc.nextLine();
        if (usuario.login(loginCorreo,
                loginPassword)) {
            System.out.println("\nLogin exitoso.");
        } else {
            System.out.println("\nCorreo o contraseña incorrectos.");
            return;
        }
        // Carrito
        Carrito carrito = new Carrito(nombre); // crea un carrito para el usuario registrado 
        // Menu
        int opcion; // Variable para almacenar la opción seleccionada por el usuario en el menú
        do {
            System.out.println("\n MI MERCADO ");
            System.out.println("1. Ver productos");
            System.out.println("2. Agregar producto al carrito");
            System.out.println("3. Ver carrito");
            System.out.println("4. Ver usuarios registrados");
            System.out.println("5. Entrar a lista de espera");
            System.out.println("6. Ver lista de espera");
            System.out.println("7. Atender siguiente usuario");
            System.out.println("8. Eliminar producto carrito");
            System.out.println("9. Ver historial");
            System.out.println("10. Salir");

            System.out.print("\nSeleccione opcion: ");

            opcion = sc.nextInt();

            switch (opcion) {
                // Ver productos
                case 1: // 
                    System.out.println("\n CATALOGO "); // muestra el catalogo 
                    for (int i = 0;
                         i < catalogo.size();
                         i++) {
                        System.out.println("\nPRODUCTO #"
                                + (i + 1)); // Usando la poscion i y luego lo ejecuta 
                        catalogo.get(i).mostrarDetalle();
                    }
                    break;
                // Agregar producto 
                case 2:
                    System.out.println("\nSeleccione producto:");
                    for (int i = 0;
                         i < catalogo.size();
                         i++) {
                        System.out.println((i + 1)
                                + ". "
                                + catalogo.get(i).getNombre());
                    }
                    int seleccion = sc.nextInt();
                    if (seleccion > 0
                            && seleccion <= catalogo.size()) {
                        carrito.agregar(
                                catalogo.get(seleccion - 1));
                    } else {
                        System.out.println("Opcion invalida.");
                    }
                    break;
                // Ver carrito
                case 3:
                    carrito.mostrarResumen(); // 
                    break;
                // Ver usuarios
                case 4:
                    System.out.println("\n USUARIOS ");
                    for (Usuario u : usuarios) {
                        u.mostrarUsuario();
                    }
                    break;
                // Lista de espera 
                case 5:
                    espera.agregarUsuario(usuario);
                    break;
                // Mostrar espera
                case 6:
                    espera.mostrarLista();
                    break;
                // Atender usuario
                case 7:
                    espera.atenderUsuario();
                    break;
                // Eliminar producto
                case 8:
                    carrito.mostrarResumen();
                    System.out.print(
                            "\nIngrese posicion a eliminar: ");
                    int eliminar = sc.nextInt();
                    carrito.eliminarProducto(eliminar - 1);
                    break;
                // Historial
                case 9:
                    carrito.mostrarHistorial();
                    break;
                // Salir
                case 10:
                    System.out.println(
                            "\nGracias por usar MiMercado.");
                    break;
                default:
                    System.out.println(
                            "\nOpcion invalida.");
            }
        } while (opcion != 10);
        sc.close();
    }
}
}


