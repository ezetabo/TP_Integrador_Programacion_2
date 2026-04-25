package tpi.prog2.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import tpi.prog2.enums.Estado;
import tpi.prog2.enums.FormaPago;

/**
 *
 * @author Ezequiel Taboada
 */
public class Pedido extends Base implements Calculable {

    private LocalDate fecha;
    private Estado estado;
    private double total;
    private FormaPago formaPago;
    private Usuario usuario;
    private List<DetallePedido> detalles;

    public Pedido(Long id, boolean eliminado, LocalDateTime createdAt,
            LocalDate fecha, Estado estado, double total,
            FormaPago formaPago, Usuario usuario,
            List<DetallePedido> detalles) {

        super(id, eliminado, createdAt);
        setFecha(fecha);
        setEstado(estado);
        setTotal(total);
        setFormaPago(formaPago);
        setUsuario(usuario);
        this.detalles = detalles;
    }

    public Pedido(Estado estado, FormaPago formaPago, Usuario usuario) {
        this(null, false, LocalDateTime.now(), LocalDate.now(),
                estado, 0.0, formaPago, usuario, new ArrayList<>());
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public double getTotal() {
        return total;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public List<DetallePedido> getDetalles() {
        return new ArrayList<>(detalles);
    }

    public void setEstado(Estado estado) {
        if (estado == null) {
            throw new IllegalArgumentException("ERROR!! El estado no puede ser nulo.");
        }
        this.estado = estado;
    }

    public void setFormaPago(FormaPago formaPago) {
        if (formaPago == null) {
            throw new IllegalArgumentException("ERROR!! La forma de pago no puede ser nula.");
        }
        this.formaPago = formaPago;
    }

    private void setFecha(LocalDate fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("ERROR!! La fecha no puede ser nula.");
        }
        this.fecha = fecha;
    }

    private void setTotal(double total) {
        if (total < 0) {
            throw new IllegalArgumentException("ERROR!! El total no puede ser negativo.");
        }
        this.total = total;
    }

    private void setUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("ERROR!! El pedido debe tener un usuario.");
        }
        this.usuario = usuario;
    }

    @Override
    public double calcularTotal() {
        double suma = 0;

        for (DetallePedido detalle : detalles) {
            suma += detalle.getSubtotal();
        }

        return suma;
    }

    public void addDetallePedido(int cantidad, double precioUnitario, Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("ERROR!! El producto no puede ser nulo.");
        }

        if (precioUnitario < 0) {
            throw new IllegalArgumentException("ERROR!! El precio unitario no puede ser negativo.");
        }

        if (cantidad < 1) {
            throw new IllegalArgumentException("ERROR!! La cantidad no puede ser menor a 1.");
        }

        double subtotal = cantidad * precioUnitario;
        DetallePedido detalle = new DetallePedido(cantidad, subtotal, producto);
        detalles.add(detalle);
        this.total = calcularTotal();
    }

    public DetallePedido findeDetallePedidoByProducto(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("ERROR!! El producto no puede ser nulo.");
        }

        for (DetallePedido detalle : detalles) {
            if (detalle.getProducto().equals(producto)) {
                return detalle;
            }
        }

        return null;
    }

    public DetallePedido deleteDetallePedidoByProducto(Producto producto) {
        DetallePedido detalle = findeDetallePedidoByProducto(producto);

        if (detalle == null) {
            return null;
        }

        detalles.remove(detalle);
        detalle.setEliminado(true);
        this.total = calcularTotal();

        return detalle;
    }

    @Override
    public String toString() {
        return String.format(
                "| ID: %-4s | Fecha: %-10s | Usuario: %-25s | Estado: %-12s | Pago: %-14s | Total: $%10.2f |",
                getId() == null ? "N/A" : getId(),
                fecha,
                usuario.getNombre() + " " + usuario.getApellido(),
                estado,
                formaPago,
                total
        );
    }
}
