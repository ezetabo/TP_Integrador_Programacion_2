package tpi.prog2.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import tpi.prog2.enums.Estado;
import tpi.prog2.enums.FormaPago;
import tpi.prog2.interfaces.Calculable;

/**
 *
 * @author Ezequiel Taboada
 */
public class Pedido extends Base implements Calculable {

    private LocalDate fecha;
    private Estado estado;
    private Double total;
    private FormaPago formaPago;
    private List<DetallePedido> detalles;
    private Usuario usuario;

    public Pedido(LocalDate fecha, Estado estado, FormaPago formaPago) {
        super();
        setFecha(fecha);
        setEstado(estado);
        this.total = 0.0;
        setFormaPago(formaPago);
        this.detalles = new ArrayList<>();
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("ERROR!! La fecha del pedido no puede ser nula.");
        }
        this.fecha = fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        if (estado == null) {
            throw new IllegalArgumentException("ERROR!! El estado del pedido no puede ser nulo.");
        }
        this.estado = estado;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        if (total == null || total < 0) {
            throw new IllegalArgumentException("ERROR!! El total del pedido debe ser mayor o igual a 0.");
        }
        this.total = total;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        if (formaPago == null) {
            throw new IllegalArgumentException("ERROR!! La forma de pago no puede ser nula.");
        }
        this.formaPago = formaPago;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        if (detalles == null) {
            throw new IllegalArgumentException("ERROR!! La lista de detalles no puede ser nula.");
        }
        this.detalles = detalles;
        calcularTotal();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        if (this.usuario != usuario) {
            this.usuario = usuario;
        }
        if (!usuario.getPedidos().contains(this)) {
            usuario.agregarPedido(this);
        }
    }

    @Override
    public void calcularTotal() {
        double suma = 0.0;
        for (DetallePedido detalle : detalles) {
            suma += detalle.getSubtotal();
        }
        this.total = suma;
    }

    public DetallePedido findDetallePedidoByProducto(Producto producto) {
        if (producto != null) {
            for (DetallePedido detalle : detalles) {
                if (detalle.getProducto() != null && detalle.getProducto().equals(producto)) {
                    return detalle;
                }
            }
        }
        return null;
    }

    public void addDetallePedido(int cantidad, Double precioUnitario, Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("ERROR!! El producto del detalle no puede ser nulo.");
        }
        if (producto.isEliminado()) {
            throw new IllegalArgumentException("ERROR!! No se puede agregar un producto eliminado.");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("ERROR!! La cantidad del detalle debe ser mayor a 0.");
        }
        if (precioUnitario == null || precioUnitario < 0) {
            throw new IllegalArgumentException("ERROR!! El precio unitario debe ser mayor o igual a 0.");
        }
        if (producto.getPrecio() != precioUnitario) {
            producto.setPrecio(precioUnitario);
        }

        DetallePedido detalleExistente = findDetallePedidoByProducto(producto);

        if (detalleExistente != null) {
            detalleExistente.setCantidad(detalleExistente.getCantidad() + cantidad);
        } else {
            detalles.add(new DetallePedido(cantidad, producto));
        }

        calcularTotal();
    }

    public void deleteDetallePedidoByProducto(Producto producto) {
        DetallePedido detalle = findDetallePedidoByProducto(producto);

        if (detalle != null) {
            detalles.remove(detalle);
            calcularTotal();
        }
    }

    @Override
    public String toString() {     
        StringBuilder sb = new StringBuilder();

        sb.append(String.format(
                "| ID: %-4s | Fecha: %-10s | Usuario: %-15s | Estado: %-12s | Pago: %-14s | Total: $%10.2f |%n",
                id,
                fecha,
                usuario.getNombre(),
                estado,
                formaPago,
                total
        ));

        sb.append("Detalles:%n".formatted());

        if (detalles.isEmpty()) {
            sb.append("  Sin detalles cargados.%n");
        } else {
            for (DetallePedido detalle : detalles) {
                sb.append("  ")
                        .append(detalle)
                        .append(System.lineSeparator());
            }
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || (obj instanceof Pedido other && this.getId().equals(other.getId()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
