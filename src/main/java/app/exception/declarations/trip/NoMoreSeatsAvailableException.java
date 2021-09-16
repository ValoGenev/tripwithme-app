package app.exception.declarations.trip;

public class NoMoreSeatsAvailableException extends RuntimeException {
    public NoMoreSeatsAvailableException(String message) {
        super(message);
    }
}
