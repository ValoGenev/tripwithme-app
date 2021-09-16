package app.exception.declarations.trip;

public class DriverCannotBePassengerException extends RuntimeException {
    public DriverCannotBePassengerException(String message) {
        super(message);
    }
}
