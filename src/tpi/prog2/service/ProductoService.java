package tpi.prog2.service;

import java.util.List;
import tpi.prog2.dao.ProductoDAO;
import tpi.prog2.dao.CategoriaDAO;
import tpi.prog2.entities.Producto;
import tpi.prog2.entities.Categoria;
import tpi.prog2.exception.DAOException;
import tpi.prog2.exception.EntityNotFoundException;
import tpi.prog2.exception.ServiceException;

/**
 *
 * @author Ezequiel Taboada
 */
public class ProductoService {

    private final ProductoDAO productoDAO;
    private final CategoriaDAO categoriaDAO;

    public ProductoService() {
        this.productoDAO = new ProductoDAO();
        this.categoriaDAO = new CategoriaDAO();
    }

    public Producto crear(String nombre, Double precio, String descripcion,
            int stock, String imagen, boolean disponible, Long categoriaId) {

        try {
            Categoria categoria = categoriaDAO.buscarPorId(categoriaId);

            if (categoria == null) {
                throw new EntityNotFoundException("No existe la categoría.");
            }

            Producto producto = new Producto(
                    nombre, precio, descripcion, stock, imagen, disponible, categoria
            );

            return productoDAO.crear(producto);

        } catch (IllegalArgumentException e) {
            throw new ServiceException(e.getMessage(), e);
        } catch (DAOException e) {
            throw new ServiceException("No se pudo crear el producto.", e);
        }
    }

    public List<Producto> listar() {
        try {
            return productoDAO.listar();
        } catch (DAOException e) {
            throw new ServiceException("No se pudieron listar los productos.", e);
        }
    }

    public Producto buscarPorId(Long id) {
        try {
            Producto producto = productoDAO.buscarPorId(id);

            if (producto == null) {
                throw new EntityNotFoundException("No existe el producto.");
            }

            return producto;

        } catch (DAOException e) {
            throw new ServiceException("No se pudo buscar el producto.", e);
        }
    }

    public void actualizar(Long id, String nombre, Double precio, String descripcion,
            int stock, String imagen, boolean disponible, Long categoriaId) {

        try {
            Producto existente = buscarPorId(id);

            Categoria categoria = categoriaDAO.buscarPorId(categoriaId);

            if (categoria == null) {
                throw new EntityNotFoundException("No existe la categoría.");
            }

            Producto actualizado = new Producto(
                    existente.getId(),
                    existente.isEliminado(),
                    existente.getCreatedAt(),
                    nombre,
                    precio,
                    descripcion,
                    stock,
                    imagen,
                    disponible,
                    categoria
            );

            productoDAO.actualizar(actualizado);

        } catch (IllegalArgumentException e) {
            throw new ServiceException(e.getMessage(), e);
        } catch (DAOException e) {
            throw new ServiceException("No se pudo actualizar el producto.", e);
        }
    }

    public void eliminarLogico(Long id) {
        try {
            buscarPorId(id);
            productoDAO.eliminarLogico(id);
        } catch (DAOException e) {
            throw new ServiceException("No se pudo eliminar el producto.", e);
        }
    }
}
