package tpi.prog2.enums;

/**
 *
 * @author Ezequiel Taboada
 */
public enum FormaPago {
    TARJETA("Pago con tarjeta"),
    TRANSFERENCIA("Transferencia bancaria"),
    EFECTIVO("Pago en efectivo");

    private final String descripcion;

    FormaPago(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
