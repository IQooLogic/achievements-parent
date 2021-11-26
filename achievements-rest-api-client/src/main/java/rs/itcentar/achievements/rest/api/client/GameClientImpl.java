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
import rs.itcentar.achievements.data.Game;

/**
 *
 * @author Milos Stojkovic <iqoologic@gmail.com>
 */
public class GameClientImpl implements GameClient {

    private final WebTarget client;

    public GameClientImpl(String baseUrl, HttpAuthenticationFeature basicAuthFeature) {
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
    public List<Game> list() {
        Response response = client.path("game")
                .request(MediaType.APPLICATION_JSON)
                .get();
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            return response.readEntity(new GenericType<List<Game>>() {
            });
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public Game one(String id) {
        Response response = client.path("game").path(id)
                .request(MediaType.APPLICATION_JSON)
                .get();
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            return response.readEntity(Game.class);
        }
        return null;
    }

    @Override
    public Game create(String displayName) {
        Form form = new Form();
        form.param("displayName", displayName);
        Response response = client.path("game").path("form")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.form(form));
        if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
            return response.readEntity(Game.class);
        }
        return null;
    }

    @Override
    public Game update(String id, String newDisplayName) {
        Form form = new Form();
        form.param("displayName", newDisplayName);
        Response response = client.path("game").path("form").path(id)
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.form(form));
        if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
            return response.readEntity(Game.class);
        }
        return null;
    }

    @Override
    public boolean delete(String id) {
        Response response = client.path("game").path(id)
                .request(MediaType.APPLICATION_JSON)
                .delete();
        return response.getStatus() == Response.Status.OK.getStatusCode();
    }
}
