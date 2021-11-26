package rs.itcentar.achievemvents.dal.repo.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import rs.itcentar.achievements.data.Game;
import rs.itcentar.achievemvents.dal.repo.GameRepository;

/**
 *
 * @author Milos Stojkovic <iqoologic@gmail.com>
 */
public class GameRepositoryImpl implements GameRepository {

    private final Connection connection;

    public GameRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Game> findAll() throws SQLException, RuntimeException {
        if (connection == null) {
            throw new RuntimeException("Not connected to database server. Invoke connect() method first.");
        }

        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM game")) {
            ResultSet rs = ps.executeQuery();

            List<Game> games = new ArrayList<>();
            while (rs.next()) {
                String id = rs.getString(Game.COL_ID);
                String displayName = rs.getString(Game.COL_DISPLAY_NAME);

                Game game = new Game(id, displayName);
                games.add(game);
            }

            return games;
        }
    }

    @Override
    public Game findOne(String id) throws SQLException, RuntimeException {
        if (connection == null) {
            throw new RuntimeException("Not connected to database server. Invoke connect() method first.");
        }

        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM game WHERE id = ?")) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
//            String id = rs.getString(Game.COL_ID);
                String displayName = rs.getString(Game.COL_DISPLAY_NAME);

                return new Game(id, displayName);
            }
        }

        return null;
    }

    @Override
    public boolean exists(String id) throws SQLException, RuntimeException {
        if (connection == null) {
            throw new RuntimeException("Not connected to database server. Invoke connect() method first.");
        }

        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM game WHERE id = ?")) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    @Override
    public Game save(String dislayName) throws SQLException, RuntimeException {
        if (connection == null) {
            throw new RuntimeException("Not connected to database server. Invoke connect() method first.");
        }

        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO game (id, displayName) VALUES (?, ?)")) {
            final String id = UUID.randomUUID().toString();
            ps.setString(1, id);
            ps.setString(2, dislayName);
            ps.executeUpdate();

            return findOne(id);
        }
    }

    @Override
    public Game update(String id, String displayName) throws SQLException, RuntimeException {
        if (connection == null) {
            throw new RuntimeException("Not connected to database server. Invoke connect() method first.");
        }

        try (PreparedStatement ps = connection.prepareStatement("UPDATE game SET displayName = ? WHERE id = ?")) {
            ps.setString(1, displayName);
            ps.setString(2, id);
            ps.executeUpdate();

            return findOne(id);
        }
    }

    @Override
    public void delete(String id) throws SQLException, RuntimeException {
        if (connection == null) {
            throw new RuntimeException("Not connected to database server. Invoke connect() method first.");
        }

        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM game WHERE id = ?")) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }
}
