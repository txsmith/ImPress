package nl.impress.app.endpoints;


public class LoginError {
    public static final String NOT_FOUND = "notFound";
    public static final String INVALID_CREDENTIALS = "invalid";

    private final String message;

    public LoginError(String errorValue) {
        if (errorValue.equals("notFound")) {
            this.message = "This username is not registered";
        } else {
            this.message = "Invalid username or password";
        }
    }

    public String getMessage() {
        return message;
    }
}
