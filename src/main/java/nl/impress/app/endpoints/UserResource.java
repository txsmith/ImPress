package nl.impress.app.endpoints;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;
import nl.impress.app.exceptions.UserStoreException;
import nl.impress.app.data.User;
import nl.impress.app.data.UserStorage;
import nl.impress.app.data.image.Image;
import nl.impress.app.data.image.Thumbnail;
import nl.impress.app.exceptions.ImageStoreException;
import nl.impress.app.guice.CurrentUser;
import nl.impress.app.guice.UserProvider;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

@Path("/")
@Produces(MediaType.TEXT_HTML)
@RequestScoped
public class UserResource {

    @Inject
    private UserStorage userAccess;

    @Inject
    @CurrentUser
    private UserProvider currentUserProvider;

    @POST
    @Path("login")
    public Response doLogin(@FormParam("username") String username,
                            @FormParam("password") String password,
                            @Context HttpServletRequest request) {
        try {
            User user = userAccess.findByUsername(username);

            if (user.getPassword().equals(password)) {
                request.getSession().setAttribute("user", user);
                return Response.seeOther(URI.create("/me")).build();
            } else {
                return Response.seeOther(URI.create("/login?error=" + LoginError.INVALID_CREDENTIALS)).build();
            }
        } catch (UserStoreException.NotFound e) {
            return Response.seeOther(URI.create("/login?error=" + LoginError.NOT_FOUND)).build();
        }
    }

    @POST
    @Path("signup")
    public Response doSignup(@FormParam("username") String username,
                             @FormParam("password") String password,
                             @FormParam("bio") String userBio,
                             @Context HttpServletRequest request) {

        User newUser = new User(username, password, userBio);
        userAccess.storeUser(newUser);
        request.getSession().setAttribute("user", newUser);

        return Response.seeOther(URI.create("/me")).build();
    }

    @POST
    @Path("me/edit")
    public Response doEdit(MultipartFormDataInput form,
                           @Context HttpServletRequest request) throws IOException {
        InputPart passwordField = form.getFormDataMap().get("password").get(0);
        InputPart bioField      = form.getFormDataMap().get("bio").get(0);
        InputPart imageField    = form.getFormDataMap().get("profileImage").get(0);

        User currentUser = currentUserProvider.get();
        String username  = currentUser.getUsername();
        String password  = passwordField.getBodyAsString();
        String bio = bioField.getBodyAsString();

        if (password.isEmpty()) {
            password = currentUser.getPassword();
        }

        User updatedUser;
        try {
            InputStream imageStream = imageField.getBody(InputStream.class, null);
            Image img = new Image(imageStream);
            Optional<Thumbnail> maybeThumb = Thumbnail.create(img);
            if (maybeThumb.isPresent()) {
                updatedUser = new User(username, password, bio, true);
                userAccess.storeProfilePicture(updatedUser, maybeThumb.get());
            } else {
                throw new ImageStoreException.TooSmall();
            }
        } catch (ImageStoreException.NoImage ignored) {
            updatedUser = new User(username, password, bio, currentUser.hasProfilePicture());
        }
        userAccess.updateUser(updatedUser);
        request.getSession().setAttribute("user", updatedUser);

        return Response.seeOther(URI.create("/me")).build();
    }
}
