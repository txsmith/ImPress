package nl.impress.app.exceptions.mappers;

import com.google.inject.Singleton;
import nl.impress.app.exceptions.ImageStoreException;

import javax.ws.rs.ext.Provider;

@Singleton
@Provider
public class ImageNotFoundExceptionMapper extends BaseExceptionMapper<ImageStoreException.NotFound> {
    @Override
    protected Integer getErrorCode(ImageStoreException.NotFound exception) {
        return 404;
    }

    @Override
    protected String getErrorMessage(ImageStoreException.NotFound exception) {
        return exception.getMessage();
    }
}
