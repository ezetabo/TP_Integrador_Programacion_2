package tpi.prog2.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import tpi.prog2.config.ConexionDB;
import tpi.prog2.entities.Usuario;
import tpi.prog2.enums.Rol;
import tpi.prog2.exception.DAOException;

/**
 *
 * @author Ezequiel Taboada
 */
public class UsuarioDAO implements IBaseDAO<Usuario> {

    @Override
    public Usuario crear(Usuario usuario) {
        String sql = """
                     INSERT INTO usuario
                     (nombre, apellido, mail, celular, contrasena, rol, eliminado, created_at)
                     VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                     """;

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getMail());
            ps.setString(4, usuario.getCelular());
            ps.setString(5, usuario.getContrasena());
            ps.setString(6, usuario.getRol().name());
            ps.setBoolean(7, usuario.isEliminado());
            ps.setTimestamp(8, Timestamp.valueOf(usuario.getCreatedAt()));

            if (ps.executeUpdate() == 0) {
                throw new DAOException("No se pudo crear el usuario.");
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return buscarPorId(rs.getLong(1));
                }
            }

            throw new DAOException("No se pudo obtener el id del usuario.");

        } catch (SQLException e) {
            throw new DAOException("Error SQL al crear usuario.", e);
        }
    }

    @Override
    public List<Usuario> listar() {
        List<Usuario> usuarios = new ArrayList<>();

        String sql = """
                     SELECT id, nombre, apellido, mail, celular, contrasena, rol, eliminado, created_at
                     FROM usuario
                     WHERE eliminado = false
                     ORDER BY id
                     """;

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }

            return usuarios;

        } catch (SQLException e) {
            throw new DAOException("Error SQL al listar usuarios.", e);
        }
    }

    @Override
    public Usuario buscarPorId(Long id) {

        String sql = """
                     SELECT id, nombre, apellido, mail, celular, contrasena, rol, eliminado, created_at
                     FROM usuario
                     WHERE id = ? AND eliminado = false
                     """;

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }

            return null;

        } catch (SQLException e) {
            throw new DAOException("Error SQL al buscar usuario.", e);
        }
    }

    @Override
    public void actualizar(Usuario usuario) {
        String sql = """
                     UPDATE usuario
                     SET nombre = ?, apellido = ?, mail = ?, celular = ?, contrasena = ?, rol = ?
                     WHERE id = ? AND eliminado = false
                     """;

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getMail());
            ps.setString(4, usuario.getCelular());
            ps.setString(5, usuario.getContrasena());
            ps.setString(6, usuario.getRol().name());
            ps.setLong(7, usuario.getId());

            if (ps.executeUpdate() == 0) {
                throw new DAOException("No se encontró el usuario para actualizar.");
            }

        } catch (SQLException e) {
            throw new DAOException("Error SQL al actualizar usuario.", e);
        }
    }

    @Override
    public void eliminarLogico(Long id) {

        String sql = """
                     UPDATE usuario
                     SET eliminado = true
                     WHERE id = ? AND eliminado = false
                     """;

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            if (ps.executeUpdate() == 0) {
                throw new DAOException("No se encontró el usuario para eliminar.");
            }

        } catch (SQLException e) {
            throw new DAOException("Error SQL al eliminar usuario.", e);
        }
    }

    public boolean existeMail(String mail) {
        String sql = """
                     SELECT 1
                     FROM usuario
                     WHERE mail = ?
                     LIMIT 1
                     """;

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, mail);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new DAOException("Error SQL al verificar mail.", e);
        }
    }

    public boolean existeEmailEnOtroUsuario(String email, Long id) {

        String sql = """
                 SELECT mail
                 FROM usuario
                 WHERE mail = ? AND id <> ?
                 LIMIT 1
                 """;

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setLong(2, id);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new DAOException("Error SQL al verificar email.", e);
        }
    }

    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        return new Usuario(
                rs.getLong("id"),
                rs.getBoolean("eliminado"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getString("mail"),
                rs.getString("celular"),
                rs.getString("contrasena"),
                Rol.valueOf(rs.getString("rol"))
        );
    }

}
