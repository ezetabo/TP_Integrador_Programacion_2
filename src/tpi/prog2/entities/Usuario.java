package tpi.prog2.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    private String contrasenia;
    private Rol rol;
    private List<Pedido> pedidos;

    public Usuario(String nombre, String apellido, String mail, String celular,
            String contrasenia, Rol rol) {
        super();
        setNombre(nombre);
        setApellido(apellido);
        setMail(mail);
        setCelular(celular);
        setContrasenia(contrasenia);
        setRol(rol);
        this.pedidos = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("ERROR!! El nombre del usuario no puede estar vacío.");
        }

        this.nombre = nombre.trim();
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        if (apellido == null || apellido.isEmpty()) {
            throw new IllegalArgumentException("ERROR!! El apellido del usuario no puede estar vacío.");
        }

        this.apellido = apellido.trim();
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        if (mail == null || mail.isEmpty() || !mail.contains("@") || mail.contains(" ")) {
            throw new IllegalArgumentException("ERROR!! El mail ingresado no tiene un formato válido.");
        }

        this.mail = mail.trim().toLowerCase();
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        if (celular == null || celular.isEmpty()) {
            throw new IllegalArgumentException("ERROR!! El celular del usuario no puede estar vacío.");
        }

        this.celular = celular.trim();
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        if (contrasenia == null || contrasenia.isEmpty()) {
            throw new IllegalArgumentException("ERROR!! La contraseña del usuario no puede estar vacía.");
        }

        this.contrasenia = contrasenia;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        if (rol == null) {
            throw new IllegalArgumentException("ERROR!! El rol del usuario no puede ser nulo.");
        }

        this.rol = rol;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        if (pedidos == null) {
            throw new IllegalArgumentException("ERROR!! La lista de pedidos no puede ser nula.");
        }

        this.pedidos = pedidos;
    }

    private boolean existe(Pedido pedido) {
        for (Pedido p : pedidos) {
            if (p.equals(pedido)) {
                return true;
            }
        }
        return false;
    }

    public void agregarPedido(Pedido pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException("ERROR!! El pedido no puede ser nulo.");
        }

        if (!existe(pedido)) {
            pedidos.add(pedido);
            if (pedido.getUsuario() != this) {
                pedido.setUsuario(this);
            }
        }
    }

    public boolean tienePedidosActivos() {
        for (Pedido pedido : pedidos) {
            if (!pedido.isEliminado()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format(
                "| ID: %-4s | Usuario: %-12s %-12s | Mail: %-15s | Celular: %-15s | Rol: %-8s |%n",
                id,
                nombre,
                apellido,
                mail,
                celular,
                rol
        ));

        sb.append("Pedidos:%n".formatted());

        if (pedidos.isEmpty()) {
            sb.append("  Sin pedidos cargados.%n");
        } else {
            for (Pedido pedido : pedidos) {
                sb.append("  ")
                        .append(pedido)
                        .append(System.lineSeparator());
            }
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || (obj instanceof Usuario other && this.getId().equals(other.getId()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

}
