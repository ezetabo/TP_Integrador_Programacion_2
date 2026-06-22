package tpi.prog2.enums;

public enum FormaPago {
    TARJETA("Tarjeta"),
    TRANSFERENCIA("Transferencia"),
    EFECTIVO("Efectivo");

    private final String descripcion;

    FormaPago(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
