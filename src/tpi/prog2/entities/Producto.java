package tpi.prog2.entities;

import java.time.LocalDateTime;

/**
 *
 * @author Ezequiel Taboada
 */
public class Producto extends Base {

    private String nombre;
    private Double precio;
    private String descripcion;
    private int stock;
    private String imagen;
    private boolean disponible;
    private Categoria categoria;

    public Producto(Long id, boolean eliminado, LocalDateTime createdAt,
            String nombre, Double precio, String descripcion, int stock,
            String imagen, boolean disponible, Categoria categoria) {

        super(id, eliminado, createdAt);
        setNombre(nombre);
        setPrecio(precio);
        setDescripcion(descripcion);
        setStock(stock);
        setImagen(imagen);
        setDisponible(disponible);
        setCategoria(categoria);
    }

    public Producto(String nombre, Double precio, String descripcion, int stock,
            String imagen, boolean disponible, Categoria categoria) {

        this(null, false, LocalDateTime.now(), nombre, precio, descripcion,
                stock, imagen, disponible, categoria);
    }

    public String getNombre() {
        return nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getStock() {
        return stock;
    }

    public String getImagen() {
        return imagen;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("ERROR!! El nombre del producto no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    public void setPrecio(Double precio) {
        if (precio == null || precio < 0) {
            throw new IllegalArgumentException("ERROR!! El precio no puede ser negativo.");
        }
        this.precio = precio;
    }

    public void setDescripcion(String descripcion) {
        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("ERROR!! La descripción del producto no puede estar vacía.");
        }
        this.descripcion = descripcion.trim();
    }

    public void setStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("ERROR!! El stock no puede ser negativo.");
        }
        this.stock = stock;
    }

    public void setImagen(String imagen) {
        if (imagen == null || imagen.isBlank()) {
            throw new IllegalArgumentException("ERROR!! La imagen no puede estar vacía.");
        }
        this.imagen = imagen.trim();
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public void setCategoria(Categoria categoria) {
        if (categoria == null) {
            throw new IllegalArgumentException("ERROR!! El producto debe tener una categoría.");
        }
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return String.format(
                "| ID: %-4s | Producto: %-25s | Precio: $%10.2f | Stock: %-5d | Disponible: %-5s | Categoría: %-20s |",
                getId() == null ? "N/A" : getId(),
                nombre,
                precio,
                stock,
                disponible ? "Sí" : "No",
                categoria.getNombre()
        );
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Producto other)
                && getId() != null
                && other.getId() != null
                && getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
