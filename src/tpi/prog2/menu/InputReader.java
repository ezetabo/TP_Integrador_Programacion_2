package tpi.prog2.menu;

import java.util.Scanner;
import tpi.prog2.enums.Estado;
import tpi.prog2.enums.FormaPago;
import tpi.prog2.enums.Rol;

/**
 * Clase utilitaria para la lectura y validación de datos desde consola. 
 * Centraliza la lógica de entrada para evitar duplicación de código.
 */
public class InputReader {

    private static final Scanner sc = new Scanner(System.in);

    /**
     * Solicita al usuario un número decimal (double) desde consola. 
     * Permite ingresar valores utilizando coma o punto como separador decimal. 
     * Reintenta hasta que el valor ingresado sea válido.
     *
     * @param mensaje Mensaje que se muestra al usuario para solicitar el dato.
     * @param mensajeError Mensaje que se muestra en caso de error de formato.
     * @return número decimal válido ingresado por el usuario.
     */
    public static double leerDouble(String mensaje, String mensajeError) {
        double numero = 0;
        boolean datoValido = false;

        do {
            try {
                String textoIngresado = leerCadena(mensaje);
                String textoNormalizado = textoIngresado.replace(',', '.');
                numero = Double.parseDouble(textoNormalizado);
                datoValido = true;
            } catch (NumberFormatException e) {
                System.out.println(mensajeError);
            }
        } while (!datoValido);

        return numero;
    }

    /**
     * Solicita al usuario un CUIT válido. El CUIT debe contener exactamente 11 dígitos numéricos sin guiones ni espacios. 
     * Reintenta hasta que el valor ingresado cumpla con el formato requerido.
     *
     * @return cadena con el CUIT válido ingresado por el usuario.
     */
    public static String leerCUIT() {
        String cuit = "";
        boolean valido = false;

        do {
            cuit = leerCadena("Ingrese CUIT (11 números sin guiones): ");

            if (cuit.matches("[0-9]{11}")) {
                valido = true;
            } else {
                System.out.println("Error: El CUIT debe tener 11 dígitos numéricos.");
            }
        } while (!valido);

        return cuit;
    }

    /**
     * Solicita al usuario un correo electrónico con validación básica. 
     * El email debe contener el carácter '@' y no debe tener espacios. 
     * Reintenta hasta que el formato sea válido.
     *
     * @return cadena con el email válido ingresado por el usuario.
     */
    public static String leerEmail() {
        String email = "";
        boolean emailValido = false;

        do {
            email = leerCadena("Ingrese su Email: ");

            if (email.contains("@") && !email.contains(" ")) {
                emailValido = true;
            } else {
                System.out.println("Error: Formato de correo inválido. Asegúrese de incluir '@' y no usar espacios.");
            }
        } while (!emailValido);

        return email;
    }

    /**
     * Solicita al usuario un número de teléfono en formato internacional argentino. 
     * Formato requerido: +54 9 XXXXXXXXXX (ejemplo: +54 9 2611234567). 
     * Reintenta hasta que el valor cumpla con el patrón definido.
     *
     * @return cadena con el teléfono válido ingresado por el usuario.
     */
    public static String leerTelefono() {
        String telefono = "";
        String patron = "^\\+54 9 \\d{10}$";

        do {
            System.out.println("Formato requerido: +54 9 2611234567 (ejemplo para Mendoza)");
            telefono = leerCadena("Ingrese Teléfono: ");

            if (telefono.matches(patron)) {
                break;
            }

            System.out.println("Error: Por favor, respete el formato internacional (+54 9 XXXXXXXXXX).");

        } while (true);

        return telefono;
    }

    /**
     * Solicita al usuario una cadena de texto no vacía. Elimina espacios en blanco al inicio y al final. 
     * Reintenta hasta que el usuario ingrese un valor válido.
     *
     * @param mensaje Mensaje que se muestra al usuario para solicitar el dato.
     * @return cadena no vacía ingresada por el usuario.
     */
    public static String leerCadena(String mensaje) {
        String texto = "";
        boolean datoValido = false;

        do {
            System.out.print(mensaje);
            texto = sc.nextLine().trim();

            if (texto.isEmpty()) {
                System.out.println("Debe ingresar un valor");
                continue;
            }

            datoValido = true;

        } while (!datoValido);

        return texto;
    }

    /**
     * Solicita al usuario un número entero (int) desde consola. Reintenta hasta que el valor ingresado sea un entero válido.
     *
     * @param mensaje Mensaje que se muestra al usuario para solicitar el dato.
     * @param mensajeError Mensaje que se muestra en caso de error de formato.
     * @return número entero válido ingresado por el usuario.
     */
    public static int leerInt(String mensaje, String mensajeError) {
        int numero = 0;
        boolean datoValido = false;

        do {
            try {
                String textoIngresado = leerCadena(mensaje);
                numero = Integer.parseInt(textoIngresado);
                datoValido = true;
            } catch (NumberFormatException e) {
                System.out.println(mensajeError);
            }
        } while (!datoValido);

        return numero;
    }

