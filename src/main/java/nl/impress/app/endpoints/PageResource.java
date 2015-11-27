package nl.impress.app.endpoints;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;
import freemarker.template.TemplateException;
import nl.impress.app.FreeMarkerUtil;
import nl.impress.app.data.ImageStorage;
import nl.impress.app.data.User;
import nl.impress.app.guice.UserProvider;
import nl.impress.app.data.UserStorage;
import nl.impress.app.data.image.ImageMetaData;
import nl.impress.app.guice.CurrentUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;


@Path("/")
@Produces(MediaType.TEXT_HTML)
@RequestScoped
public class PageResource {

    private static final Logger log = LogManager.getLogger();

    @Inject
    private FreeMarkerUtil freeMarkerUtil;

    @Inject
    @CurrentUser
    private UserProvider currentUserProvider;

    @Inject
    private UserStorage userAccess;

    @Inject
    private ImageStorage imageAccess;

    @GET
    public String showHomePage()
            throws IOException, TemplateException {

        List<ImageMetaData> recentImages = imageAccess.findRecentImages();
        Map<String, Object> model =  ImmutableMap.of(
                "user", currentUserProvider.get(),
                "recentImages", recentImages);
        return freeMarkerUtil.getTemplateHTML("home.ftl",model);
    }

    @GET
    @Path("upload")
    public String showUploadPage() throws IOException, TemplateException {
        Map<String, Object> model =  ImmutableMap.of(
                "user", (Object)currentUserProvider.get());
        return freeMarkerUtil.getTemplateHTML("upload.ftl", model);
    }

    @GET
    @Path("me")
    public String showProfilePage()
            throws IOException, TemplateException {
        User currentUser = currentUserProvider.get();
        List<ImageMetaData> userImages = imageAccess.findImagesByUser(currentUser.getUsername());
        Map<String, Object> model =  ImmutableMap.of(
                "user", currentUser,
                "userImages", userImages);

        return freeMarkerUtil.getTemplateHTML("me.ftl", model);
    }

    @GET
    @Path("me/edit")
    public String showEditPage() throws IOException, TemplateException {
        Map<String, Object> model = Maps.newHashMap();
        model.put("user", currentUserProvider.get());
        return freeMarkerUtil.getTemplateHTML("edit.ftl", model);
    }

    @GET
    @Path("user/{id}")
    public String showUserProfile(@PathParam("id") String userId)
            throws IOException, TemplateException {

        User viewUser = userAccess.findByUsername(userId);
        List<ImageMetaData> userImages = imageAccess.findImagesByUser(userId);
        Map<String, Object> model =  ImmutableMap.of(
                "user", viewUser,
                "userImages", userImages);
        return freeMarkerUtil.getTemplateHTML("profile.ftl", model);
    }

    @GET
    @Path("view/{id}")
    public String showImage(@PathParam("id") String imageId)
            throws IOException, TemplateException {

        User currentUser = currentUserProvider.get();
        ImageMetaData image = imageAccess.findImageMetaDataById(imageId);
        User uploader = userAccess.findByUsername(image.getUploader());

        Map<String, Object> model =  ImmutableMap.of(
                "user", currentUser,
                "uploader", uploader,
                "viewImage", image);
        return freeMarkerUtil.getTemplateHTML("view.ftl", model);
    }

    @GET
    @Path("login")
    public String showLoginPage(@QueryParam("error") LoginError error) throws IOException, TemplateException {
        Map<String, Object> model = Maps.newHashMap();
        model.put("error", error);
        return freeMarkerUtil.getTemplateHTML("login.ftl", model);
    }

    @GET
    @Path("logoff")
    public Response doLogoff(@Context HttpServletRequest request) {
        request.getSession(false).invalidate();
        return Response.seeOther(URI.create("/login")).build();
    }

    @GET
    @Path("signup")
    public String showSignupPage() throws IOException, TemplateException {
        return freeMarkerUtil.getTemplateHTML("signup.ftl");
    }

    @GET
    @Path("favicon.ico")
    public Response favicon() throws URISyntaxException {
        return Response.seeOther(new URI("/static/favicon.ico")).build();
    }
}
