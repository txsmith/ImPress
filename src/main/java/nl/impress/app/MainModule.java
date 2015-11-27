package nl.impress.app;

import com.google.inject.servlet.RequestScoped;
import com.google.inject.servlet.ServletModule;
import com.google.inject.throwingproviders.CheckedProvides;
import com.google.inject.throwingproviders.ThrowingProviderBinder;
import nl.impress.app.data.ImageStorage;
import nl.impress.app.data.InMemoryImageStorage;
import nl.impress.app.data.InMemoryUserStorage;
import nl.impress.app.data.User;
import nl.impress.app.data.UserStorage;
import nl.impress.app.guice.CurrentUser;
import nl.impress.app.guice.UserProvider;
import org.jboss.resteasy.plugins.guice.ext.JaxrsModule;
import org.reflections.Reflections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;

public class MainModule extends ServletModule {

    @Override
    @SuppressWarnings("unchecked")
    public void configureServlets() {
        install(new JaxrsModule());
        install(ThrowingProviderBinder.forModule(this));

        bindResourcesWith(Path.class, Provider.class);

        bind(UserStorage.class).to(InMemoryUserStorage.class);
        bind(ImageStorage.class).to(InMemoryImageStorage.class);

        filter("/", "/me", "/me/*", "/user/*", "/upload", "/image/remove/*").through(AuthorizationFilter.class);
    }

    /**
     * Find all classes annotated with {@code annotations},
     * and bind these resources so they can be served.
     * @param annotations The {@link Annotation} classes to search for.
     */
    private void bindResourcesWith(Class<? extends Annotation>... annotations) {
        Reflections reflections = new Reflections(getClass().getPackage().getName());

        for (Class<? extends Annotation> annotation : annotations) {
            for (Class<?> type : reflections.getTypesAnnotatedWith(annotation)) {
                this.bind(type);
            }
        }
    }

    @CheckedProvides(UserProvider.class)
    @RequestScoped
    @CurrentUser
    public User provideUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return (User) session.getAttribute("user");
        }
        throw new NotAuthorizedException("");
    }
}
