package rs.itcentar.achievements.rest.api.filter;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rs.itcentar.achievements.rest.api.security.BasicAuthSecurityContext;

/**
 *
 * @author Milos Stojkovic <iqoologic@gmail.com>
 */
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);
    private static final String BASIC_AUTH = "Basic";
    private final Map<String, String> users = new HashMap<>();// username, password

    public AuthenticationFilter() {
        users.put("milos", "changeme");
        users.put("marko", "p@ssword1");
        users.put("svetlana", "samo_ventilatori");
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        System.out.println(authorizationHeader);
        if (authorizationHeader != null) {
            if (authorizationHeader.startsWith(BASIC_AUTH)) {
                String base64UsernameAndPassword = authorizationHeader.substring(BASIC_AUTH.length() + 1);
                LOGGER.info("Encoded username and password: {}", base64UsernameAndPassword);
                byte[] decodedUsernameAndPassword = Base64.getDecoder().decode(base64UsernameAndPassword);
                String usernameAndPassword = new String(decodedUsernameAndPassword);
                LOGGER.info("Decoded username and pasword: {}", usernameAndPassword);
                String[] split = usernameAndPassword.split(":");
                if (split.length == 2) {
                    String username = split[0];
                    String password = split[1];

                    String dbPassword = users.getOrDefault(username, "");
                    if (dbPassword.equals(password)) {
                        // success
                        LOGGER.info("GREAT SUCCESS !");
                        requestContext.setSecurityContext(new BasicAuthSecurityContext(username, "USER"));// ovo bi trebalo da se izbuce iz User objekta koji stoji u bazi
                    } else {
                        // failure
                        LOGGER.warn("NOT AUTHORIZED");
                    }
                }
            } else {
                System.out.println("NOT BASIC AUTH");
            }
        }
    }

}
