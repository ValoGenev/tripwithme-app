package app.exception.declarations.search;

public class OneSearchPerTimeIntervalException extends RuntimeException {
    public OneSearchPerTimeIntervalException(String message) {
        super(message);
    }
}
