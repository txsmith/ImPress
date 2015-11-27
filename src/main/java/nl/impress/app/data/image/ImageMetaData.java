package nl.impress.app.data.image;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Immutable structure for holding information associated with an image.
 */
public class ImageMetaData {
    private final String imageID;
    private final String title;
    private final String description;
    private final String uploader;

    public ImageMetaData(String imageID,
                         String title,
                         String description,
                         String uploader) {
        checkNotNull(imageID, title, description, uploader);

        this.imageID = imageID;
        this.title = title;
        this.description = description;
        this.uploader = uploader;
    }

    public String getImageID() {
        return imageID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUploader() {
        return uploader;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImageMetaData)) return false;

        ImageMetaData that = (ImageMetaData) o;

        return imageID.equals(that.imageID);
    }

    @Override
    public int hashCode() {
        return imageID.hashCode();
    }
}
