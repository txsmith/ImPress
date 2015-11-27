package nl.impress.app.exceptions.mappers;

import com.google.inject.ProvisionException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;


public class ProvisionExceptionMapper implements ExceptionMapper<ProvisionException> {
    @Override
    public Response toResponse(ProvisionException exception) {
        return null;
    }
}
