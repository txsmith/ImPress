package nl.impress.app.endpoints;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;
import nl.impress.app.data.ImageStorage;
import nl.impress.app.data.image.Image;
import nl.impress.app.data.image.ImageMetaData;
import nl.impress.app.data.image.MediumImage;
import nl.impress.app.data.image.Thumbnail;
import nl.impress.app.data.Upload;
import nl.impress.app.guice.CurrentUser;
import nl.impress.app.guice.UserProvider;
import nl.impress.app.data.UserStorage;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

@Path("/")
@Produces("image/jpeg")
@RequestScoped
public class ImageResource {

    @Inject
    private UserStorage userAccess;

    @Inject
    private ImageStorage imageAccess;

    @Inject
    @CurrentUser
    private UserProvider currentUserProvider;

    @GET
    @Path("/image/t/{id}")
    public byte[] getThumbnail(@PathParam("id") String imageId) throws IOException {
        Thumbnail img = imageAccess.findThumbnailById(imageId);
        return img.getBytes();
    }

    @GET
    @Path("/image/m/{id}")
    public byte[] getMediumImage(@PathParam("id") String imageId) throws IOException {
        MediumImage img = imageAccess.findMediumById(imageId);
        return img.getBytes();
    }

    @GET
     @Path("/image/o/{id}")
     public byte[] getOriginalImage(@PathParam("id") String imageId) throws IOException {
        Image img = imageAccess.findOriginalById(imageId);
        return img.getBytes();
    }

    @GET
    @Path("/image/user/{id}")
    public byte[] getProfilePicture(@PathParam("id") String userId) throws IOException {
        Image img = userAccess.getProfilePicture(userId);
        return img.getBytes();
    }

    @POST
    @Path("upload")
    public Response doUpload(MultipartFormDataInput form) throws IOException {
        InputPart imageFormData = form.getFormDataMap().get("uploadFile").get(0);
        InputPart titleFormData = form.getFormDataMap().get("title").get(0);
        InputPart descriptionFormData = form.getFormDataMap().get("text").get(0);

        InputStream imageStream = imageFormData.getBody(InputStream.class, null);

        String title = titleFormData.getBodyAsString();
        String description = descriptionFormData.getBodyAsString();

        Upload u = new Upload(currentUserProvider.get(), title, description, imageStream);
        String imageId = imageAccess.storeImage(u);

        return Response.seeOther(URI.create("/me")).build();

    }

    @GET
    @Path("/image/remove/{id}")
    public Response doRemove(@PathParam("id") String imageId) {
        ImageMetaData imageData = imageAccess.findImageMetaDataById(imageId);
        String currentUser = currentUserProvider.get().getUsername();
        if (imageData.getUploader().equals(currentUser)) {
            imageAccess.removeImage(imageId);
            return Response.seeOther(URI.create("/me")).build();
        } else {
            return Response.seeOther(URI.create("/")).build();
        }
    }
}
