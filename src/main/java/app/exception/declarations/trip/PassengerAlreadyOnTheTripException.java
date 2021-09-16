package app.exception.declarations.trip;

public class PassengerAlreadyOnTheTripException extends RuntimeException {
    public PassengerAlreadyOnTheTripException(String message) {
        super(message);
    }
}
