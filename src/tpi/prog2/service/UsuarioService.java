package tpi.prog2.service;

import java.util.List;
import tpi.prog2.entities.Usuario;
import tpi.prog2.enums.Rol;
import tpi.prog2.menu.InputReader;

public class UsuarioService {

    public static Usuario crear(List<Usuario> lista) {
        String nombre = InputReader.leerCadena("Ingrese el nombre: ");
        String apellido = InputReader.leerCadena("Ingrese el apellido: ");
        String mail;
        boolean existe = false;
        String celular = InputReader.leerTelefono("Ingrese el numero de celular: ");
        String contrasenia = InputReader.leerCadena("Ingrese la contraseña: ");
        Rol rol = InputReader.leerRol();
         do {
            mail = InputReader.leerEmail("Ingrese el mail: ");
            existe = existeMail(lista, mail);
            if(existe){
                System.out.println("ERROR!!! El mail ya existe.");
            }
        } while (existe);
         return crear(nombre, apellido, mail, celular, contrasenia, rol);
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
}
