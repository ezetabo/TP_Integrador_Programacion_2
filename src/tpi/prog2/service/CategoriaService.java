package tpi.prog2.service;

import java.util.List;
import tpi.prog2.dao.CategoriaDAO;
import tpi.prog2.entities.Categoria;
import tpi.prog2.exception.DAOException;
import tpi.prog2.exception.EntityNotFoundException;
import tpi.prog2.exception.ServiceException;

/**
 *
 * @author Ezequiel Taboada
 */
public class CategoriaService {

    private final CategoriaDAO categoriaDAO;

    public CategoriaService() {
        this.categoriaDAO = new CategoriaDAO();
    }

    public Categoria crear(String nombre, String descripcion) {
        try {
            Categoria categoria = new Categoria(nombre, descripcion);
            return categoriaDAO.crear(categoria);
        } catch (IllegalArgumentException e) {
            throw new ServiceException(e.getMessage(), e);
        } catch (DAOException e) {
            throw new ServiceException("No se pudo crear la categoría.", e);
        }
    }

    public List<Categoria> listar() {
        try {
            return categoriaDAO.listar();
        } catch (DAOException e) {
            throw new ServiceException("No se pudieron listar las categorías.", e);
        }
    }

    public Categoria buscarPorId(Long id) {
        try {
            Categoria categoria = categoriaDAO.buscarPorId(id);

            if (categoria == null) {
                throw new EntityNotFoundException("No existe una categoría con ese id.");
            }

            return categoria;

        } catch (DAOException e) {
            throw new ServiceException("No se pudo buscar la categoría.", e);
        }
    }

    public void actualizar(Long id, String nombre, String descripcion) {
        try {
            Categoria existente = buscarPorId(id);

            Categoria actualizada = new Categoria(
                    existente.getId(),
                    existente.isEliminado(),
                    existente.getCreatedAt(),
                    nombre,
                    descripcion
            );

            categoriaDAO.actualizar(actualizada);

        } catch (IllegalArgumentException e) {
            throw new ServiceException(e.getMessage(), e);
        } catch (DAOException e) {
            throw new ServiceException("No se pudo actualizar la categoría.", e);
        }
    }

    public void eliminarLogico(Long id) {
        try {
            buscarPorId(id);
            categoriaDAO.eliminarLogico(id);
        } catch (DAOException e) {
            throw new ServiceException("No se pudo eliminar la categoría.", e);
        }
    }
}
