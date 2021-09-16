package app.exception.declarations.notifcation;

public class NotificationNotFoundException extends RuntimeException{

    public NotificationNotFoundException(String message) {
        super(message);
    }
}
