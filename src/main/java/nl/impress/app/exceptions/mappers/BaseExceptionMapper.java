package nl.impress.app.exceptions.mappers;

import com.google.inject.Inject;
import freemarker.template.TemplateException;
import nl.impress.app.FreeMarkerUtil;
import nl.impress.app.guice.CurrentUser;
import nl.impress.app.guice.UserProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;


public abstract class BaseExceptionMapper<E extends Throwable> implements ExceptionMapper<E> {

    protected static final Logger log = LogManager.getLogger();

    @Inject
    protected FreeMarkerUtil freeMarkerUtil;

    @Inject
    @CurrentUser
    protected UserProvider userProvider;

    @Override
    public Response toResponse(E exception) {
        log.error(exception);
        String errorPage;
        try {
            Map<String, Object> model = new HashMap<>();
            Integer errorCode = this.getErrorCode(exception);
            model.put("errorCode", errorCode);
            model.put("errorMessage", this.getErrorMessage(exception));
            try {
                model.put("user", userProvider.get());
            } catch (NotAuthorizedException ignored) { }

            errorPage = freeMarkerUtil.getTemplateHTML("error.ftl", model);

            return Response.status(errorCode)
                    .entity(errorPage)
                    .type(MediaType.TEXT_HTML_TYPE).build();
        } catch (IOException | TemplateException e) {
            log.error(e);
            return Response.seeOther(URI.create("/static/error.html")).build();
        }
    }

    protected abstract Integer getErrorCode(E exception);
    protected abstract String getErrorMessage(E exception);
}
