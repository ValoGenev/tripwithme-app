package app.exception.declarations.trip;

public class CannotUpdateTripWithPassengersException extends RuntimeException {

    public CannotUpdateTripWithPassengersException(String message) {
        super(message);
    }
}
