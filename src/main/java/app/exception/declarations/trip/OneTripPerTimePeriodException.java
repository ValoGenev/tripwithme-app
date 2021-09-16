package app.exception.declarations.trip;

public class OneTripPerTimePeriodException extends RuntimeException {

    public OneTripPerTimePeriodException(String message) {
        super(message);
    }
}
