package tpi.prog2.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import tpi.prog2.entities.Categoria;
import tpi.prog2.entities.DetallePedido;
import tpi.prog2.entities.Producto;
import tpi.prog2.exception.DAOException;

/**
 *
 * @author Ezequiel Taboada
 */
public class DetallePedidoDAO {

    public DetallePedido crear(Long pedidoId, DetallePedido detalle, Connection conn) {
        String sql = """
                     INSERT INTO detalle_pedido
                     (pedido_id, producto_id, cantidad, subtotal, eliminado, created_at)
                     VALUES (?, ?, ?, ?, ?, ?)
                     """;

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, pedidoId);
            ps.setLong(2, detalle.getProducto().getId());
            ps.setInt(3, detalle.getCantidad());
            ps.setDouble(4, detalle.getSubtotal());
            ps.setBoolean(5, detalle.isEliminado());
            ps.setTimestamp(6, Timestamp.valueOf(detalle.getCreatedAt()));

            if (ps.executeUpdate() == 0) {
                throw new DAOException("No se pudo crear el detalle del pedido.");
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return buscarPorId(rs.getLong(1), conn);
                }
            }

            throw new DAOException("No se pudo obtener el id del detalle.");

        } catch (SQLException e) {
            throw new DAOException("Error SQL al crear detalle de pedido.", e);
        }
    }

    public List<DetallePedido> listarPorPedido(Long pedidoId, Connection conn) {
        List<DetallePedido> detalles = new ArrayList<>();

        String sql = """
                     SELECT dp.*,
                            p.id AS p_id, p.nombre AS p_nombre, p.precio AS p_precio,
                            p.descripcion AS p_descripcion, p.stock AS p_stock,
                            p.imagen AS p_imagen, p.disponible AS p_disponible,
                            p.eliminado AS p_eliminado, p.created_at AS p_created_at,
                            c.id AS c_id, c.nombre AS c_nombre, c.descripcion AS c_descripcion,
                            c.eliminado AS c_eliminado, c.created_at AS c_created_at
                     FROM detalle_pedido dp
                     JOIN producto p ON dp.producto_id = p.id
                     JOIN categoria c ON p.categoria_id = c.id
                     WHERE dp.pedido_id = ? AND dp.eliminado = false
                     ORDER BY dp.id
                     """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, pedidoId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    detalles.add(mapearDetallePedido(rs));
                }
            }

            return detalles;

        } catch (SQLException e) {
            throw new DAOException("Error SQL al listar detalles del pedido.", e);
        }
    }

    public DetallePedido buscarPorId(Long id, Connection conn) {
        String sql = """
                     SELECT dp.*,
                            p.id AS p_id, p.nombre AS p_nombre, p.precio AS p_precio,
                            p.descripcion AS p_descripcion, p.stock AS p_stock,
                            p.imagen AS p_imagen, p.disponible AS p_disponible,
                            p.eliminado AS p_eliminado, p.created_at AS p_created_at,
                            c.id AS c_id, c.nombre AS c_nombre, c.descripcion AS c_descripcion,
                            c.eliminado AS c_eliminado, c.created_at AS c_created_at
                     FROM detalle_pedido dp
                     JOIN producto p ON dp.producto_id = p.id
                     JOIN categoria c ON p.categoria_id = c.id
                     WHERE dp.id = ? AND dp.eliminado = false
                     """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearDetallePedido(rs);
                }
            }

            return null;

        } catch (SQLException e) {
            throw new DAOException("Error SQL al buscar detalle de pedido.", e);
        }
    }
    
    public void eliminarLogico(Long detalleId, Connection conn) {
    String sql = """
                 UPDATE detalle_pedido
                 SET eliminado = true
                 WHERE id = ? AND eliminado = false
                 """;

    try (PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setLong(1, detalleId);

        if (ps.executeUpdate() == 0) {
            throw new DAOException("No se encontró el detalle para eliminar.");
        }

    } catch (SQLException e) {
        throw new DAOException("Error SQL al eliminar detalle de pedido.", e);
    }
}

    public void eliminarLogicoPorPedido(Long pedidoId, Connection conn) {
        String sql = """
                     UPDATE detalle_pedido
                     SET eliminado = true
                     WHERE pedido_id = ? AND eliminado = false
                     """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, pedidoId);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Error SQL al eliminar detalles del pedido.", e);
        }
    }

    private DetallePedido mapearDetallePedido(ResultSet rs) throws SQLException {
        Categoria categoria = new Categoria(
                rs.getLong("c_id"),
                rs.getBoolean("c_eliminado"),
                rs.getTimestamp("c_created_at").toLocalDateTime(),
                rs.getString("c_nombre"),
                rs.getString("c_descripcion")
        );

        Producto producto = new Producto(
                rs.getLong("p_id"),
                rs.getBoolean("p_eliminado"),
                rs.getTimestamp("p_created_at").toLocalDateTime(),
                rs.getString("p_nombre"),
                rs.getDouble("p_precio"),
                rs.getString("p_descripcion"),
                rs.getInt("p_stock"),
                rs.getString("p_imagen"),
                rs.getBoolean("p_disponible"),
                categoria
        );

        return new DetallePedido(
                rs.getLong("id"),
                rs.getBoolean("eliminado"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getInt("cantidad"),
                rs.getDouble("subtotal"),
                producto
        );
    }
}
