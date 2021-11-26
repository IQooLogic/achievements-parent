package rs.itcentar.achievements.rest.api.filter;

import java.io.IOException;
import java.lang.reflect.Method;
import javax.annotation.Priority;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Milos Stojkovic <iqoologic@gmail.com>
 */
@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationFilter implements ContainerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationFilter.class);
    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        Method method = resourceInfo.getResourceMethod();

        // DenyAll
        if (method.isAnnotationPresent(DenyAll.class)) {
            requestContext.abortWith(Response.status(Status.FORBIDDEN).build());
        }

        // RolesAllowed
//        if (method.isAnnotationPresent(RolesAllowed.class)) {
        RolesAllowed rolesAllowed = method.getAnnotation(RolesAllowed.class);
        if (rolesAllowed != null) {
            // provera rola sa logovanim korisnikom
            performAuthorization(rolesAllowed.value(), requestContext);
            return;
        }
//        }

        // PermitAll
        if (method.isAnnotationPresent(PermitAll.class)) {
            return;
        }

        // RolesAllowed class
        rolesAllowed = resourceInfo.getResourceClass().getAnnotation(RolesAllowed.class);
        if (rolesAllowed != null) {
            // provera rola sa logovanim korisnikom
            performAuthorization(rolesAllowed.value(), requestContext);
            return;
        }

        // PermitAll class
        if (resourceInfo.getResourceClass().isAnnotationPresent(PermitAll.class)) {
            return;
        }

        // metode bez anotacija za role
        if (!isAuthneticated(requestContext)) {
            requestContext.abortWith(Response.status(Status.FORBIDDEN).build());
        }
    }

    private boolean isAuthneticated(ContainerRequestContext requestContext) {
        return requestContext.getSecurityContext().getUserPrincipal() != null;
    }

    private void performAuthorization(String[] allowedRoles, ContainerRequestContext requestContext) {
        if (!isAuthneticated(requestContext)) {
            if (allowedRoles.length > 0) {
                LOGGER.error("You don't have sufficient rights to perform this action");
                requestContext.abortWith(Response.status(Status.FORBIDDEN).build());
            }
            LOGGER.error("You don't have permission to perform this action");
            requestContext.abortWith(Response.status(Status.FORBIDDEN).build());
        }

        for (String role : allowedRoles) {
            if (requestContext.getSecurityContext().isUserInRole(role)) {
                return;
            }
        }

        LOGGER.error("You don't have sufficient rights to perform this action");
        requestContext.abortWith(Response.status(Status.FORBIDDEN).build());
    }
}
