package nl.impress.app.exceptions.mappers;

import com.google.inject.Singleton;
import nl.impress.app.exceptions.UserStoreException;

import javax.ws.rs.ext.Provider;

@Singleton
@Provider
public class UserNotFoundExceptionMapper extends BaseExceptionMapper<UserStoreException.NotFound> {
    @Override
    protected Integer getErrorCode(UserStoreException.NotFound exception) {
        return 404;
    }

    @Override
    protected String getErrorMessage(UserStoreException.NotFound exception) {
        return exception.getMessage();
    }
}