    /**
     * Solicita al usuario un número entero (int) denrto de un rango desde consola. 
     * Reintenta hasta que el valor ingresado sea un entero válido.
     *
     * @param mensaje Mensaje que se muestra al usuario para solicitar el dato.
     * @param mensajeError Mensaje que se muestra en caso de error de formato.
     * @param minimo valor minimo esperado(incluido).
     * @param maximo valor maximo esperado(incluido).
     * @return número entero válido ingresado por el usuario dentro del rango esperado.
     */
    public static int leerIntEnRango(String mensaje, String mensajeError, int minimo, int maximo) {
        int numero = 0;
        boolean datoValido = false;

        do {
            try {
                String textoIngresado = leerCadena(mensaje);
                numero = Integer.parseInt(textoIngresado);
                if (numero < minimo || numero > maximo) {
                    System.out.printf("El valor debe estar entre %d y %d.", minimo, maximo);
                    continue;
                }
                datoValido = true;
            } catch (NumberFormatException e) {
                System.out.println(mensajeError);
            }
        } while (!datoValido);

        return numero;
    }

    /**
     * Solicita al usuario un número decimal (double) dentro de un rando deseado desde consola. 
     * Permite ingresar valores utilizando coma o punto como separador decimal. 
     * Reintenta hasta que el valor ingresado sea válido.
     *
     * @param mensaje Mensaje que se muestra al usuario para solicitar el dato.
     * @param mensajeError Mensaje que se muestra en caso de error de formato.
     * @param minimo valor minimo esperado(incluido).
     * @param maximo valor maximo esperado(incluido).
     * @return número decimal válido ingresado por el usuario dentro del rango esperado.
     */
    public static double leerDoubleEnRango(String mensaje, String mensajeError, double minimo, double maximo) {
        double numero = 0;
        boolean datoValido = false;

        do {
            try {
                String textoIngresado = leerCadena(mensaje);
                String textoNormalizado = textoIngresado.replace(',', '.');
                numero = Double.parseDouble(textoNormalizado);
                if (numero < minimo || numero > maximo) {
                    System.out.printf("El valor debe estar entre %f y %f.", minimo, maximo);
                    continue;
                }
                datoValido = true;
            } catch (NumberFormatException e) {
                System.out.println(mensajeError);
            }
        } while (!datoValido);

        return numero;
    }

    public static Long leerId(String mensaje) {
        int id = InputReader.leerIntEnRango(mensaje, "Ingrese un ID válido.", 1, Integer.MAX_VALUE);
        return (long) id;
    }

    public static boolean confirmar(String mensaje) {
        String respuesta;
        do {
            respuesta = InputReader.leerCadena(mensaje).trim().toUpperCase();
            if (!respuesta.equals("S") && !respuesta.equals("N")) {
                System.out.println("Ingrese S o N.");
            }
        } while (!respuesta.equals("S") && !respuesta.equals("N"));
        return respuesta.equals("S");
    }

    public static Rol leerRol() {
        String menu = """
                1. ADMIN
                2. USUARIO
                Rol: """;
        int opcion = InputReader.leerIntEnRango(menu, "Ingrese una opción válida.", 1, 2);

        switch (opcion) {
            case 1:
                return Rol.ADMIN;
            case 2:
                return Rol.USUARIO;
            default:
                throw new IllegalArgumentException("Rol inválido.");
        }
    }

    public static Estado leerEstado() {
        String menu = """
                1. PENDIENTE
                2. CONFIRMADO
                3. TERMINADO
                4. CANCELADO
                Estado: """;
        int opcion = InputReader.leerIntEnRango(menu, "Ingrese una opción válida.", 1, 4);

        switch (opcion) {
            case 1:
                return Estado.PENDIENTE;
            case 2:
                return Estado.CONFIRMADO;
            case 3:
                return Estado.TERMINADO;
            case 4:
                return Estado.CANCELADO;
            default:
                throw new IllegalArgumentException("Estado inválido.");
        }
    }

    public static FormaPago leerFormaPago() {
        String menu = """
                1. TARJETA
                2. TRANSFERENCIA
                3. EFECTIVO
                Forma de pago: """;
        int opcion = InputReader.leerIntEnRango(menu, "Ingrese una opción válida.", 1, 3);

        switch (opcion) {
            case 1:
                return FormaPago.TARJETA;
            case 2:
                return FormaPago.TRANSFERENCIA;
            case 3:
                return FormaPago.EFECTIVO;
            default:
                throw new IllegalArgumentException("Forma de pago inválida.");
        }
    }

    public static void mostrarError(RuntimeException e) {
        System.out.println("ERROR: " + e.getMessage());
    }
}
