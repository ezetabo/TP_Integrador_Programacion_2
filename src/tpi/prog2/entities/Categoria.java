package tpi.prog2.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Ezequiel Taboada
 */
public class Categoria extends Base {

    private String nombre;
    private String descripcion;
    private List<Producto> productos;

    public Categoria(String nombre, String descripcion) {
        super();
        setNombre(nombre);
        setDescripcion(descripcion);
        setProductos(productos);
        this.productos = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("ERROR!! El nombre de la categoría no puede estar vacío.");
        }

        this.nombre = nombre.trim();
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        if (descripcion == null || descripcion.isEmpty()) {
            throw new IllegalArgumentException("ERROR!! La descripción de la categoría no puede estar vacía.");
        }

        this.descripcion = descripcion.trim();
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        if (productos == null) {
            throw new IllegalArgumentException("ERROR!! La lista de productos no puede ser nula.");
        }

        this.productos = productos;
    }

    private boolean existe(Producto producto) {
        for (Producto p : productos) {
            if (p.equals(producto)) {
                return true;
            }
        }

        return false;
    }

    public void agregarProducto(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("ERROR!! El producto no puede ser nulo.");
        }

        if (!existe(producto)) {
            productos.add(producto);
            if (producto.getCategoria() != this) {
                producto.setCategoria(this);
            }
        }
    }

    public void quitarProducto(Producto producto) {
        if (producto != null) {
            productos.remove(producto);
        }
    }

    public boolean tieneProductosActivos() {
        for (Producto producto : productos) {
            if (!producto.isEliminado()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format(
                "| ID: %-4s | Categoría: %-25s | Descripción: %-40s |%n",
                id,
                nombre,
                descripcion
        ));

        sb.append("Productos:%n".formatted());

        if (productos.isEmpty()) {
            sb.append("  Sin productos cargados.%n");
        } else {
            for (Producto producto : productos) {
                sb.append("  ")
                        .append(producto)
                        .append(System.lineSeparator());
            }
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || (obj instanceof Categoria other && this.getId().equals(other.getId()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
