package nl.impress.app;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import nl.impress.app.guice.CurrentUser;
import nl.impress.app.guice.UserProvider;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.NotAuthorizedException;
import java.io.IOException;

@Singleton
public class AuthorizationFilter implements Filter {

    @Inject
    @CurrentUser
    private UserProvider userProvider;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        try {
            userProvider.get();
            chain.doFilter(request, response);
        } catch (NotAuthorizedException e) {
            ((HttpServletResponse) response).sendRedirect("/login");
        }
    }

    @Override
    public void destroy() { }
}
