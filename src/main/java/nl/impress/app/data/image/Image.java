package nl.impress.app.data.image;

import nl.impress.app.exceptions.ImageStoreException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.google.common.base.Preconditions.checkNotNull;
/**
 * Immutable structure holding an actual image.
 */
public class Image {

    private final BufferedImage buffer;

    public Image(InputStream stream)
            throws IOException, ImageStoreException.NoImage {
        this.buffer = ImageIO.read(stream);
        if (this.buffer == null) {
            throw new ImageStoreException.NoImage();
        }
    }
    private Image(BufferedImage newBuffer) {
        checkNotNull(newBuffer);
        this.buffer = newBuffer;
    }
    protected Image(Image i) {
        checkNotNull(i);

        this.buffer = i.buffer;
    }

    public int getWidth() {
        return this.buffer.getWidth();
    }

    public int getHeight() {
        return this.buffer.getHeight();
    }

    public Image scaleToWidth(int width) {
        int newHeight = (int)Math.round(this.getHeight()*((double)width / this.getWidth()));
        BufferedImage result = new BufferedImage(
                width,
                newHeight,
                buffer.getType());
        Graphics2D g2d = result.createGraphics();
        g2d.drawImage(buffer, 0, 0, result.getWidth(), result.getHeight(), null);
        g2d.dispose();
        return new Image(result);
    }

    public byte[] getBytes() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(buffer, "jpg", baos);
        baos.flush();
        byte[] bytes = baos.toByteArray();
        baos.close();
        return bytes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Image)) return false;

        Image image = (Image) o;

        return buffer.equals(image.buffer);

    }

    @Override
    public int hashCode() {
        return buffer.hashCode();
    }
}
