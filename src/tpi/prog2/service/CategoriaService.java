package tpi.prog2.service;

import java.util.List;
import tpi.prog2.entities.Categoria;
import tpi.prog2.menu.InputReader;

public class CategoriaService {

    public static void crear(List<Categoria> lista) {
        String nombre = pedirNombreUnico(lista);
        String descripcion = InputReader.leerCadena("Ingrese la descripcion de \"" + nombre + "\" :");
        lista.add(crear(nombre, descripcion));
        System.out.println(">>> Creacion exitosa <<<");
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
        Categoria c = lista.get(obtnerIndex(lista, "actualizar"));
        do {
            int opcion = InputReader.leerIntEnRango(menu, "ERROR.. El dato debe ser numerico", 1, 3);
            switch (opcion) {
                case 1:
                    System.out.println("[" + c.getNombre() + "]");
                    String nombre = pedirNombreUnico(lista);
                    c.setNombre(nombre);
                    System.out.println("---Actualizacion exitosa---");
                    break;
                case 2:
                    System.out.println("[" + c.getDescripcion() + "]");
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

    public static int obtnerIndex(List<Categoria> lista, String accion) {
        int largo = lista.size();
        for (int i = 0; i < largo; i++) {
            Categoria c = lista.get(i);
            if (!c.isEliminado()) {
                System.out.println((i + 1) + c.info());
            }
        }
        return InputReader.leerIntEnRango("Seleccione el numero de categoria que quiere " + accion + ": ",
                "ERROR... El dato debe ser numerico", 1, largo) - 1;
    }

    public static void eliminar(List<Categoria> lista) {
        int index = obtnerIndex(lista, "eliminar");
        Categoria c = lista.get(index);
        boolean tieneActivos = c.tieneProductosActivos();
        System.out.println("[" + c.info() + "]");
        if (!tieneActivos) {
            int borrar = InputReader.leerIntEnRango("Seguro que desea eliminar esta categoria? \n1.SI\n2.NO",
                    "ERROR.. El dato debe ser numerico.", 1, 2);
            if (borrar == 1) {
                c.setEliminado(true);
                System.out.println("---Eliminacion exitosa---");
            }
        }else{
            System.out.println(">>> No se puede eliminar ya que tiene productos activos asociados <<<");
        }

    }

}
