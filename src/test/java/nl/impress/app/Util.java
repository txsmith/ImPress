package nl.impress.app;


import com.google.common.base.Optional;
import nl.impress.app.data.image.Image;
import nl.impress.app.data.image.MediumImage;
import nl.impress.app.data.image.Thumbnail;

import java.io.IOException;
import java.io.InputStream;

public class Util {

    private static Image cacheOriginal;

    public static Image getFullImage() throws IOException {
        if (cacheOriginal == null) {
            InputStream inStream = Util.class.getResourceAsStream("/mock-image.jpg");
            cacheOriginal = new Image(inStream);
        }
        return cacheOriginal;
    }

    public static MediumImage getMediumImage() throws IOException {
        Optional<MediumImage> t = MediumImage.create(getFullImage());
        return t.get();
    }

    public static Thumbnail getThumbnail() throws IOException {
        Optional<Thumbnail> t = Thumbnail.create(getFullImage());
        return t.get();
    }
}
