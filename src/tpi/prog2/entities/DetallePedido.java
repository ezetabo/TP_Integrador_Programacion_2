package tpi.prog2.entities;

import java.time.LocalDateTime;

/**
 *
 * @author Ezequiel Taboada
 */
public class DetallePedido extends Base {

    private int cantidad;
    private Double subtotal;
    private Producto producto;

    public DetallePedido(Long id, boolean eliminado, LocalDateTime createdAt,
            int cantidad, Double subtotal, Producto producto) {

        super(id, eliminado, createdAt);
        setCantidad(cantidad);
        setSubtotal(subtotal);
        setProducto(producto);
    }

    public DetallePedido(int cantidad, Double subtotal, Producto producto) {
        this(null, false, LocalDateTime.now(), cantidad, subtotal, producto);
    }

    public int getCantidad() {
        return cantidad;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setCantidad(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("ERROR!! La cantidad debe ser mayor a 0.");
        }
        this.cantidad = cantidad;
    }

    public void setSubtotal(Double subtotal) {
        if (subtotal == null || subtotal < 0) {
            throw new IllegalArgumentException("ERROR!! El subtotal no puede ser negativo.");
        }
        this.subtotal = subtotal;
    }

    public void setProducto(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("ERROR!! El detalle debe tener un producto.");
        }
        this.producto = producto;
    }

    @Override
    public String toString() {
        return String.format(
                "| ID: %-4s | Producto: %-25s | Cantidad: %-5d | Subtotal: $%10.2f |",
                getId() == null ? "N/A" : getId(),
                producto.getNombre(),
                cantidad,
                subtotal
        );
    }
}
