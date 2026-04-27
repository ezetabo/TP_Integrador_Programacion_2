package tpi.prog2.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import tpi.prog2.config.ConexionDB;
import tpi.prog2.entities.Producto;
import tpi.prog2.entities.Categoria;
import tpi.prog2.exception.DAOException;

/**
 *
 * @author Ezequiel Taboada
 */
public class ProductoDAO implements IBaseDAO<Producto> {

    @Override
    public Producto crear(Producto producto) {

        String sql = """
                     INSERT INTO producto 
                     (nombre, precio, descripcion, stock, imagen, disponible, categoria_id, eliminado, created_at)
                     VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                     """;

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getPrecio());
            ps.setString(3, producto.getDescripcion());
            ps.setInt(4, producto.getStock());
            ps.setString(5, producto.getImagen());
            ps.setBoolean(6, producto.isDisponible());
            ps.setLong(7, producto.getCategoria().getId());
            ps.setBoolean(8, producto.isEliminado());
            ps.setTimestamp(9, Timestamp.valueOf(producto.getCreatedAt()));

            if (ps.executeUpdate() == 0) {
                throw new DAOException("No se pudo crear el producto.");
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    Long id = rs.getLong(1);
                    return buscarPorId(id);
                }
            }

            throw new DAOException("No se pudo obtener el id del producto.");

        } catch (SQLException e) {
            throw new DAOException("Error SQL al crear producto.", e);
        }
    }

    @Override
    public List<Producto> listar() {

        List<Producto> lista = new ArrayList<>();

        String sql = """
                     SELECT p.*, c.id AS c_id, c.nombre AS c_nombre, c.descripcion AS c_desc,
                            c.eliminado AS c_eliminado, c.created_at AS c_created
                     FROM producto p
                     JOIN categoria c ON p.categoria_id = c.id
                     WHERE p.eliminado = false
                     ORDER BY p.id
                     """;

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearProducto(rs));
            }

            return lista;

        } catch (SQLException e) {
            throw new DAOException("Error SQL al listar productos.", e);
        }
    }

    @Override
    public Producto buscarPorId(Long id) {     

        String sql = """
                     SELECT p.*, c.id AS c_id, c.nombre AS c_nombre, c.descripcion AS c_desc,
                            c.eliminado AS c_eliminado, c.created_at AS c_created
                     FROM producto p
                     JOIN categoria c ON p.categoria_id = c.id
                     WHERE p.id = ? AND p.eliminado = false
                     """;

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearProducto(rs);
                }
            }

            return null;

        } catch (SQLException e) {
            throw new DAOException("Error SQL al buscar producto.", e);
        }
    }

    @Override
    public void actualizar(Producto producto) {

        String sql = """
                     UPDATE producto
                     SET nombre = ?, precio = ?, descripcion = ?, stock = ?, 
                         imagen = ?, disponible = ?, categoria_id = ?
                     WHERE id = ? AND eliminado = false
                     """;

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getPrecio());
            ps.setString(3, producto.getDescripcion());
            ps.setInt(4, producto.getStock());
            ps.setString(5, producto.getImagen());
            ps.setBoolean(6, producto.isDisponible());
            ps.setLong(7, producto.getCategoria().getId());
            ps.setLong(8, producto.getId());

            if (ps.executeUpdate() == 0) {
                throw new DAOException("No se encontró el producto.");
            }

        } catch (SQLException e) {
            throw new DAOException("Error SQL al actualizar producto.", e);
        }
    }

    @Override
    public void eliminarLogico(Long id) {
        
        String sql = """
                     UPDATE producto
                     SET eliminado = true
                     WHERE id = ?
                     """;

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            if (ps.executeUpdate() == 0) {
                throw new DAOException("No se encontró el producto.");
            }

        } catch (SQLException e) {
            throw new DAOException("Error SQL al eliminar producto.", e);
        }
    }

    private Producto mapearProducto(ResultSet rs) throws SQLException {

        Categoria categoria = new Categoria(
                rs.getLong("c_id"),
                rs.getBoolean("c_eliminado"),
                rs.getTimestamp("c_created").toLocalDateTime(),
                rs.getString("c_nombre"),
                rs.getString("c_desc")
        );

        return new Producto(
                rs.getLong("id"),
                rs.getBoolean("eliminado"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getString("nombre"),
                rs.getDouble("precio"),
                rs.getString("descripcion"),
                rs.getInt("stock"),
                rs.getString("imagen"),
                rs.getBoolean("disponible"),
                categoria
        );
    }

}
