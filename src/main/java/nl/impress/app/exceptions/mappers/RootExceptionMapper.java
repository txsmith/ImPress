package nl.impress.app.exceptions.mappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class RootExceptionMapper extends BaseExceptionMapper<Exception> {
    @Override
    public Integer getErrorCode(Exception e) {
        return 500;
    }

    @Override
    public Response toResponse(Exception exception) {
        log.error("Uncaught exception:", exception);
        return super.toResponse(exception);
    }

    @Override
    public String getErrorMessage(Exception e) {
        return e.getMessage();
    }
}
