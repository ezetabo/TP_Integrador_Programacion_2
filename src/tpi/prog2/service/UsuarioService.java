package tpi.prog2.service;

import java.util.List;
import tpi.prog2.dao.UsuarioDAO;
import tpi.prog2.entities.Usuario;
import tpi.prog2.enums.Rol;
import tpi.prog2.exception.DAOException;
import tpi.prog2.exception.EntityNotFoundException;
import tpi.prog2.exception.ServiceException;

/**
 *
 * @author Ezequiel Taboada
 */
public class UsuarioService {

    private final UsuarioDAO usuarioDAO;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public Usuario crear(String nombre, String apellido, String mail,
            String celular, String contrasena, Rol rol) {

        try {
            if (usuarioDAO.existeEmail(mail)) {
                throw new ServiceException("El email ya está en uso.");
            }

            Usuario usuario = new Usuario(
                    nombre,
                    apellido,
                    mail,
                    celular,
                    contrasena,
                    rol
            );

            return usuarioDAO.crear(usuario);

        } catch (IllegalArgumentException e) {
            throw new ServiceException(e.getMessage(), e);
        } catch (DAOException e) {
            throw new ServiceException("No se pudo crear el usuario.", e);
        }
    }

    public List<Usuario> listar() {
        try {
            return usuarioDAO.listar();
        } catch (DAOException e) {
            throw new ServiceException("No se pudieron listar los usuarios.", e);
        }
    }

    public Usuario buscarPorId(Long id) {
        try {
            Usuario usuario = usuarioDAO.buscarPorId(id);

            if (usuario == null) {
                throw new EntityNotFoundException("No existe el usuario.");
            }

            return usuario;

        } catch (DAOException e) {
            throw new ServiceException("No se pudo buscar el usuario.", e);
        }
    }

    public void actualizar(Long id, String nombre, String apellido, String mail,
            String celular, String contrasena, Rol rol) {

        try {
            Usuario existente = buscarPorId(id);

            if (usuarioDAO.existeEmailEnOtroUsuario(mail, existente.getId())) {
                throw new ServiceException("El email ya está en uso por otro usuario.");
            }

            Usuario actualizado = new Usuario(
                    existente.getId(),
                    existente.isEliminado(),
                    existente.getCreatedAt(),
                    nombre,
                    apellido,
                    mail,
                    celular,
                    contrasena,
                    rol
            );

            usuarioDAO.actualizar(actualizado);

        } catch (IllegalArgumentException e) {
            throw new ServiceException(e.getMessage(), e);
        } catch (DAOException e) {
            throw new ServiceException("No se pudo actualizar el usuario.", e);
        }
    }

    public void eliminarLogico(Long id) {
        try {
            buscarPorId(id);
            usuarioDAO.eliminarLogico(id);
        } catch (DAOException e) {
            throw new ServiceException("No se pudo eliminar el usuario.", e);
        }
    }
}
