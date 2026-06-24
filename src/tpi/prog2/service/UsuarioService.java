package tpi.prog2.service;

import java.util.List;
import tpi.prog2.entities.Usuario;
import tpi.prog2.enums.Rol;
import tpi.prog2.menu.InputReader;

public class UsuarioService {

    public static void crear(List<Usuario> lista) {
        String nombre = InputReader.leerCadena("Ingrese el nombre: ");
        String apellido = InputReader.leerCadena("Ingrese el apellido: ");
        String mail = pedirMailUnico(lista);
        String celular = InputReader.leerTelefono("Ingrese el numero de celular: ");
        String contrasenia = InputReader.leerCadena("Ingrese la contraseña: ");
        Rol rol = InputReader.leerRol();
        lista.add(crear(nombre, apellido, mail, celular, contrasenia, rol));
        System.out.println(">>> Creacion exitosa <<<");
    }

    public static Usuario crear(String nombre, String apellido, String mail,
            String celular, String contrasenia, Rol rol) {
        return new Usuario(nombre, apellido, mail, celular, contrasenia, rol);
    }

    private static boolean existeMail(List<Usuario> lista, String nombre) {
        for (Usuario usuario : lista) {
            if (usuario.getNombre().equalsIgnoreCase(nombre.trim())) {
                return true;
            }
        }
        return false;
    }

    private static String pedirMailUnico(List<Usuario> lista) {
        String mail = null;
        boolean existe = false;
        do {
            mail = InputReader.leerEmail("Ingrese el mail: ");
            existe = existeMail(lista, mail);
            if (existe) {
                System.out.println("ERROR!!! El mail ya existe.");
            }
        } while (existe);
        return mail;
    }

    public static int obtnerIndex(List<Usuario> lista, String accion) {
        int largo = lista.size();
        for (int i = 0; i < largo; i++) {
            Usuario u = lista.get(i);
            if (!u.isEliminado()) {
                System.out.println((i + 1) + u.info());
            }
        }
        return InputReader.leerIntEnRango("Seleccione el numero de usuario que quiere " + accion + ": ",
                "ERROR... El dato debe ser numerico", 1, largo) - 1;
    }

    public static Usuario obtnerUno(List<Usuario> lista, String accion) {
        int largo = lista.size();
        for (int i = 0; i < largo; i++) {
            Usuario u = lista.get(i);
            if (!u.isEliminado()) {
                System.out.println((i + 1) + u.info());
            }
        }
        int index = InputReader.leerIntEnRango("Seleccione el numero de usuario que quiere " + accion + ": ",
                "ERROR... El dato debe ser numerico", 1, largo) - 1;
        return lista.get(index);
    }

    public static void actualizar(List<Usuario> lista) {
        boolean volver = true;
        String menu = """
                        1. Actualizar Nombre.
                        2. Actualizar Apellido.
                        3. Actualizar Mail.
                        4. Actualizar Celular.
                        5. Actualizar Contraseña.
                        6. Actualizar Rol.
                        0. Volver al menu de Usuarios.
                        Seleccione: 
                        """;
        Usuario u = lista.get(obtnerIndex(lista, "actualizar"));
        do {
            int opcion = InputReader.leerIntEnRango(menu, "ERROR.. El dato debe ser numerico", 0, 6);
            switch (opcion) {
                case 1:
                    System.out.println("[" + u.getNombre() + "]");
                    String nombre = InputReader.leerCadena("Ingrese el nuevo nombre: ");
                    u.setNombre(nombre);
                    System.out.println("---Actualizacion exitosa---");
                    break;
                case 2:
                    System.out.println("[" + u.getApellido() + "]");
                    String apellido = InputReader.leerCadena("Ingrese el apellido: ");
                    u.setApellido(apellido);
                    System.out.println("---Actualizacion exitosa---");
                    break;
                case 3:
                    System.out.println("[" + u.getMail() + "]");
                    String mail = pedirMailUnico(lista);
                    u.setMail(mail);
                    System.out.println("---Actualizacion exitosa---");
                    break;
                case 4:
                    System.out.println("[" + u.getCelular() + "]");
                    String celular = InputReader.leerTelefono("Ingrese el numero de celular: ");

                    System.out.println("---Actualizacion exitosa---");
                    break;
                case 5:
                    System.out.println("[" + u.getContrasenia() + "]");
                    String contrasenia = InputReader.leerCadena("Ingrese la nueva contraseña: ");
                    u.setContrasenia(contrasenia);
                    System.out.println("---Actualizacion exitosa---");
                    break;
                case 6:
                    System.out.println("[" + u.getRol() + "]");
                    Rol rol = InputReader.leerRol();
                    u.setRol(rol);
                    System.out.println("---Actualizacion exitosa---");
                    break;
                default:
                    volver = false;
            }
        } while (volver);
    }

    public static void eliminar(List<Usuario> lista) {
        int index = obtnerIndex(lista, "eliminar");
        Usuario u = lista.get(index);
        System.out.println("[" + u.info() + "]");

        int borrar = InputReader.leerIntEnRango("Seguro que desea eliminar este usuario? \n1.SI\n2.NO\nSeleccione: ",
                "ERROR.. El dato debe ser numerico.", 1, 2);
        if (borrar == 1) {
            u.setEliminado(true);
            System.out.println("---Eliminacion exitosa---");
        } else {
            System.out.println("---Eliminacion cancelada---");
        }
    }

    public static void listarConListado(List<Usuario> lista) {
        for (Usuario elemento : lista) {
            if (!elemento.isEliminado()) {
                System.out.print(elemento.infoConListado());
            }
        }
    }
}
