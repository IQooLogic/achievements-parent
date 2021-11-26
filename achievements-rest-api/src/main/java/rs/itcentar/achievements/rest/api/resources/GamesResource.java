package rs.itcentar.achievements.rest.api.resources;

import java.sql.SQLException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import rs.itcentar.achievements.data.Game;
import rs.itcentar.achievements.data.GameTransfer;
import rs.itcentar.achievements.rest.api.di.DI;
import rs.itcentar.achievemvents.dal.repo.GameRepository;

/**
 *
 * @author Milos Stojkovic <iqoologic@gmail.com>
 */
@Path("game")
public class GamesResource {

    private static GameRepository repo = DI.getGameRepo();// implement some kind of DI and connection

    // http://localhost:8080/achievements-rest-api/v1/game/*
    @Path("/hello")
    @GET
    public Response sayHello() throws SQLException {
        return Response.ok("Hello from Games endpoint!").build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() throws SQLException {
        List<Game> games = repo.findAll();
        return Response.ok(games).build();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response one(@PathParam("id") String id) throws SQLException {
//        if (repo.exists(id)) {
//            Game game = repo.findOne(id);
//            return Response.ok(game).build();
//        }

        Game game = repo.findOne(id);
        if (game != null) {
            return Response.ok(game).build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(GameTransfer gt) throws SQLException {
        Game game = repo.save(gt.getDisplayName());
        return Response.ok(game).build();
    }

    @Path("/form")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@FormParam("displayName") String displayName) throws SQLException {
        Game game = repo.save(displayName);
        return Response.ok(game).build();
    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") String id, GameTransfer gt) throws SQLException {
        Game game = repo.update(id, gt.getDisplayName());
        if (game != null) {
            return Response.status(Status.CREATED).entity(game).build();
        }

        return Response.status(Status.NOT_FOUND).build();
    }

    @Path("/form/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") String id,
            @FormParam("displayName") String displayName) throws SQLException {
        Game game = repo.update(id, displayName);
        if (game != null) {
            return Response.status(Status.CREATED).entity(game).build();
        }

        return Response.status(Status.NOT_FOUND).build();
    }

    @Path("/{id}")
    @DELETE
    public Response delete(@PathParam("id") String id) throws SQLException {
        boolean exists = repo.exists(id);
        if (exists) {
            repo.delete(id);
            exists = repo.exists(id);
            System.out.println(exists);
            if (!exists) {
                return Response.ok().build();
            }
        }
        return Response.notModified().build();
    }
}
