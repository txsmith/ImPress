package nl.impress.app.data;

import nl.impress.app.data.image.Image;
import nl.impress.app.data.image.ImageMetaData;
import nl.impress.app.data.image.MediumImage;
import nl.impress.app.data.image.Thumbnail;
import nl.impress.app.exceptions.ImageStoreException;

import java.util.List;


public class HBaseImageStorage implements ImageStorage {

    private static final int RECENT_IMAGE_AMOUNT = 10;

    @Override
    public Thumbnail findThumbnailById(String imageId) throws ImageStoreException.NotFound {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public MediumImage findMediumById(String imageId) throws ImageStoreException.NotFound {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public Image findOriginalById(String imageId) throws ImageStoreException.NotFound {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public String storeImage(Upload u) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public ImageMetaData findImageMetaDataById(String imageId) throws ImageStoreException.NotFound {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public List<ImageMetaData> findRecentImages() {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public List<ImageMetaData> findImagesByUser(String username) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public void removeImage(String imageId) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public int getMaxRecentImages() {
        return RECENT_IMAGE_AMOUNT;
    }
}
