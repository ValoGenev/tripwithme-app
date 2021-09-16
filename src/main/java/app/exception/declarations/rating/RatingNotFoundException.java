package app.exception.declarations.rating;

public class RatingNotFoundException extends RuntimeException {

    public RatingNotFoundException(String message) {
        super(message);
    }
}
