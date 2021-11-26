package rs.itcentar.achievements.rest.api.resources;

import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rs.itcentar.achievements.data.Achievement;
import rs.itcentar.achievements.data.ListAchievementsResponse;
import rs.itcentar.achievements.rest.api.di.DI;
import rs.itcentar.achievemvents.dal.repo.AchievementRepository;

/**
 *
 * @author Milos Stojkovic <iqoologic@gmail.com>
 */
@Path("achievement")
public class AchievementsResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(AchievementsResource.class);
    private static final AchievementRepository repo = DI.getAchievementRepo();

    // http://localhost:8080/achievements-rest-api/v1/achievement/*
    @RolesAllowed({"USER", "ADMIN"})
    @Path("/hello")
    @GET
    public Response sayHello(@Context SecurityContext sc) throws SQLException {
//        String username = sc.getUserPrincipal().getName();
//        return Response.ok(String.format("Hello '%s' from Achievements endpoint!", username)).build();
        return Response.ok("Hello from Achievements endpoint!").build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() throws SQLException {
        LOGGER.info("List endpoint call");
        List<Achievement> achievements = repo.findAll();
        return Response.ok(achievements)
                .header("X-TotalCount", achievements.size())
                .header("X-TotalPages", achievements.size())
                .header("X-CurrentPage", achievements.size())
                .header("X-PageSize", achievements.size())
                .build();
    }

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list2() {
        try {
            List<Achievement> achievements = repo.findAll();
            return Response.ok(new ListAchievementsResponse(achievements, achievements.size())).build();
        } catch (Exception ex) {
            LOGGER.error("Error getting achievements", ex);
            return Response.serverError().entity(ex.getMessage()).build();
        }
    }

    @Path("/game/{gameId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllByGameId(@PathParam("gameId") String gameId) throws SQLException {
        LOGGER.info("findAllByGameId for '{}'", gameId);
        List<Achievement> achievements = repo.findAllForGame(gameId);
        return Response.ok(achievements).build();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findOne(@PathParam("id") String id) throws SQLException {
        Achievement achievement = repo.findOne(id);
        return Response.ok(achievement).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@FormParam("displayName") String displayName,
            @FormParam("description") String description,
            @FormParam("icon") String icon,
            @FormParam("gameId") String gameId) throws SQLException {
        Achievement achievement = new Achievement(UUID.randomUUID().toString(),
                displayName, description, icon,
                ZonedDateTime.now(ZoneId.of("UTC")).toInstant().toEpochMilli(), -1, gameId);
        achievement = repo.save(achievement);
        if (achievement != null) {
            return Response.status(Response.Status.CREATED).entity(achievement).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@NotNull @PathParam("id") String id,
            @FormParam("displayName") String displayName,
            @FormParam("description") String description,
            @FormParam("icon") String icon,
            @FormParam("gameId") String gameId) throws SQLException {
        Achievement achievement = repo.findOne(id);
        achievement.setDisplayName(displayName);
        achievement.setDescription(description);
        achievement.setIcon(icon);
        achievement.setGameId(gameId);
        achievement.setUpdated(ZonedDateTime.now(ZoneId.of("UTC")).toInstant().toEpochMilli());

        achievement = repo.update(achievement);

        if (achievement != null) {
            return Response.status(Response.Status.CREATED).entity(achievement).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@NotNull @PathParam("id") String id) throws SQLException {
        boolean exists = repo.exists(id);
        if (exists) {
            repo.delete(id);
            exists = repo.exists(id);
            if (!exists) {
                return Response.ok().build();
            }
        }
        return Response.notModified().build();
    }
}
