package tpi.prog2.service;

import java.util.ArrayList;
import java.util.List;
import tpi.prog2.entities.Categoria;
import tpi.prog2.entities.Producto;
import tpi.prog2.exception.ServiceException;
import tpi.prog2.utils.InputReader;

public class ProductoService {

    public static void crear(List<Producto> lista, List<Categoria> categorias) {
        try {
            String nombre = pedirNombreUnico(lista);
            double precio = InputReader.leerDoubleEnRango("Ingrese el precio de \"" + nombre + "\": ", "Error!! El precio debe ser numerido",
                    0, Double.MAX_VALUE);
            String descripcion = InputReader.leerCadena("Ingrese la descripcion de \"" + nombre + "\": ");
            int stock = InputReader.leerIntEnRango("Ingrese el stock de \"" + nombre + "\": ", "Error!! El stock debe ser numerido",
                    0, Integer.MAX_VALUE);
            String imagen = InputReader.leerCadena("Ingrese el nombre de la imagen: ");
            Producto p = crear(nombre, precio, descripcion, stock, imagen);
            p.setCategoria(CategoriaService.obtnerUno(categorias, "asignar"));
            lista.add(p);
            System.out.println(">>> Creacion exitosa <<<");
        } catch (Exception e) {
            throw new ServiceException("Error al intentar crear un producto: " + e.getMessage());
        }

    }

    public static Producto crear(String nombre, Double precio, String descripcion, int stock, String imagen) {
        return new Producto(nombre, precio, descripcion, stock, imagen);
    }

    private static boolean existeNombre(List<Producto> lista, String nombre) {
        for (Producto producto : lista) {
            if (producto.getNombre().equalsIgnoreCase(nombre.trim())) {
                return true;
            }
        }
        return false;
    }

    private static String pedirNombreUnico(List<Producto> lista) {
        String nombre = null;
        boolean existe = false;
        do {
            nombre = InputReader.leerCadena("Ingrese el nombre del producto: ");
            existe = existeNombre(lista, nombre);
            if (existe) {
                System.out.println("ERROR!!! El nombre ya existe.");
            }
        } while (existe);
        return nombre;
    }

    public static Producto obtnerUno(List<Producto> lista, String accion) {
        List<Producto> activos = new ArrayList<>();
        for (Producto p : lista) {
            if (!p.isEliminado() && p.getStock() > 0) {
                activos.add(p);
                System.out.println(activos.size() + p.info());
            }
        }
        int index = InputReader.leerIntEnRango("Seleccione el numero de producto que quiere " + accion + ": ",
                "ERROR... El dato debe ser numerico", 1, activos.size()) - 1;
        return activos.get(index);
    }

    public static Producto obtnerUnoActivo(List<Producto> lista, String accion) {
        List<Producto> activos = new ArrayList<>();
        for (Producto p : lista) {
            if (!p.isEliminado()) {
                activos.add(p);
                System.out.println(activos.size() + p.info());
            }
        }
        int index = InputReader.leerIntEnRango("Seleccione el numero de producto que quiere " + accion + ": ",
                "ERROR... El dato debe ser numerico", 1, activos.size()) - 1;
        return activos.get(index);
    }

    public static void actualizar(List<Producto> lista, List<Categoria> categorias) {
        try {
            boolean volver = true;
            String menu = """
                        1. Actualizar Precio.
                        2. Actualizar Stock.
                        3. Actualizar Categoria.
                        0. Volver al menu de productos.
                        Seleccione: 
                        """;
            Producto p = obtnerUnoActivo(lista, "actualizar");
            do {
                int opcion = InputReader.leerIntEnRango(menu, "ERROR.. El dato debe ser numerico", 0, 3);
                switch (opcion) {
                    case 1:
                        System.out.println("ACTUAL: [" + p.getPrecio() + "]");
                        p.setPrecio(InputReader.leerDoubleEnRango("Ingrese el nuevo precio de \"" + p.getNombre()
                                + "\": ", "Error!! El precio debe ser numerido", 0, Double.MAX_VALUE));
                        System.out.println("---Actualizacion exitosa---");
                        break;
                    case 2:
                        System.out.println("ACTUAL: [" + p.getStock() + "]");
                        p.setStock(InputReader.leerIntEnRango("Ingrese el nuevo stock de \"" + p.getNombre() + "\": ",
                                "Error!! El stock debe ser numerido", 0, Integer.MAX_VALUE));
                        System.out.println("---Actualizacion exitosa---");
                        break;
                    case 3:
                        System.out.println("ACTUAL: [" + p.getCategoria().getNombre() + "]");
                        p.setCategoria(CategoriaService.obtnerUno(categorias, "asignar"));
                        System.out.println("---Actualizacion exitosa---");
                        break;
                    default:
                        volver = false;
                }
            } while (volver);
        } catch (Exception e) {
            throw new ServiceException("Error al intentar actualizar el producto: " + e.getMessage());
        }

    }

    public static void eliminar(List<Producto> lista) {
        Producto p = obtnerUnoActivo(lista, "eliminar");
        System.out.println("[" + p.info() + "]");

        int borrar = InputReader.leerIntEnRango("Seguro que desea eliminar este producto? \n1.SI\n2.NO\nSeleccione: ",
                "ERROR.. El dato debe ser numerico.", 1, 2);
        if (borrar == 1) {
            p.setEliminado(true);
            System.out.println("---Eliminacion exitosa---");
        } else {
            System.out.println("---Eliminacion cancelada---");
        }
    }

    public static boolean existeDisponible(List<Producto> lista) {
        for (Producto p : lista) {
            if (!p.isEliminado() && p.getStock() > 0) {
                return true;
            }
        }
        return false;

    }
}
