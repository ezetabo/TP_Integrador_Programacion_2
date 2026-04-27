package tpi.prog2.entities;

import java.time.LocalDateTime;
/**
 *
 * @author Ezequiel Taboada
 */
public abstract class Base {

    private final Long id;
    private boolean eliminado;
    private final LocalDateTime createdAt;

    protected Base(Long id, boolean eliminado, LocalDateTime createdAt) {
        if (id != null && id < 1) {
            throw new IllegalArgumentException("ERROR!! El id debe ser mayor a 0.");
        }
        if (createdAt == null) {
            throw new IllegalArgumentException("ERROR!! La fecha de creación no puede ser nula.");
        }
        this.id = id;
        this.eliminado = eliminado;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }
}
