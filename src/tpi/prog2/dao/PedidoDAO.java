package tpi.prog2.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import tpi.prog2.config.ConexionDB;
import tpi.prog2.entities.Pedido;
import tpi.prog2.entities.Usuario;
import tpi.prog2.enums.Estado;
import tpi.prog2.enums.FormaPago;
import tpi.prog2.exception.DAOException;

/**
 *
 * @author Ezequiel Taboada
 */
public class PedidoDAO implements IBaseDAO<Pedido> {

    @Override
    public Pedido crear(Pedido pedido) {
        try (Connection conn = ConexionDB.getConnection()) {
            return crear(pedido, conn);
        } catch (SQLException e) {
            throw new DAOException("Error SQL al crear pedido.", e);
        }
    }

    public Pedido crear(Pedido pedido, Connection conn) {
        String sql = """
                     INSERT INTO pedido
                     (fecha, estado, total, forma_pago, usuario_id, eliminado, created_at)
                     VALUES (?, ?, ?, ?, ?, ?, ?)
                     """;

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(pedido.getFecha()));
            ps.setString(2, pedido.getEstado().name());
            ps.setDouble(3, pedido.getTotal());
            ps.setString(4, pedido.getFormaPago().name());
            ps.setLong(5, pedido.getUsuario().getId());
            ps.setBoolean(6, pedido.isEliminado());
            ps.setTimestamp(7, Timestamp.valueOf(pedido.getCreatedAt()));

            if (ps.executeUpdate() == 0) {
                throw new DAOException("No se pudo crear el pedido.");
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return buscarPorId(rs.getLong(1), conn);
                }
            }

            throw new DAOException("No se pudo obtener el id del pedido.");

        } catch (SQLException e) {
            throw new DAOException("Error SQL al crear pedido.", e);
        }
    }

    @Override
    public List<Pedido> listar() {
        List<Pedido> pedidos = new ArrayList<>();

        String sql = """
                     SELECT p.*,
                            u.id AS u_id, u.nombre AS u_nombre, u.apellido AS u_apellido,
                            u.mail AS u_mail, u.celular AS u_celular,
                            u.contrasena AS u_contrasena, u.rol AS u_rol,
                            u.eliminado AS u_eliminado, u.created_at AS u_created_at
                     FROM pedido p
                     JOIN usuario u ON p.usuario_id = u.id
                     WHERE p.eliminado = false
                     ORDER BY p.id
                     """;

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                pedidos.add(mapearPedido(rs));
            }

            return pedidos;

        } catch (SQLException e) {
            throw new DAOException("Error SQL al listar pedidos.", e);
        }
    }

    @Override
    public Pedido buscarPorId(Long id) {
        try (Connection conn = ConexionDB.getConnection()) {
            return buscarPorId(id, conn);
        } catch (SQLException e) {
            throw new DAOException("Error SQL al buscar pedido.", e);
        }
    }

    public Pedido buscarPorId(Long id, Connection conn) {

        String sql = """
                     SELECT p.*,
                            u.id AS u_id, u.nombre AS u_nombre, u.apellido AS u_apellido,
                            u.mail AS u_mail, u.celular AS u_celular,
                            u.contrasena AS u_contrasena, u.rol AS u_rol,
                            u.eliminado AS u_eliminado, u.created_at AS u_created_at
                     FROM pedido p
                     JOIN usuario u ON p.usuario_id = u.id
                     WHERE p.id = ? AND p.eliminado = false
                     """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearPedido(rs);
                }
            }

            return null;

        } catch (SQLException e) {
            throw new DAOException("Error SQL al buscar pedido por id.", e);
        }
    }

    @Override
    public void actualizar(Pedido pedido) {
        String sql = """
                     UPDATE pedido
                     SET estado = ?, forma_pago = ?, total = ?
                     WHERE id = ? AND eliminado = false
                     """;

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pedido.getEstado().name());
            ps.setString(2, pedido.getFormaPago().name());
            ps.setDouble(3, pedido.getTotal());
            ps.setLong(4, pedido.getId());

            if (ps.executeUpdate() == 0) {
                throw new DAOException("No se encontró el pedido para actualizar.");
            }

        } catch (SQLException e) {
            throw new DAOException("Error SQL al actualizar pedido.", e);
        }
    }

    @Override
    public void eliminarLogico(Long id) {
  
        String sql = """
                     UPDATE pedido
                     SET eliminado = true
                     WHERE id = ? AND eliminado = false
                     """;

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            if (ps.executeUpdate() == 0) {
                throw new DAOException("No se encontró el pedido para eliminar.");
            }

        } catch (SQLException e) {
            throw new DAOException("Error SQL al eliminar pedido.", e);
        }
    }

    private Pedido mapearPedido(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario(
                rs.getLong("u_id"),
                rs.getBoolean("u_eliminado"),
                rs.getTimestamp("u_created_at").toLocalDateTime(),
                rs.getString("u_nombre"),
                rs.getString("u_apellido"),
                rs.getString("u_mail"),
                rs.getString("u_celular"),
                rs.getString("u_contrasena"),
                tpi.prog2.enums.Rol.valueOf(rs.getString("u_rol"))
        );

        return new Pedido(
                rs.getLong("id"),
                rs.getBoolean("eliminado"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getDate("fecha").toLocalDate(),
                Estado.valueOf(rs.getString("estado")),
                rs.getDouble("total"),
                FormaPago.valueOf(rs.getString("forma_pago")),
                usuario,
                new ArrayList<>()
        );
    }

}
