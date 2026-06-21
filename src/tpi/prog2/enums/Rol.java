package tpi.prog2.enums;

/**
 *
 * @author Ezequiel Taboada
 */
public enum Rol {
    ADMIN("Administrador del sistema"),
    USUARIO("Usuario del sistema");

    private final String descripcion;

    Rol(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
