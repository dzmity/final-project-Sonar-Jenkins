package by.epam.filmrating.exception;

/**
 *  The {@code DAOException} class is an exception that occurs
 *  in the DAO layer.
 *  @author Dmitry Rafalovich
 */
public class DAOException extends Exception {

    public DAOException() {
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(Throwable cause) {
        super(cause);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
