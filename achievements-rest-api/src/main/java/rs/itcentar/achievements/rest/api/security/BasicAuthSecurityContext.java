package rs.itcentar.achievements.rest.api.security;

import java.security.Principal;
import javax.ws.rs.core.SecurityContext;

/**
 *
 * @author Milos Stojkovic <iqoologic@gmail.com>
 */
public class BasicAuthSecurityContext implements SecurityContext {

    private final String username;
    private final String role;

    public BasicAuthSecurityContext(String username, String role) {
        this.username = username;
        this.role = role;
    }

    @Override
    public Principal getUserPrincipal() {
        return new AuthenticatedPrincipal(username, role);
    }

    @Override
    public boolean isUserInRole(String role) {
        return this.role.equalsIgnoreCase(role);
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public String getAuthenticationScheme() {
        return BASIC_AUTH;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}
