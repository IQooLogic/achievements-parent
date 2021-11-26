package rs.itcentar.achievements.rest.api.client;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

/**
 *
 * @author Milos Stojkovic <iqoologic@gmail.com>
 */
public class AchievementsApiClient {

    private final AchievementClient achievementClient;
    private final GameClient gameClient;

    public AchievementsApiClient(String baseUrl, String username, String password) {
        HttpAuthenticationFeature basicAuthFeature = HttpAuthenticationFeature.basic(username, password);
        this.achievementClient = new AchievementClientImpl(baseUrl, basicAuthFeature);
        this.gameClient = new GameClientImpl(baseUrl, basicAuthFeature);
    }

    public AchievementClient achievement() {
        return achievementClient;
    }

    public GameClient game() {
        return gameClient;
    }
}
