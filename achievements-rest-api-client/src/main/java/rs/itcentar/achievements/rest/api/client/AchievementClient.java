package rs.itcentar.achievements.rest.api.client;

import java.util.List;
import rs.itcentar.achievements.data.Achievement;

/**
 *
 * @author Milos Stojkovic <iqoologic@gmail.com>
 */
public interface AchievementClient {

    List<Achievement> list();

    List<Achievement> findAllByGameId(String gameId);

    Achievement findOne(String id);

    Achievement create(String displayName, String description, String icon, String gameId);

    Achievement update(String id, String displayName, String description, String icon, String gameId);

    boolean delete(String id);
}
