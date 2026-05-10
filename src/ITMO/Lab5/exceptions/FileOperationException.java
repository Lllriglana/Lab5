package ITMO.Lab5.exceptions;

/**
 * Signals file read/write problems for collection persistence.
 */
public class FileOperationException extends Exception {
    public FileOperationException(String message) {
        super(message);
    }

    public FileOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
