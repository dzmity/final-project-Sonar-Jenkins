package by.epam.filmrating.exception;

/**
 *  The {@code ServiceException} class is an exception that occurs
 *  in the Service layer.
 *  @author Dmitry Rafalovich
 */
public class ServiceException extends Exception {

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
