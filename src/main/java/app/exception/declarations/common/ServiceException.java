package app.exception.declarations.common;

public class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }
}
