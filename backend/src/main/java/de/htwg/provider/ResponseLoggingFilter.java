package de.htwg.provider;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class ResponseLoggingFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
            throws IOException {
        String method = requestContext.getMethod();
        String uri = requestContext.getUriInfo().getRequestUri().toString();
        int status = responseContext.getStatus();
        // Object entity = responseContext.getEntity();

        System.out.println("Response for " + method + " request to URI: " + uri);
        System.out.println("Status: " + status);
        /*
         * if (entity != null) {
         * System.out.println("Response entity: " + entity.toString());
         * }
         */
    }
}
