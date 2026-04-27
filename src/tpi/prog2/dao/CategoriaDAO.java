package tpi.prog2.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import tpi.prog2.config.ConexionDB;
import tpi.prog2.entities.Categoria;
import tpi.prog2.exception.DAOException;

/**
 *
 * @author Ezequiel Taboada
 */
public class CategoriaDAO implements IBaseDAO<Categoria> {

    @Override
    public Categoria crear(Categoria categoria) {
        String sql = """
                 INSERT INTO categoria (nombre, descripcion, eliminado, created_at)
                 VALUES (?, ?, ?, ?)
                 """;

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, categoria.getNombre());
            ps.setString(2, categoria.getDescripcion());
            ps.setBoolean(3, categoria.isEliminado());
            ps.setTimestamp(4, Timestamp.valueOf(categoria.getCreatedAt()));

            if (ps.executeUpdate() == 0) {
                throw new DAOException("No se pudo crear la categoría.");
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    Long idGenerado = rs.getLong(1);
                    return buscarPorId(idGenerado);
                }
            }

            throw new DAOException("No se pudo obtener el id generado de la categoría.");

        } catch (SQLException e) {
            throw new DAOException("Error SQL al crear categoría.", e);
        }
    }

    @Override
    public List<Categoria> listar() {
        List<Categoria> categorias = new ArrayList<>();

        String sql = """
                     SELECT id, nombre, descripcion, eliminado, created_at
                     FROM categoria
                     WHERE eliminado = false
                     ORDER BY id
                     """;

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                categorias.add(mapearCategoria(rs));
            }

            return categorias;

        } catch (SQLException e) {
            throw new DAOException("Error SQL al listar categorías.", e);
        }
    }

    @Override
    public Categoria buscarPorId(Long id) {    

        String sql = """
                     SELECT id, nombre, descripcion, eliminado, created_at
                     FROM categoria
                     WHERE id = ? AND eliminado = false
                     """;

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearCategoria(rs);
                }
            }

            return null;

        } catch (SQLException e) {
            throw new DAOException("Error SQL al buscar categoría por id.", e);
        }
    }

    @Override
    public void actualizar(Categoria categoria) {
        String sql = """
                     UPDATE categoria
                     SET nombre = ?, descripcion = ?
                     WHERE id = ? AND eliminado = false
                     """;

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, categoria.getNombre());
            ps.setString(2, categoria.getDescripcion());
            ps.setLong(3, categoria.getId());

            if (ps.executeUpdate() == 0) {
                throw new DAOException("No se encontró la categoría para actualizar.");
            }

        } catch (SQLException e) {
            throw new DAOException("Error SQL al actualizar categoría.", e);
        }
    }

    @Override
    public void eliminarLogico(Long id) {
        String sql = """
                     UPDATE categoria
                     SET eliminado = true
                     WHERE id = ? AND eliminado = false
                     """;

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            if (ps.executeUpdate() == 0) {
                throw new DAOException("No se encontró la categoría para eliminar.");
            }

        } catch (SQLException e) {
            throw new DAOException("Error SQL al eliminar categoría.", e);
        }
    }

    private Categoria mapearCategoria(ResultSet rs) throws SQLException {
        return new Categoria(
                rs.getLong("id"),
                rs.getBoolean("eliminado"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getString("nombre"),
                rs.getString("descripcion")
        );
    }

}
