package tpi.prog2.service;

import java.util.List;
import tpi.prog2.entities.Categoria;
import tpi.prog2.menu.InputReader;

public class CategoriaService {

    public static Categoria crear(List<Categoria> lista) {
        String nombre = null;
        boolean existe = false;
        do {
            nombre = InputReader.leerCadena("Ingrese el nombre de la categoria: ");
            existe = existeNombre(lista, nombre);
            if(existe){
                System.out.println("ERROR!!! El nombre ya existe.");
            }
        } while (existe);
        String descripcion = InputReader.leerCadena("Ingrese la descripcion de \"" + nombre + "\" :");       
        return crear(nombre, descripcion);
    }
    
    public static Categoria crear(String nombre, String descripcion){
        return new Categoria(nombre, descripcion);
    }
    
    private static boolean existeNombre(List<Categoria> lista, String nombre) {
        for (Categoria categoria : lista) {
            if (categoria.getNombre().equalsIgnoreCase(nombre.trim())) {
                return true;
            }
        }
        return false;
    }
}
