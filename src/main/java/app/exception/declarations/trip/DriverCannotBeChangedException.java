package app.exception.declarations.trip;


public class DriverCannotBeChangedException extends RuntimeException {

    public DriverCannotBeChangedException(String message) {
        super(message);
    }
}
