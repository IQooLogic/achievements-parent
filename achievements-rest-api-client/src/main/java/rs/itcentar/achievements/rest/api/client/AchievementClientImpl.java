package rs.itcentar.achievements.rest.api.client;

import java.util.Collections;
import java.util.List;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.client.filter.EncodingFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.message.GZipEncoder;
import rs.itcentar.achievements.data.Achievement;

/**
 *
 * @author Milos Stojkovic <iqoologic@gmail.com>
 */
public class AchievementClientImpl implements AchievementClient {

    private final WebTarget client;

    public AchievementClientImpl(String baseUrl, HttpAuthenticationFeature basicAuthFeature) {
        ClientConfig clientConfig = new ClientConfig()
                .property(ClientProperties.READ_TIMEOUT, 60_000)
                .property(ClientProperties.CONNECT_TIMEOUT, 10_000)
                .register(GZipEncoder.class)
                .register(EncodingFilter.class);
        client = ClientBuilder.newClient(clientConfig)
                .register(JacksonJsonProvider.class)
                .register(JacksonFeature.class)
                .register(MultiPartFeature.class)
                .register(basicAuthFeature)
                .target(baseUrl);
    }

    @Override
    public List<Achievement> list() {
//        String basicAuthHeader = "Basic " + Base64.getEncoder().encodeToString("milos:changeme".getBytes(StandardCharsets.UTF_8));
        Response response = client.path("achievement")
                .request(MediaType.APPLICATION_JSON)
                //                .header("Authorization", basicAuthHeader)
                .get();
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            return response.readEntity(new GenericType<List<Achievement>>() {
            });
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Achievement> findAllByGameId(String gameId) {
        Response response = client.path("achievement").path("game").path(gameId)
                .request(MediaType.APPLICATION_JSON)
                .get();
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            return response.readEntity(new GenericType<List<Achievement>>() {
            });
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public Achievement findOne(String id) {
        Response response = client.path("achievement").path(id)
                .request(MediaType.APPLICATION_JSON)
                .get();
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            return response.readEntity(Achievement.class);
        }
        return null;
    }

    @Override
    public Achievement create(String displayName, String description, String icon, String gameId) {
        Form form = new Form();
        form.param("displayName", displayName);
        form.param("description", description);
        form.param("icon", icon);
        form.param("gameId", gameId);
        Response response = client.path("achievement")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.form(form));
        if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
            return response.readEntity(Achievement.class);
        }
        return null;
    }

    @Override
    public Achievement update(String id, String displayName, String description, String icon, String gameId) {
        Form form = new Form();
        form.param("displayName", displayName);
        form.param("description", description);
        form.param("icon", icon);
        form.param("gameId", gameId);
        Response response = client.path("achievement").path(id)
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.form(form));
        if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
            return response.readEntity(Achievement.class);
        }
        return null;
    }

    @Override
    public boolean delete(String id) {
        Response response = client.path("achievement").path(id)
                .request(MediaType.APPLICATION_JSON)
                .delete();
        return response.getStatus() == Response.Status.OK.getStatusCode();
    }
}
