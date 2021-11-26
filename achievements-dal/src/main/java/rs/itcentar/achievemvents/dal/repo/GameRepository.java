package rs.itcentar.achievemvents.dal.repo;

import java.sql.SQLException;
import java.util.List;
import rs.itcentar.achievements.data.Game;

/**
 *
 * @author Milos Stojkovic <iqoologic@gmail.com>
 */
public interface GameRepository {

    List<Game> findAll() throws SQLException, RuntimeException;

    Game findOne(String id) throws SQLException, RuntimeException;

    boolean exists(String id) throws SQLException, RuntimeException;

    Game save(String displayName) throws SQLException, RuntimeException;

    Game update(String id, String displayName) throws SQLException, RuntimeException;

    void delete(String id) throws SQLException, RuntimeException;
}
