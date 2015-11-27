package nl.impress.app.data;

import nl.impress.app.data.image.Image;
import nl.impress.app.data.image.ImageMetaData;
import nl.impress.app.data.image.MediumImage;
import nl.impress.app.data.image.Thumbnail;
import nl.impress.app.exceptions.ImageStoreException;

import java.util.List;

public interface ImageStorage {

    Thumbnail findThumbnailById(String imageId) throws ImageStoreException.NotFound;
    MediumImage findMediumById(String imageId) throws ImageStoreException.NotFound;
    Image findOriginalById(String imageId) throws ImageStoreException.NotFound;
    String storeImage(Upload u);

    ImageMetaData findImageMetaDataById(String imageId) throws ImageStoreException.NotFound;
    List<ImageMetaData> findRecentImages();

    /**
     * Find all images that a user has uploaded.
     *
     * @param username The user to find all images for.
     * @return All unique image ID's, empty if the user does not exist
     */
    List<ImageMetaData> findImagesByUser(String username);

    void removeImage(String imageId);

    int getMaxRecentImages();
}
