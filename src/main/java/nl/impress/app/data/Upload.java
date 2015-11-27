package nl.impress.app.data;

import nl.impress.app.data.image.Image;
import nl.impress.app.data.image.MediumImage;
import nl.impress.app.data.image.Thumbnail;
import nl.impress.app.exceptions.ImageStoreException;

import java.io.IOException;
import java.io.InputStream;

import static com.google.common.base.Preconditions.checkNotNull;

public class Upload {
    private final User user;
    private final String title;
    private final String description;
    private final Thumbnail thumb;
    private final MediumImage mediumImage;
    private final Image fullResolution;

    public Upload(User user, String title, String description, InputStream inputStream) throws IOException {
        checkNotNull(user, title, description, inputStream);

        this.user = user;
        this.title = title;
        this.description = description;
        this.fullResolution = new Image(inputStream);
        try {
            this.thumb = Thumbnail.create(this.fullResolution).get();
            this.mediumImage = MediumImage.create(this.fullResolution).get();
        } catch (IllegalStateException ex) {
            throw new ImageStoreException.TooSmall();
        }


    }

    public String getUserID() {
        return this.user.getUsername();
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Thumbnail getThumbnail() {
        return thumb;
    }

    public MediumImage getMediumImage() {
        return mediumImage;
    }

    public Image getOriginalImage() {
        return fullResolution;
    }
}
