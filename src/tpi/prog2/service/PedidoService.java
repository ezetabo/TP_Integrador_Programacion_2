package tpi.prog2.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import tpi.prog2.config.ConexionDB;
import tpi.prog2.dao.DetallePedidoDAO;
import tpi.prog2.dao.PedidoDAO;
import tpi.prog2.dao.ProductoDAO;
import tpi.prog2.dao.UsuarioDAO;
import tpi.prog2.entities.DetallePedido;
import tpi.prog2.entities.Pedido;
import tpi.prog2.entities.Usuario;
import tpi.prog2.exception.DAOException;
import tpi.prog2.exception.EntityNotFoundException;
import tpi.prog2.exception.ServiceException;

/**
 *
 * @author Ezequiel Taboada
 */
public class PedidoService {

    private final PedidoDAO pedidoDAO;
    private final DetallePedidoDAO detallePedidoDAO;
    private final UsuarioDAO usuarioDAO;
    private final ProductoDAO productoDAO;

    public PedidoService() {
        this.pedidoDAO = new PedidoDAO();
        this.detallePedidoDAO = new DetallePedidoDAO();
        this.usuarioDAO = new UsuarioDAO();
        this.productoDAO = new ProductoDAO();
    }

    public Pedido crear(Pedido pedido) {
        if (pedido == null) {
            throw new ServiceException("El pedido no puede ser nulo.");
        }

        if (pedido.getUsuario() == null || pedido.getUsuario().getId() == null) {
            throw new ServiceException("El pedido debe tener un usuario persistido.");
        }

        if (pedido.getDetalles().isEmpty()) {
            throw new ServiceException("El pedido debe tener al menos un detalle.");
        }

        Connection conn = null;

        try {
            conn = ConexionDB.getConnection();
            conn.setAutoCommit(false);

            Pedido pedidoPersistido = pedidoDAO.crear(pedido, conn);

            for (DetallePedido detalle : pedido.getDetalles()) {
                detallePedidoDAO.crear(pedidoPersistido.getId(), detalle, conn);
            }

            conn.commit();

            return pedidoDAO.buscarPorId(pedidoPersistido.getId(), conn);

        } catch (SQLException | DAOException e) {
            hacerRollback(conn);
            throw new ServiceException("No se pudo crear el pedido.", e);

        } finally {
            cerrarConexion(conn);
        }
    }

    public List<Pedido> listar() {
        try {
            return pedidoDAO.listar();
        } catch (DAOException e) {
            throw new ServiceException("No se pudieron listar los pedidos.", e);
        }
    }

    public Pedido buscarPorId(Long id) {
        try {
            Pedido pedido = pedidoDAO.buscarPorId(id);

            if (pedido == null) {
                throw new EntityNotFoundException("No existe el pedido.");
            }

            return pedido;

        } catch (DAOException e) {
            throw new ServiceException("No se pudo buscar el pedido.", e);
        }
    }

    public void actualizar(Pedido pedido) {
        try {
            buscarPorId(pedido.getId());
            pedidoDAO.actualizar(pedido);
        } catch (DAOException e) {
            throw new ServiceException("No se pudo actualizar el pedido.", e);
        }
    }

    public void eliminarLogico(Long id) {
        Connection conn = null;

        try {
            buscarPorId(id);

            conn = ConexionDB.getConnection();
            conn.setAutoCommit(false);

            detallePedidoDAO.eliminarLogicoPorPedido(id, conn);
            pedidoDAO.eliminarLogico(id, conn);

            conn.commit();

        } catch (SQLException | DAOException e) {
            hacerRollback(conn);
            throw new ServiceException("No se pudo eliminar el pedido.", e);

        } finally {
            cerrarConexion(conn);
        }
    }

    private void hacerRollback(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                throw new ServiceException("Error al realizar rollback.", e);
            }
        }
    }

    private void cerrarConexion(Connection conn) {
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                throw new ServiceException("Error al cerrar la conexión.", e);
            }
        }
    }
}
