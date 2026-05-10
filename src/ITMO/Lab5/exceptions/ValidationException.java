package ITMO.Lab5.exceptions;

/**
 * Signals invalid data in the domain model or user input.
 */
public class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
