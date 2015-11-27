package nl.impress.app.data;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.Singleton;
import nl.impress.app.data.image.Image;
import nl.impress.app.data.image.ImageMetaData;
import nl.impress.app.data.image.MediumImage;
import nl.impress.app.data.image.Thumbnail;
import nl.impress.app.exceptions.ImageStoreException;

import java.util.List;
import java.util.Map;

@Singleton
public class InMemoryImageStorage implements ImageStorage {

    private final Map<String, ImageMetaData> imagesById;
    private final Map<String, Thumbnail> thumbnailsById;
    private final Map<String, MediumImage> mediumImagesById;
    private final Map<String, Image> fullImagesById;

    private final Map<String, List<ImageMetaData>> uploadsByUser;
    private final List<ImageMetaData> recentUploads;

    private static final int RECENT_IMAGE_AMOUNT = 10;

    public InMemoryImageStorage() {
        this.imagesById = Maps.newHashMap();
        this.uploadsByUser = Maps.newHashMap();
        this.thumbnailsById = Maps.newHashMap();
        this.mediumImagesById = Maps.newHashMap();
        this.fullImagesById = Maps.newHashMap();
        this.recentUploads = Lists.newArrayList();
    }

    @Override
    public Thumbnail findThumbnailById(String imageId) throws ImageStoreException.NotFound {
        Thumbnail image = thumbnailsById.get(imageId);
        if (image == null) {
            throw new ImageStoreException.NotFound();
        } else {
            return image;
        }
    }

    @Override
    public MediumImage findMediumById(String imageId) throws ImageStoreException.NotFound {
        MediumImage maybeImage = mediumImagesById.get(imageId);
        if (maybeImage == null) {
            throw new ImageStoreException.NotFound();
        } else {
            return maybeImage;
        }
    }

    @Override
    public Image findOriginalById(String imageId) throws ImageStoreException.NotFound {
        Image image = fullImagesById.get(imageId);
        if (image == null) {
            throw new ImageStoreException.NotFound();
        } else {
            return image;
        }
    }

    @Override
    public List<ImageMetaData> findRecentImages() {
        return ImmutableList.copyOf(recentUploads);
    }

    @Override
    public ImageMetaData findImageMetaDataById(String imageId) throws ImageStoreException.NotFound {
        ImageMetaData result = imagesById.get(imageId);
        if (result == null) {
            throw new ImageStoreException.NotFound();
        } else {
            return result;
        }
    }

    @Override
    public List<ImageMetaData> findImagesByUser(String username) {
        return ImmutableList.copyOf(findImagesByUser_mutable(username));
    }

    private List<ImageMetaData> findImagesByUser_mutable(String username) {
        List<ImageMetaData> result = uploadsByUser.get(username);
        if (result == null) {
            result = Lists.newArrayList();
            uploadsByUser.put(username, result);
        }
        return result;
    }

    @Override
    public void removeImage(String imageId) {
        ImageMetaData data = imagesById.remove(imageId);
        thumbnailsById.remove(imageId);
        mediumImagesById.remove(imageId);
        fullImagesById.remove(imageId);
        recentUploads.remove(data);
        List<ImageMetaData> userUploads = uploadsByUser.get(data.getUploader());
        userUploads.remove(data);
    }

    @Override
    public int getMaxRecentImages() {
        return RECENT_IMAGE_AMOUNT;
    }

    @Override
    public String storeImage(Upload upload) {
        List<ImageMetaData> userUploads = findImagesByUser_mutable(upload.getUserID());
        ImageMetaData metaData = prepareMetaData(upload, userUploads);
        String imageID = metaData.getImageID();

        storeImage(upload, imageID);
        imagesById.put(imageID, metaData);
        userUploads.add(0, metaData);
        updateRecentUploads(metaData);

        return imageID;
    }

    private void updateRecentUploads(ImageMetaData metaData) {
        recentUploads.add(0, metaData);
        if (recentUploads.size() > RECENT_IMAGE_AMOUNT) {
            recentUploads.remove(RECENT_IMAGE_AMOUNT);
        }
    }

    private void storeImage(Upload upload, String imageID) {
        thumbnailsById.put(imageID, upload.getThumbnail());
        mediumImagesById.put(imageID, upload.getMediumImage());
        fullImagesById.put(imageID, upload.getOriginalImage());
    }

    private ImageMetaData prepareMetaData(Upload upload, List<ImageMetaData> userUploads) {
        return new ImageMetaData(
                    upload.getUserID() + "-" + userUploads.size(),
                    upload.getTitle(),
                    upload.getDescription(),
                    upload.getUserID()
            );
    }
}
