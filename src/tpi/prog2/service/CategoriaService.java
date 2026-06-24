package tpi.prog2.service;

import java.util.List;
import tpi.prog2.entities.Categoria;
import tpi.prog2.menu.InputReader;

public class CategoriaService {

    public static Categoria crear(List<Categoria> lista) {
        String nombre = pedirNombreUnico(lista);
        String descripcion = InputReader.leerCadena("Ingrese la descripcion de \"" + nombre + "\" :");
        return crear(nombre, descripcion);
    }

    public static Categoria crear(String nombre, String descripcion) {
        return new Categoria(nombre, descripcion);
    }

    public static void actualizar(List<Categoria> lista) {
        boolean volver = true;
        String menu = """
                        1. Actualizar Nombre.
                        2. Actualizar Descripcion.
                        3. Volver al menu principal.
                        """;
        Categoria c = lista.get(obtnerIndex(lista));
        do {
            int opcion = InputReader.leerIntEnRango(menu, "ERROR.. El dato debe ser numerico", 1, 3);
            switch (opcion) {
                case 1:
                    System.out.println("["+c.getNombre()+"]");
                    String nombre = pedirNombreUnico(lista);
                    c.setNombre(nombre);
                    System.out.println("---Actualizacion exitosa---");
                    break;
                case 2:
                    System.out.println("["+c.getDescripcion()+"]");
                    String descripcion = InputReader.leerCadena("Ingrese la descripcion de \"" + c.getNombre() + "\" :");
                    c.setDescripcion(descripcion);
                    System.out.println("---Actualizacion exitosa---");
                    break;
                default:
                    volver = false;
            }    
        } while (volver);       
    }

    private static boolean existeNombre(List<Categoria> lista, String nombre) {
        for (Categoria categoria : lista) {
            if (categoria.getNombre().equalsIgnoreCase(nombre.trim())) {
                return true;
            }
        }
        return false;
    }

    private static String pedirNombreUnico(List<Categoria> lista) {
        String nombre = null;
        boolean existe = false;
        do {
            nombre = InputReader.leerCadena("Ingrese el nombre de la categoria: ");
            existe = existeNombre(lista, nombre);
            if (existe) {
                System.out.println("ERROR!!! El nombre ya existe.");
            }
        } while (existe);
        return nombre;
    }

    public static int obtnerIndex(List<Categoria> lista) {
        int largo = lista.size();
        for (int i = 0; i < largo; i++) {
            System.out.println((i + 1) + lista.get(i).info());
        }
        return InputReader.leerIntEnRango("Seleccione el numero de categoria que quiere actualizar: ",
                "ERROR... El dato debe ser numerico", 1, largo) - 1;
    }

}
