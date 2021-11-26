package rs.itcentar.achievemvents.dal.repo;

import java.sql.SQLException;
import java.util.List;
import rs.itcentar.achievements.data.Achievement;

/**
 *
 * @author Milos Stojkovic <iqoologic@gmail.com>
 */
public interface AchievementRepository {

    List<Achievement> findAll() throws SQLException, RuntimeException;

    List<Achievement> findAllForGame(String gameId) throws SQLException, RuntimeException;

    Achievement findOne(String id) throws SQLException, RuntimeException;

    boolean exists(String id) throws SQLException, RuntimeException;

    Achievement save(Achievement achievement) throws SQLException, RuntimeException;

    Achievement update(Achievement achievement) throws SQLException, RuntimeException;

    void delete(String id) throws SQLException, RuntimeException;

    int findMaxDisplayOrder(String gameId) throws SQLException, RuntimeException;
}
