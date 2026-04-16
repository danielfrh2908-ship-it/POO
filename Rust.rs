use std::io; // Importamos la librería para entrada/salida

fn main() {
    // Mostramos un mensaje al usuario
    println!("Ingresa una contraseña:");

    // Creamos una variable mutable para guardar la contraseña
    let mut password = String::new();

    // Leemos lo que el usuario escribe en consola
    io::stdin()
        .read_line(&mut password)
        .expect("Error al leer la entrada");

    // Eliminamos espacios o saltos de línea (\n)
    let password = password.trim();

    // Llamamos a la función que valida la contraseña
    if validar_password(password) {
        println!("✅ Contraseña segura");
    } else {
        println!("❌ Contraseña insegura");
    }
}

// Función que valida si la contraseña cumple las reglas
fn validar_password(pass: &str) -> bool {
    // Verifica si hay al menos una letra mayúscula
    let tiene_mayuscula = pass.chars().any(|c| c.is_uppercase());

    // Verifica si hay al menos un número
    let tiene_numero = pass.chars().any(|c| c.is_numeric());

    // Verifica que tenga mínimo 8 caracteres
    let longitud_valida = pass.len() >= 8;

    // Retorna true solo si TODAS las condiciones se cumplen
    tiene_mayuscula && tiene_numero && longitud_valida
}
