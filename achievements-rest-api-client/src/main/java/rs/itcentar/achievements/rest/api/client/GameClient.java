package rs.itcentar.achievements.rest.api.client;

import java.util.List;
import rs.itcentar.achievements.data.Game;

/**
 *
 * @author Milos Stojkovic <iqoologic@gmail.com>
 */
public interface GameClient {

    List<Game> list();

    Game one(String id);

    Game create(String displayName);

    Game update(String id, String newDisplayName);

    boolean delete(String id);
}
