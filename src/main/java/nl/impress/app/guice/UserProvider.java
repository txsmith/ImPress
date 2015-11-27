package nl.impress.app.guice;

import com.google.inject.throwingproviders.CheckedProvider;
import nl.impress.app.data.User;

import javax.ws.rs.NotAuthorizedException;

public interface UserProvider extends CheckedProvider<User> {
    User get() throws NotAuthorizedException;
}
