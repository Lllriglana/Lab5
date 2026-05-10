package ITMO.Lab5.exceptions;

/**
 * Signals input source problems while reading command or field values.
 */
public class InputException extends Exception {
    public InputException(String message) {
        super(message);
    }

    public InputException(String message, Throwable cause) {
        super(message, cause);
    }
}
