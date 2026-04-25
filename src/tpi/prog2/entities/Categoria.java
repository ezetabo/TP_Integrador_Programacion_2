package tpi.prog2.entities;

import java.time.LocalDateTime;

/**
 *
 * @author Ezequiel Taboada
 */
public class Categoria extends Base {

    private String nombre;
    private String descripcion;

    public Categoria(Long id, boolean eliminado, LocalDateTime createdAt,
            String nombre, String descripcion) {

        super(id, eliminado, createdAt);
        setNombre(nombre);
        setDescripcion(descripcion);
    }

    public Categoria(String nombre, String descripcion) {
        this(null, false, LocalDateTime.now(), nombre, descripcion);
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("ERROR!! El nombre de la categoría no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    public void setDescripcion(String descripcion) {
        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("ERROR!! La descripción de la categoría no puede estar vacía.");
        }
        this.descripcion = descripcion.trim();
    }

    @Override
    public String toString() {
        return String.format(
                "| ID: %-4s | Categoría: %-25s | Descripción: %-40s |",
                getId() == null ? "N/A" : getId(),
                nombre,
                descripcion
        );
    }
}
