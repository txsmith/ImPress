package nl.impress.app.exceptions;

public abstract class ImageStoreException extends RuntimeException {
    public static class NotFound extends ImageStoreException {
        @Override
        public String getMessage() {
            return "This image does not exist.";
        }
    }

    public static class TooSmall extends ImageStoreException {
        @Override
        public String getMessage() {
            return "The uploaded image was too small. Please use an image of at least 840 pixels wide.";
        }
    }

    public static class NoImage extends ImageStoreException {
        @Override
        public String getMessage() {
            return "The uploaded file not an image.";
        }
    }
}
