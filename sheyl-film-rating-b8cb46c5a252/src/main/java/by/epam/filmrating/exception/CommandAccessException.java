package by.epam.filmrating.exception;

/**
 *  The {@code CommandAccessException} class is an exception that occurs
 *  in the command layer, where access is prohibited to carry out commands
 *  for the current user.
 *  @author Dmitry Rafalovich
 */
public class CommandAccessException extends Exception {

    public CommandAccessException() {
        super();
    }

    public CommandAccessException(String message) {
        super(message);
    }

    public CommandAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandAccessException(Throwable cause) {
        super(cause);
    }
}
