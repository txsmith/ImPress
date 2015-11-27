package nl.impress.app.exceptions.mappers;

import com.google.inject.Singleton;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;

@Singleton
@Provider
public class HTTPExceptionMapper extends BaseExceptionMapper<WebApplicationException> {
    @Override
    protected Integer getErrorCode(WebApplicationException exception) {
        return exception.getResponse().getStatus();
    }

    @Override
    protected String getErrorMessage(WebApplicationException exception) {
        return exception.getResponse().getStatusInfo().getReasonPhrase();
    }
}
