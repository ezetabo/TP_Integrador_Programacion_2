package tpi.prog2.entities;

import java.time.LocalDateTime;
import tpi.prog2.enums.Rol;

/**
 *
 * @author Ezequiel Taboada
 */
public class Usuario extends Base {

    private String nombre;
    private String apellido;
    private String mail;
    private String celular;
    private String contrasena;
    private Rol rol;

    public Usuario(Long id, boolean eliminado, LocalDateTime createdAt,
            String nombre, String apellido, String mail,
            String celular, String contrasena, Rol rol) {

        super(id, eliminado, createdAt);
        setNombre(nombre);
        setApellido(apellido);
        setMail(mail);
        setCelular(celular);
        setContrasena(contrasena);
        setRol(rol);
    }

    public Usuario(String nombre, String apellido, String mail,
            String celular, String contrasena, Rol rol) {

        this(null, false, LocalDateTime.now(), nombre, apellido, mail,
                celular, contrasena, rol);
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getMail() {
        return mail;
    }

    public String getCelular() {
        return celular;
    }

    public String getContrasena() {
        return contrasena;
    }

    public Rol getRol() {
        return rol;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("ERROR!! El nombre del usuario no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    public void setApellido(String apellido) {
        if (apellido == null || apellido.isBlank()) {
            throw new IllegalArgumentException("ERROR!! El apellido del usuario no puede estar vacío.");
        }
        this.apellido = apellido.trim();
    }

    public void setMail(String mail) {
        if (mail == null || mail.isBlank() || !mail.contains("@") || mail.contains(" ")) {
            throw new IllegalArgumentException("ERROR!! El mail ingresado no tiene un formato válido.");
        }
        this.mail = mail.trim().toLowerCase();
    }

    public void setCelular(String celular) {
        if (celular == null || celular.isBlank()) {
            throw new IllegalArgumentException("ERROR!! El celular del usuario no puede estar vacío.");
        }
        this.celular = celular.trim();
    }

    public void setContrasena(String contrasena) {
        if (contrasena == null || contrasena.isBlank()) {
            throw new IllegalArgumentException("ERROR!! La contraseña no puede estar vacía.");
        }
        this.contrasena = contrasena;
    }

    public void setRol(Rol rol) {
        if (rol == null) {
            throw new IllegalArgumentException("ERROR!! El rol del usuario no puede ser nulo.");
        }
        this.rol = rol;
    }

    @Override
    public String toString() {
        return String.format(
                "| ID: %-4s | Usuario: %-20s %-20s | Mail: %-30s | Celular: %-15s | Rol: %-8s |",
                getId() == null ? "N/A" : getId(),
                nombre,
                apellido,
                mail,
                celular,
                rol
        );
    }
}