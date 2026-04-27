package tpi.prog2.exception;

/**
 *
 * @author Ezequiel Taboada
 */
public class EntityNotFoundException extends ServiceException {

    public EntityNotFoundException(String mensaje) {
        super(mensaje);
    }
}
