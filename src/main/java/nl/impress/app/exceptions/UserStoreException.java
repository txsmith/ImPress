package nl.impress.app.exceptions;


public abstract class UserStoreException extends RuntimeException {
    public static class AlreadyExists extends UserStoreException {
        @Override
        public String getMessage() {
            return "The specified username already exists.";
        }
    }
    public static class NotFound extends UserStoreException {
        @Override
        public String getMessage() {
            return "The specified user does not exist.";
        }


    }
}
