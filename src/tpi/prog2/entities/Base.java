package tpi.prog2.entities;

import java.time.LocalDateTime;

/**
 *
 * @author Ezequiel Taboada
 */
public abstract class Base {

    private static Long contadorId = 1L;

    protected Long id;
    protected boolean eliminado;
    protected LocalDateTime createdAt;

    public Base() {
        this.id = contadorId;
        contadorId++;
        this.eliminado = false;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        if (createdAt == null) {
            throw new IllegalArgumentException("ERROR!! La fecha de creación no puede ser nula.");
        }

        this.createdAt = createdAt;
    }

    public void eliminar() {
        this.eliminado = true;
    }

    @Override
    public abstract String toString();
}
