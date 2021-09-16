package app.exception.declarations.common;

public class AlreadyExistingResourceException extends RuntimeException {

    public AlreadyExistingResourceException(String message) {
        super(message);
    }
}
