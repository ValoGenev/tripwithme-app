package app.model;


public enum UserPermission {

    USER_GET_ALL("user:get:all"),
    USER_GET_ONE("user:get:one"),
    USER_CREATE("user:create"),
    USER_UPDATE("user:update"),
    USER_DELETE("user:delete");

    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
