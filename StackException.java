/**
 * This abstract Exception-Class inherits from RuntimeException is used as a template for all its children.
 */
public abstract class StackException extends RuntimeException{
    public StackException() {}
    public StackException(String message) {
        super(message);
    }
    public StackException(String message, Throwable cause) {
        super(message, cause);
    }
}
