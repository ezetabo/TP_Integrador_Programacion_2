package tpi.prog2.entities;

import java.util.Objects;

/**
 *
 * @author Ezequiel Taboada
 */
public class DetallePedido extends Base {

    private int cantidad;
    private Double subtotal;
    private Producto producto;

    public DetallePedido(int cantidad, Producto producto) {
        super();
        setProducto(producto);
        setCantidad(cantidad);
        setSubtotal(calcularSubtotal());
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("ERROR!! La cantidad del detalle debe ser mayor a 0.");
        }

        this.cantidad = cantidad;
        this.subtotal = calcularSubtotal();
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        if (subtotal == null || subtotal < 0) {
            throw new IllegalArgumentException("ERROR!! El subtotal del detalle debe ser mayor o igual a 0.");
        }

        this.subtotal = subtotal;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("ERROR!! El producto no puede ser nulo.");
        }
        if (this.producto != producto) {
            this.producto = producto;
            this.subtotal = calcularSubtotal();
        }

    }

    private Double calcularSubtotal() {
        if (producto == null || producto.getPrecio() == null) {
            return 0.0;
        }
        return producto.getPrecio() * cantidad;
    }

    @Override
    public String toString() {        
        return String.format(
                "| ID: %-4s | Producto: %-25s | Cantidad: %-5d | Subtotal: $%10.2f |",
                id,
                producto.getNombre(),
                cantidad,
                subtotal
        );
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || (obj instanceof DetallePedido other && this.getId().equals(other.getId()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
