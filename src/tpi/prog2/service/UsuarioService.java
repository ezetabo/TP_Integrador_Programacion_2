package tpi.prog2.service;

import java.util.ArrayList;
import java.util.List;
import tpi.prog2.entities.Usuario;
import tpi.prog2.enums.Rol;
import tpi.prog2.exception.ServiceException;
import tpi.prog2.utils.InputReader;

public class UsuarioService {

    public static void crear(List<Usuario> lista) {
        try {
            String nombre = InputReader.leerCadena("Ingrese el nombre: ");
            String apellido = InputReader.leerCadena("Ingrese el apellido: ");
            String mail = pedirMailUnico(lista);
            String celular = InputReader.leerTelefono("Ingrese el numero de celular: ");
            String contrasenia = InputReader.leerCadena("Ingrese la contraseña: ");
            Rol rol = InputReader.leerRol();
            lista.add(crear(nombre, apellido, mail, celular, contrasenia, rol));
            System.out.println(">>> Creacion exitosa <<<");
        } catch (Exception e) {
            throw new ServiceException("Error al intentar crear un usuario: " + e.getMessage());
        }

    }

    public static Usuario crear(String nombre, String apellido, String mail,
            String celular, String contrasenia, Rol rol) {
        return new Usuario(nombre, apellido, mail, celular, contrasenia, rol);
    }

    public static Usuario crearAdmin(){
        return crear("Admin", "Admin", "admin@mail.com", "1122334455", "admin123", Rol.ADMIN);
    }
    public static boolean existeMail(List<Usuario> lista, String mail) {
        for (Usuario usuario : lista) {
            if (usuario.getMail().equalsIgnoreCase(mail.trim())) {
                return true;
            }
        }
        return false;
    }

    public static Usuario obtenerUnoPorMail(List<Usuario> lista, String mail) {
        for (Usuario usuario : lista) {
            if (usuario.getMail().equalsIgnoreCase(mail)) {
                return usuario;
            }
        }
        return null;
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

    public static Usuario obtenerUno(List<Usuario> lista, String accion) {
        List<Usuario> activos = new ArrayList<>();
        for (Usuario usuario : lista) {
            if (!usuario.isEliminado()) {
                activos.add(usuario);
                System.out.println(activos.size() + ". " + usuario.info());
            }
        }
        int index = InputReader.leerIntEnRango("Seleccione el usuario que quiere " + accion + ": ",
                "ERROR... El dato debe ser numérico.", 1, activos.size()) - 1;
        return activos.get(index);
    }

    public static void actualizar(List<Usuario> lista) {
        try {
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
            Usuario u = obtenerUno(lista, "actualizar");
            do {
                int opcion = InputReader.leerIntEnRango(menu, "ERROR.. El dato debe ser numerico", 0, 6);
                switch (opcion) {
                    case 1:
                        System.out.println("ACTUAL: [" + u.getNombre() + "]");
                        u.setNombre(InputReader.leerCadena("Ingrese el nuevo nombre: "));
                        System.out.println("---Actualizacion exitosa---");
                        break;
                    case 2:
                        System.out.println("ACTUAL: [" + u.getApellido() + "]");
                        u.setApellido(InputReader.leerCadena("Ingrese el apellido: "));
                        System.out.println("---Actualizacion exitosa---");
                        break;
                    case 3:
                        System.out.println("ACTUAL: [" + u.getMail() + "]");
                        u.setMail(pedirMailUnico(lista));
                        System.out.println("---Actualizacion exitosa---");
                        break;
                    case 4:
                        System.out.println("ACTUAL: [" + u.getCelular() + "]");
                        u.setCelular(InputReader.leerTelefono("Ingrese el numero de celular: "));
                        System.out.println("---Actualizacion exitosa---");
                        break;
                    case 5:
                        System.out.println("ACTUAL: [" + u.getContrasenia() + "]");
                        u.setContrasenia(InputReader.leerCadena("Ingrese la nueva contraseña: "));
                        System.out.println("---Actualizacion exitosa---");
                        break;
                    case 6:
                        System.out.println("ACTUAL: [" + u.getRol() + "]");
                        u.setRol(InputReader.leerRol());
                        System.out.println("---Actualizacion exitosa---");
                        break;
                    default:
                        volver = false;
                }
            } while (volver);
        } catch (Exception e) {
            throw new ServiceException("Error al intentar actualizar el usuario: " + e.getMessage());
        }

    }

    public static void eliminar(List<Usuario> lista) {
        Usuario u = obtenerUno(lista, "eliminar");
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
                System.out.println(elemento.infoConListado());
            }
        }
    }

}
