package rs.itcentar.achievements.gui.app.game;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import rs.itcentar.achievements.data.Game;
import rs.itcentar.achievements.rest.api.client.AchievementsApiClient;

/**
 *
 * @author Milos Stojkovic <iqoologic@gmail.com>
 */
public class GamesListModel extends DefaultComboBoxModel<Game> {

    private final AchievementsApiClient client;
    private List<Game> games;
    private Game selectedGame;

    public GamesListModel(AchievementsApiClient client) {
        this.client = client;
    }

    @Override
    public Game getElementAt(int index) {
        return games.get(index);
    }

    @Override
    public int getSize() {
        return games != null ? games.size() : 0;
    }

    @Override
    public Object getSelectedItem() {
        return selectedGame;
    }

    @Override
    public void setSelectedItem(Object objGame) {
        selectedGame = (Game) objGame;
    }

    public void updateComponent(List<Game> games) {
        if (games == null) {
            return;
        }
        this.games = games;
        fireContentsChanged(this, 0, games.size());
    }

    public void refresh() {
        FetchGamesWorker worker = new FetchGamesWorker();
        worker.execute();
    }

    public void create(String displayName) {
        CreateGameWorker worker = new CreateGameWorker(displayName);
        worker.execute();
    }

    public void delete(String id) {
        DeleteGameWorker worker = new DeleteGameWorker(id);
        worker.execute();
    }

    public void update(String id, String displayName) {
        UpdateGameWorker worker = new UpdateGameWorker(id, displayName);
        worker.execute();
    }

    private class FetchGamesWorker extends SwingWorker<List<Game>, Void> {

        @Override
        protected List<Game> doInBackground() throws Exception {
            return client.game().list();
        }

        @Override
        protected void done() {
            try {
                List<Game> games = get(10, TimeUnit.SECONDS);
                updateComponent(games);
            } catch (InterruptedException | ExecutionException | TimeoutException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error fetching games", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class CreateGameWorker extends SwingWorker<Game, Void> {

        private final String displayName;

        public CreateGameWorker(String displayName) {
            this.displayName = displayName;
        }

        @Override
        protected Game doInBackground() throws Exception {
            return client.game().create(displayName);
        }

        @Override
        protected void done() {
            try {
                get(10, TimeUnit.SECONDS);
                refresh();
            } catch (InterruptedException | ExecutionException | TimeoutException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error creating game", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class DeleteGameWorker extends SwingWorker<Boolean, Void> {

        private final String id;

        public DeleteGameWorker(String id) {
            this.id = id;
        }

        @Override
        protected Boolean doInBackground() throws Exception {
            return client.game().delete(id);
        }

        @Override
        protected void done() {
            try {
                get(10, TimeUnit.SECONDS);
                refresh();
            } catch (InterruptedException | ExecutionException | TimeoutException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error deleting game", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class UpdateGameWorker extends SwingWorker<Game, Void> {

        private final String id;
        private final String displayName;

        public UpdateGameWorker(String id, String displayName) {
            this.id = id;
            this.displayName = displayName;
        }

        @Override
        protected Game doInBackground() throws Exception {
            return client.game().update(id, displayName);
        }

        @Override
        protected void done() {
            try {
                get(10, TimeUnit.SECONDS);
                refresh();
            } catch (InterruptedException | ExecutionException | TimeoutException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error updating game", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
