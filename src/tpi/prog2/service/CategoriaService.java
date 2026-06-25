package tpi.prog2.service;

import java.util.ArrayList;
import java.util.List;
import tpi.prog2.entities.Categoria;
import tpi.prog2.exception.ServiceException;
import tpi.prog2.utils.InputReader;

public class CategoriaService {

    public static void crear(List<Categoria> lista) {
        try {
            String nombre = pedirNombreUnico(lista);
            String descripcion = InputReader.leerCadena("Ingrese la descripcion de \"" + nombre + "\" :");
            lista.add(crear(nombre, descripcion));
            System.out.println(">>> Creacion exitosa <<<");
        } catch (Exception e) {
            throw new ServiceException("Error al intentar crear una categoria: " + e.getMessage());
        }
    }

    public static Categoria crear(String nombre, String descripcion) {
        return new Categoria(nombre, descripcion);
    }

    public static void actualizar(List<Categoria> lista) {
        try {
            boolean volver = true;
            String menu = """
                        1. Actualizar Nombre.
                        2. Actualizar Descripcion.
                        0. Volver al menu de categorias.
                        Seleccione: 
                        """;
            Categoria c = obtnerUno(lista, "actualizar");
            do {
                int opcion = InputReader.leerIntEnRango(menu, "ERROR.. El dato debe ser numerico", 0, 2);
                switch (opcion) {
                    case 1:
                        System.out.println("ACTUAL: [" + c.getNombre() + "]");
                        c.setNombre(pedirNombreUnico(lista));
                        System.out.println("---Actualizacion exitosa---");
                        break;
                    case 2:
                        System.out.println("ACTUAL: [" + c.getDescripcion() + "]");
                        c.setDescripcion(InputReader.leerCadena("Ingrese la descripcion de \"" + c.getNombre() + "\" :"));
                        System.out.println("---Actualizacion exitosa---");
                        break;
                    default:
                        volver = false;
                }
            } while (volver);
        } catch (Exception e) {
            throw new ServiceException("Error al intentar actualizar la categoria: " + e.getMessage());
        }
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

    public static Categoria obtnerUno(List<Categoria> lista, String accion) {
        List<Categoria> activos = new ArrayList<>();
        for (Categoria c : lista) {
            if (!c.isEliminado()) {
                activos.add(c);
                System.out.println(activos.size() + c.info());
            }
        }
        int index = InputReader.leerIntEnRango("Seleccione el numero de categoria que quiere " + accion + ": ",
                "ERROR... El dato debe ser numerico", 1, activos.size()) - 1;
        return activos.get(index);
    }

    public static void eliminar(List<Categoria> lista) {
        Categoria c = obtnerUno(lista, "eliminar");
        boolean tieneActivos = c.tieneProductosActivos();
        System.out.println("[" + c.info() + "]");
        if (!tieneActivos) {
            int borrar = InputReader.leerIntEnRango("Seguro que desea eliminar esta categoria? \n1.SI\n2.NO\nSeleccione: ",
                    "ERROR.. El dato debe ser numerico.", 1, 2);
            if (borrar == 1) {
                c.setEliminado(true);
                System.out.println("---Eliminacion exitosa---");
            } else {
                System.out.println("---Eliminacion cancelada---");
            }
        } else {
            System.out.println(">>> No se puede eliminar ya que tiene productos activos asociados <<<");
        }
    }

    public static void listarConListado(List<Categoria> lista) {
        for (Categoria elemento : lista) {
            if (!elemento.isEliminado()) {
                System.out.println(elemento.infoConListado());
            }
        }
    }

}
