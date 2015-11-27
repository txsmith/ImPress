package nl.impress.app.data.image;

import com.google.common.base.Optional;
import java.io.IOException;

/**
 * Immutable structure for holding a medium sized image.
 */
public class MediumImage extends Image {

    private static final int MEDIUM_WIDTH = 840;

    protected MediumImage(Image i) {
        super(i);
    }

    /**
     * Create an image of medium size (840px in width).
     * This method resizes and returns a new image,
     * the original is left untouched.
     *
     * @param original The image to resize.
     * @return Either a valid MediumImage if the original image's width >= 840px,
     *         Or {@link Optional#absent()} if the image was too small.
     * @throws IOException
     */
    public static Optional<MediumImage> create(Image original) throws IOException {

        if (original.getWidth() > MEDIUM_WIDTH) {
            MediumImage m = new MediumImage(original.scaleToWidth(MEDIUM_WIDTH));
            return Optional.of(m);
        } else {
            return Optional.absent();
        }
    }
}
