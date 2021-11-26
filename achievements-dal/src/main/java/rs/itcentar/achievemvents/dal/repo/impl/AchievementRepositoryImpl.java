package rs.itcentar.achievemvents.dal.repo.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import rs.itcentar.achievements.data.Achievement;
import rs.itcentar.achievemvents.dal.repo.AchievementRepository;

/**
 *
 * @author Milos Stojkovic <iqoologic@gmail.com>
 */
public class AchievementRepositoryImpl implements AchievementRepository {

    private final Connection connection;

    public AchievementRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Achievement> findAll() throws SQLException, RuntimeException {
        if (connection == null) {
            throw new RuntimeException("Not connected to database server. Invoke connect() method first.");
        }
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM achievement")) {
            ResultSet rs = ps.executeQuery();

            List<Achievement> achievements = new ArrayList<>();
            while (rs.next()) {
                String id = rs.getString(Achievement.COL_ID);
                String displayName = rs.getString(Achievement.COL_DISPLAY_NAME);
                String description = rs.getString(Achievement.COL_DESCRIPTION);
                String icon = rs.getString(Achievement.COL_ICON);
                int displayOrder = rs.getInt(Achievement.COL_DISPLAY_ORDER);
                long created = rs.getLong(Achievement.COL_CREATED);
                long updated = rs.getLong(Achievement.COL_UPDATED);
                String gameId = rs.getString(Achievement.COL_GAME_ID);

                Achievement achievement = new Achievement(id, displayName, description, icon,
                        displayOrder, created, updated, gameId);
                achievements.add(achievement);
            }

            return achievements;
        }
    }

    @Override
    public List<Achievement> findAllForGame(String gameId) throws SQLException, RuntimeException {
        if (connection == null) {
            throw new RuntimeException("Not connected to database server. Invoke connect() method first.");
        }

        List<Achievement> achievements = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM achievement WHERE game_id = ?")) {
            ps.setString(1, gameId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String id = rs.getString(Achievement.COL_ID);
                String displayName = rs.getString(Achievement.COL_DISPLAY_NAME);
                String description = rs.getString(Achievement.COL_DESCRIPTION);
                String icon = rs.getString(Achievement.COL_ICON);
                int displayOrder = rs.getInt(Achievement.COL_DISPLAY_ORDER);
                long created = rs.getLong(Achievement.COL_CREATED);
                long updated = rs.getLong(Achievement.COL_UPDATED);
//            String gameId = rs.getString(Achievement.COL_GAME_ID);

                Achievement achievement = new Achievement(id, displayName, description, icon,
                        displayOrder, created, updated, gameId);
                achievements.add(achievement);
            }

            return achievements;
        }
    }

    @Override
    public Achievement findOne(String id) throws SQLException, RuntimeException {
        if (connection == null) {
            throw new RuntimeException("Not connected to database server. Invoke connect() method first.");
        }
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM achievement WHERE id = ?")) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                //  String id = rs.getString(Achievement.COL_ID);
                String displayName = rs.getString(Achievement.COL_DISPLAY_NAME);
                String description = rs.getString(Achievement.COL_DESCRIPTION);
                String icon = rs.getString(Achievement.COL_ICON);
                int displayOrder = rs.getInt(Achievement.COL_DISPLAY_ORDER);
                long created = rs.getLong(Achievement.COL_CREATED);
                long updated = rs.getLong(Achievement.COL_UPDATED);
                String gameId = rs.getString(Achievement.COL_GAME_ID);

                return new Achievement(id, displayName, description, icon,
                        displayOrder, created, updated, gameId);
            }
        }

        return null;
    }

    @Override
    public boolean exists(String id) throws SQLException, RuntimeException {
        if (connection == null) {
            throw new RuntimeException("Not connected to database server. Invoke connect() method first.");
        }

        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM achievement WHERE id = ?")) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    @Override
    public Achievement save(Achievement achievement) throws SQLException, RuntimeException {
        if (connection == null) {
            throw new RuntimeException("Not connected to database server. Invoke connect() method first.");
        }

        int maxDisplayOrder = findMaxDisplayOrder(achievement.getGameId());
        if (maxDisplayOrder == -1) {
            throw new RuntimeException("Max display order not found! Maybe game doesn't exist!");
        }

        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO achievement (id, displayName, description, icon, displayOrder, created, updated, game_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
            final String id = UUID.randomUUID().toString();
            ps.setString(1, id);
            ps.setString(2, achievement.getDisplayName());
            ps.setString(3, achievement.getDescription());
            ps.setString(4, achievement.getIcon());
            ps.setInt(5, maxDisplayOrder + 1);
            ps.setLong(6, achievement.getCreated());
            ps.setLong(7, achievement.getUpdated());
            ps.setString(8, achievement.getGameId());
            ps.executeUpdate();

            return findOne(id);
        }
    }

    @Override
    public Achievement update(Achievement achievement) throws SQLException, RuntimeException {
        if (connection == null) {
            throw new RuntimeException("Not connected to database server. Invoke connect() method first.");
        }

        try (PreparedStatement ps = connection.prepareStatement("UPDATE achievement SET displayName = ?, description = ?, icon = ?, displayOrder = ?, updated = ?, game_id = ? WHERE id = ?")) {
            ps.setString(1, achievement.getDisplayName());
            ps.setString(2, achievement.getDescription());
            ps.setString(3, achievement.getIcon());
            ps.setInt(4, achievement.getDisplayOrder());
            ps.setLong(5, System.currentTimeMillis());
            ps.setString(6, achievement.getGameId());
            ps.setString(7, achievement.getId());
            ps.executeUpdate();

            return findOne(achievement.getId());
        }
    }

    @Override
    public void delete(String id) throws SQLException, RuntimeException {
        if (connection == null) {
            throw new RuntimeException("Not connected to database server. Invoke connect() method first.");
        }

        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM achievement WHERE id = ?")) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public int findMaxDisplayOrder(String gameId) throws SQLException, RuntimeException {
        try (PreparedStatement ps = connection.prepareStatement("SELECT MAX(displayOrder) AS max_order FROM achievement WHERE game_id = ?")) {
            ps.setString(1, gameId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("max_order");
            }
        }
        return -1;
    }
}
