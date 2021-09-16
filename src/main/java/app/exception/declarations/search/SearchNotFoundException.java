package app.exception.declarations.search;

public class SearchNotFoundException extends RuntimeException {

    public SearchNotFoundException(String message) {
        super(message);
    }
}
