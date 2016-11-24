package by.epam.filmrating.exception;

/**
 *  The {@code ApplicationException} class is an exception that occurs
 *  in the command layer, where data from jsp have wrong format.
 *  @author Dmitry Rafalovich
 */
public class ApplicationException extends Exception {

    public ApplicationException() {
    }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
