package tpi.prog2.enums;

/**
 *
 * @author Ezequiel Taboada
 */
public enum Estado {
    PENDIENTE("Pedido pendiente"),
    CONFIRMADO("Pedido confirmado"),
    TERMINADO("Pedido terminado"),
    CANCELADO("Pedido cancelado");

    private final String descripcion;

    Estado(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
