package tpi.prog2.entities;

import java.util.Objects;

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

    public Producto(String nombre, Double precio, String descripcion, int stock,
            String imagen) {
        super();
        setNombre(nombre);
        setPrecio(precio);
        setDescripcion(descripcion);
        setStock(stock);
        setImagen(imagen);
        calcularDisponible();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("ERROR!! El nombre del producto no puede estar vacío.");
        }

        this.nombre = nombre.trim();
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        if (precio == null || precio < 0) {
            throw new IllegalArgumentException("ERROR!! El precio del producto debe ser mayor o igual a 0.");
        }

        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        if (descripcion == null || descripcion.isEmpty()) {
            throw new IllegalArgumentException("ERROR!! La descripción del producto no puede estar vacía.");
        }

        this.descripcion = descripcion.trim();
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("ERROR!! El stock del producto debe ser mayor o igual a 0.");
        }
        calcularDisponible();
        this.stock = stock;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        if (imagen == null || imagen.isEmpty()) {
            throw new IllegalArgumentException("ERROR!! La imagen del producto no puede estar vacía.");
        }

        this.imagen = imagen.trim();
    }

    public boolean getDisponible() {
        return disponible;
    }

    private void calcularDisponible() {
        this.disponible = this.stock > 0;

    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        if (this.categoria != categoria) {
            this.categoria = categoria;
        }
        if (!categoria.getProductos().contains(this)) {
            categoria.agregarProducto(this);
        }
    }

    @Override
    public String toString() {
        return String.format("| ID: %-4s %s", id, info());
    }

    public String info() { 
        return String.format(
                "| Producto: %-25s | Precio: $%10.2f | Stock: %-5d | Categoría: %-20s |",
                nombre,
                precio,
                stock,
                categoria == null ? "Sin categoría" : categoria.getNombre()
        );
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || (obj instanceof Producto other && this.getId().equals(other.getId()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
