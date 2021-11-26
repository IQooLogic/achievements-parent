package rs.itcentar.achievements.rest.api.client;

import java.util.List;
import rs.itcentar.achievements.data.Achievement;
import rs.itcentar.achievements.data.Game;

/**
 *
 * @author Milos Stojkovic <iqoologic@gmail.com>
 */
public class ClientApp {

    public static void main(String[] args) {
        String baseUrl = "http://localhost:8080/achievements-rest-api/v1/";
        AchievementsApiClient client = new AchievementsApiClient(baseUrl, "milos", "changeme");

        System.out.println("GAMES");
        List<Game> games = client.game().list();
        System.out.println(games);

        System.out.println("ACHIEVEMENTS");
        List<Achievement> achievements = client.achievement().list();
        System.out.println(achievements);

        System.out.println("ACHIEVEMENT FOR GAME");
        List<Achievement> achievementsForGame = client.achievement().findAllByGameId("1f87b817-86e7-45bf-904f-31c9fc8b6ece");
        System.out.println(achievementsForGame);
    }

}
