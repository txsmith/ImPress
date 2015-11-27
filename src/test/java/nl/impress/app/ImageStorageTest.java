package nl.impress.app;

import nl.impress.app.data.ImageStorage;
import nl.impress.app.data.Upload;
import nl.impress.app.data.image.Image;
import nl.impress.app.data.image.ImageMetaData;
import nl.impress.app.data.image.MediumImage;
import nl.impress.app.data.image.Thumbnail;
import nl.impress.app.exceptions.ImageStoreException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public abstract class ImageStorageTest {
    protected ImageStorage imageStore;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    public Upload uploadMock;
    @Mock
    public Upload anotherUploadMock;

    @Before
    public void setup() throws IOException {
        initUploadMock(uploadMock);
        initUploadMock(anotherUploadMock);
    }

    private void initUploadMock(Upload u) throws IOException {
        when(u.getUserID()).thenReturn("test-user-id");
        when(u.getTitle()).thenReturn("test-image-title");
        when(u.getDescription()).thenReturn("test-image-description");

        Thumbnail t = spy(Util.getThumbnail());
        MediumImage m = spy(Util.getMediumImage());
        Image i = spy(Util.getFullImage());

        when(u.getThumbnail()).thenReturn(t);
        when(u.getMediumImage()).thenReturn(m);
        when(u.getOriginalImage()).thenReturn(i);
    }

    @After
    public abstract void cleanup();

    @Test
    public void findThumbnailById_InvalidId_NotFound() {
        thrown.expect(ImageStoreException.NotFound.class);
        imageStore.findThumbnailById("someInvalidId");
    }

    @Test
    public void findMediumById_InvalidId_NotFound() {
        thrown.expect(ImageStoreException.NotFound.class);
        imageStore.findMediumById("someInvalidId");
    }

    @Test
    public void findOriginalById_InvalidId_NotFound() {
        thrown.expect(ImageStoreException.NotFound.class);
        imageStore.findOriginalById("someInvalidId");
    }

    @Test
    public void findImageMetaDataById_InvalidId_NotFound() {
        thrown.expect(ImageStoreException.NotFound.class);
        imageStore.findImageMetaDataById("someInvalidId");
    }

    @Test
    public void findRecentImages_empty() {
        List<ImageMetaData> recent = imageStore.findRecentImages();
        assertTrue(recent.isEmpty());
    }

    @Test
    public void findUploadsForUser_InvalidUser_EmptyList() {
        List<ImageMetaData> uploads = imageStore.findImagesByUser("chuck");
        assertTrue(uploads.isEmpty());
    }

    @Test
    public void storeImage_titleWillBeStored() {
        String id = imageStore.storeImage(uploadMock);
        assertEquals(
                uploadMock.getTitle(),
                imageStore.findImageMetaDataById(id).getTitle());
    }

    @Test
    public void storeImage_descriptionWillBeStored() {
        String id = imageStore.storeImage(uploadMock);
        assertEquals(
                uploadMock.getDescription(),
                imageStore.findImageMetaDataById(id).getDescription());
    }

    @Test
    public void storeImage_uploaderWillBeStored() {
        String id = imageStore.storeImage(uploadMock);
        assertEquals(
                uploadMock.getUserID(),
                imageStore.findImageMetaDataById(id).getUploader());
    }

    @Test
    public void storeImage_thumbWillBeStored() {
        String id = imageStore.storeImage(uploadMock);
        assertEquals(
                uploadMock.getThumbnail(),
                imageStore.findThumbnailById(id));
    }

    @Test
    public void storeImage_mediumWillBeStored() {
        String id = imageStore.storeImage(uploadMock);
        assertEquals(
                uploadMock.getMediumImage(),
                imageStore.findMediumById(id));
    }

    @Test
    public void storeImage_originalWillBeStored() {
        String id = imageStore.storeImage(uploadMock);
        assertEquals(
                uploadMock.getOriginalImage(),
                imageStore.findOriginalById(id));
    }

    @Test
    public void storeImage_updatedRecentImages() {
        List<ImageMetaData> recentImages = imageStore.findRecentImages();
        imageStore.storeImage(uploadMock);
        assertEquals(
                "The amount of recent images should have increased when a new image gets stored.",
                recentImages.size() + 1,
                imageStore.findRecentImages().size());
    }

    @Test
    public void storeImage_updatesRecent_appendsToFront() {
        imageStore.storeImage(uploadMock);
        String expectedID = imageStore.storeImage(anotherUploadMock);
        List<ImageMetaData> recentImages = imageStore.findRecentImages();
        assertEquals(
                "The image that was inserted last should be in the front of the list.",
                expectedID,
                recentImages.get(0).getImageID());
    }

    @Test
    public void storeImage_updatesRecent_removesLast() {
        String shouldBeRemoved = imageStore.storeImage(uploadMock);
        for (int i = 0; i < imageStore.getMaxRecentImages(); i++) {
            imageStore.storeImage(anotherUploadMock);
        }
        List<ImageMetaData> recentImages = imageStore.findRecentImages();
        for (ImageMetaData imgData : recentImages) {
            assertNotEquals(
                    "The image: '" + shouldBeRemoved + "' should have been removed from the list of recent images.",
                    shouldBeRemoved,
                    imgData.getImageID());
        }
    }

    @Test
    public void storeImage_associatesImageWithUser() {
        List<ImageMetaData> userImages = imageStore.findImagesByUser(uploadMock.getUserID());
        imageStore.storeImage(uploadMock);
        assertEquals(
                "The amount of user images should have increased when a new image gets stored.",
                userImages.size() + 1,
                imageStore.findImagesByUser(uploadMock.getUserID()).size());
    }

    @Test
    public void storeImage_updatesUserImages_appendsToFront() {
        imageStore.storeImage(uploadMock);
        String expectedID = imageStore.storeImage(anotherUploadMock);
        List<ImageMetaData> userImages = imageStore.findImagesByUser(uploadMock.getUserID());
        assertEquals(
                "The image that was inserted last should be in the front of the list.",
                expectedID,
                userImages.get(0).getImageID());
    }
}
