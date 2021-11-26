package rs.itcentar.achievements.rest.api.security;

import java.security.Principal;

/**
 *
 * @author Milos Stojkovic <iqoologic@gmail.com>
 */
public class AuthenticatedPrincipal implements Principal {

    private final String name;
    private final String role;

    public AuthenticatedPrincipal(String username, String role) {
        this.name = username;
        this.role = role;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }
}
