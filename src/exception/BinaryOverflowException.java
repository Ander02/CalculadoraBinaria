package exception;

/**
 *
 * @author Anderson
 */
public class BinaryOverflowException extends Exception {

    private String message;

    public BinaryOverflowException() {
    }

    public BinaryOverflowException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
