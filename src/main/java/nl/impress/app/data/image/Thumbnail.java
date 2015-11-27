package nl.impress.app.data.image;

import com.google.common.base.Optional;

import java.io.IOException;

/**
 * Immutable structure holding a thumbnail sized image
 */
public class Thumbnail extends Image {

    private static final int THUMB_WIDTH = 320;

    protected Thumbnail(Image image) {
        super(image);
    }

    /**
     * Create a thumbnail-sized image (320px in width).
     * This method resizes and returns a new image,
     * the original is left untouched.
     *
     * @param original The image to resize.
     * @return Either a valid Thumbnail if the original image's width >= 320px,
     *         or {@link Optional#absent()} if the image was too small.
     * @throws IOException
     */
    public static Optional<Thumbnail> create(Image original) throws IOException {
        
        if (original.getWidth() > THUMB_WIDTH) {
            Thumbnail t = new Thumbnail(original.scaleToWidth(THUMB_WIDTH));
            return Optional.of(t);
        } else {
            return Optional.absent();
        }
    }
}
