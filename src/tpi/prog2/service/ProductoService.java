package tpi.prog2.service;

import java.util.List;
import tpi.prog2.entities.Categoria;
import tpi.prog2.entities.Producto;
import tpi.prog2.menu.InputReader;

public class ProductoService {

    public static void crear(List<Producto> lista, List<Categoria>categorias) {
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
}
