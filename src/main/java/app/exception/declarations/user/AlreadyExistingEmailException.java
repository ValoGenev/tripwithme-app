package app.exception.declarations.user;

public class AlreadyExistingEmailException extends RuntimeException {

    public AlreadyExistingEmailException(String message) {
        super(message);
    }
}
