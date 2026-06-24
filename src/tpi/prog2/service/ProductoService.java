package tpi.prog2.service;

import java.util.List;
import tpi.prog2.entities.Categoria;
import tpi.prog2.entities.Producto;
import tpi.prog2.menu.InputReader;
import static tpi.prog2.service.CategoriaService.obtnerIndex;

public class ProductoService {

    public static void crear(List<Producto> lista, List<Categoria> categorias) {
        String nombre = pedirNombreUnico(lista);
        double precio = InputReader.leerDoubleEnRango("Ingrese el precio de \"" + nombre + "\": ", "Error!! El precio debe ser numerido",
                0, Double.MAX_VALUE);
        String descripcion = InputReader.leerCadena("Ingrese la descripcion de \"" + nombre + "\": ");
        int stock = InputReader.leerIntEnRango("Ingrese el stock de \"" + nombre + "\": ", "Error!! El stock debe ser numerido",
                0, Integer.MAX_VALUE);
        String imagen = InputReader.leerCadena("Ingrese el nombre de la imagen: ");
        int index = CategoriaService.obtnerIndex(categorias, "asignar");
        Producto p = crear(nombre, precio, descripcion, stock, imagen);
        p.setCategoria(categorias.get(index));
        lista.add(p);
        System.out.println(">>> Creacion exitosa <<<");
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

    public static int obtnerIndex(List<Producto> lista, String accion) {
        int largo = lista.size();
        for (int i = 0; i < largo; i++) {
            Producto p = lista.get(i);
            if (!p.isEliminado()) {
                System.out.println((i + 1) + p.info());
            }
        }
        return InputReader.leerIntEnRango("Seleccione el numero del producto que quiere " + accion + ": ",
                "ERROR... El dato debe ser numerico", 1, largo) - 1;
    }

    public static void actualizar(List<Producto> lista, List<Categoria> categorias) {
        boolean volver = true;
        String menu = """
                        1. Actualizar Precio.
                        2. Actualizar Stock.
                        3. Actualizar Categoria.
                        0. Volver al menu de productos.
                        """;
        Producto p = lista.get(obtnerIndex(lista, "actualizar"));
        do {
            int opcion = InputReader.leerIntEnRango(menu, "ERROR.. El dato debe ser numerico", 0, 3);
            switch (opcion) {
                case 1:
                    System.out.println("[" + p.getPrecio() + "]");
                    double precio = InputReader.leerDoubleEnRango("Ingrese el nuevo precio de \"" + p.getNombre()
                            + "\": ", "Error!! El precio debe ser numerido", 0, Double.MAX_VALUE);
                    p.setPrecio(precio);
                    System.out.println("---Actualizacion exitosa---");
                    break;
                case 2:
                    System.out.println("[" + p.getStock() + "]");
                    int stock = InputReader.leerIntEnRango("Ingrese el nuevo stock de \"" + p.getNombre() + "\": ",
                            "Error!! El stock debe ser numerido", 0, Integer.MAX_VALUE);
                    p.setStock(stock);
                    System.out.println("---Actualizacion exitosa---");
                    break;
                case 3:
                    System.out.println("[" + p.getCategoria().getNombre() + "]");
                    int index = CategoriaService.obtnerIndex(categorias, "asignar");
                    p.setCategoria(categorias.get(index));
                    System.out.println("---Actualizacion exitosa---");
                    break;
                default:
                    volver = false;
            }
        } while (volver);
    }

    public static void eliminar(List<Producto> lista) {
        int index = obtnerIndex(lista, "eliminar");
        Producto p = lista.get(index);
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
}
