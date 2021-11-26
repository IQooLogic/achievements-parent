package rs.itcentar.achievements.gui.app.achievement;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import rs.itcentar.achievements.data.Achievement;
import rs.itcentar.achievements.rest.api.client.AchievementsApiClient;

/**
 *
 * @author Milos Stojkovic <iqoologic@gmail.com>
 */
public class AchievementsTableModel extends DefaultTableModel {

    private final AchievementsApiClient client;
    private List<Achievement> achievements;

    public AchievementsTableModel(AchievementsApiClient client) {
        this.client = client;
    }

    @Override
    public Object getValueAt(int row, int column) {
        Achievement achievement = achievements.get(row);
        switch (column) {
            case 0:
                return achievement.getDisplayName();
            case 1:
                return achievement.getDescription();
            case 2:
                return millisToStringDate(achievement.getCreated());
            case 3:
                return millisToStringDate(achievement.getUpdated());
            default:
                throw new IllegalArgumentException("Unknown column");
        }
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "DisplayName";
            case 1:
                return "Description";
            case 2:
                return "Created";
            case 3:
                return "Updated";
            default:
                throw new IllegalArgumentException("Unknown column");
        }
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public int getRowCount() {
        return achievements != null ? achievements.size() : 0;
    }

    @Override
    public Class<?> getColumnClass(int column) {
        switch (column) {
            case 0:
            case 1:
            case 2:
            case 3:
                return String.class;
            default:
                throw new IllegalArgumentException("Unknown column");
        }
    }

    private String millisToStringDate(long millis) {
        if (millis <= 0) {
            return "never";
        }
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault()).toString();
    }

    public void updateComponent(List<Achievement> achievements) {
        if (achievements == null) {
            return;
        }
        this.achievements = achievements;
        fireTableDataChanged();
    }

    public Achievement findOneBySelectedRow(int row) {
        if (achievements == null) {
            return null;
        }
        return achievements.get(row);
    }

    public void refresh(String gameId) {
        FetchAchievementsWorker worker = new FetchAchievementsWorker(gameId);
        worker.execute();
    }

    public void create(CreateAchievementDTO dto) {
        CreateAchievementWorker worker = new CreateAchievementWorker(dto);
        worker.execute();
    }

    public void delete(String id, String gameId) {
        DeleteAchievementWorker worker = new DeleteAchievementWorker(id, gameId);
        worker.execute();
    }

    public void update(String id, CreateAchievementDTO dto) {
        UpdateAchievementWorker worker = new UpdateAchievementWorker(id, dto);
        worker.execute();
    }

    private class FetchAchievementsWorker extends SwingWorker<List<Achievement>, Void> {

        private final String gameId;

        public FetchAchievementsWorker(String gameId) {
            this.gameId = gameId;
        }

        @Override
        protected List<Achievement> doInBackground() throws Exception {
            return client.achievement().findAllByGameId(gameId);
        }

        @Override
        protected void done() {
            try {
                List<Achievement> achievements = get(10, TimeUnit.SECONDS);
                updateComponent(achievements);
            } catch (InterruptedException | ExecutionException | TimeoutException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error fetching achievements", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class CreateAchievementWorker extends SwingWorker<Achievement, Void> {

        private final CreateAchievementDTO dto;

        public CreateAchievementWorker(CreateAchievementDTO dto) {
            this.dto = dto;
        }

        @Override
        protected Achievement doInBackground() throws Exception {
            return client.achievement().create(dto.getDisplayName(), dto.getDescription(),
                    dto.getIcon(), dto.getGameId());
//            return client.achievement().create(dto);
        }

        @Override
        protected void done() {
            try {
                Achievement achievement = get(10, TimeUnit.SECONDS);
                refresh(achievement.getGameId());
            } catch (InterruptedException | ExecutionException | TimeoutException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error creating achievement", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class DeleteAchievementWorker extends SwingWorker<Boolean, Void> {

        private final String id;
        private final String gameId;

        public DeleteAchievementWorker(String id, String gameId) {
            this.id = id;
            this.gameId = gameId;
        }

        @Override
        protected Boolean doInBackground() throws Exception {
            return client.achievement().delete(id);
        }

        @Override
        protected void done() {
            try {
                get(10, TimeUnit.SECONDS);
                refresh(gameId);
            } catch (InterruptedException | ExecutionException | TimeoutException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error deleting achievement", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class UpdateAchievementWorker extends SwingWorker<Achievement, Void> {

        private final String id;
        private final CreateAchievementDTO dto;

        public UpdateAchievementWorker(String id, CreateAchievementDTO dto) {
            this.id = id;
            this.dto = dto;
        }

        @Override
        protected Achievement doInBackground() throws Exception {
//            client.achievement().update(id, dto);
            return client.achievement().update(id, dto.getDisplayName(), dto.getDescription(),
                    dto.getIcon(), dto.getGameId());
        }

        @Override
        protected void done() {
            try {
                get(10, TimeUnit.SECONDS);
                refresh(dto.getGameId());
            } catch (InterruptedException | ExecutionException | TimeoutException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error updating achievement", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
