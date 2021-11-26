package rs.itcentar.achievements.gui.app;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import rs.itcentar.achievements.data.Achievement;
import rs.itcentar.achievements.data.Game;
import rs.itcentar.achievements.gui.app.achievement.AchievementsTableModel;
import rs.itcentar.achievements.gui.app.achievement.CreateAchievementPanel;
import rs.itcentar.achievements.gui.app.game.CreateGamePanel;
import rs.itcentar.achievements.gui.app.game.GamesListCellRenderer;
import rs.itcentar.achievements.gui.app.game.GamesListModel;
import rs.itcentar.achievements.rest.api.client.AchievementsApiClient;

/**
 *
 * @author Milos Stojkovic <iqoologic@gmail.com>
 */
public class MainForm extends javax.swing.JFrame {

    private final AchievementsApiClient client = new AchievementsApiClient("http://localhost:8080/achievements-rest-api/v1/", "milos", "changeme");
    private final GamesListModel gamesModel = new GamesListModel(client);
    private final AchievementsTableModel achievementsModel = new AchievementsTableModel(client);

    public MainForm() {
        initComponents();

        lGames.setModel(gamesModel);
        lGames.setCellRenderer(new GamesListCellRenderer());
        lGames.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    Game game = lGames.getSelectedValue();
                    if (game != null) {
                        achievementsModel.refresh(game.getId());
                    }
                }
            }
        });
        gamesModel.refresh();

        tAchievements.setModel(achievementsModel);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lGames = new javax.swing.JList<>();
        bCreateGame = new javax.swing.JButton();
        bDeleteGame = new javax.swing.JButton();
        bUpdateGame = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tAchievements = new javax.swing.JTable();
        bCreateAchievement = new javax.swing.JButton();
        bDeleteAchievement = new javax.swing.JButton();
        bUpdateAchievement = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Achievements REST Api App");

        jSplitPane1.setDividerLocation(300);
        jSplitPane1.setResizeWeight(0.3);

        lGames.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(lGames);

        bCreateGame.setText("Create");
        bCreateGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCreateGameActionPerformed(evt);
            }
        });

        bDeleteGame.setText("Delete");
        bDeleteGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDeleteGameActionPerformed(evt);
            }
        });

        bUpdateGame.setText("Update");
        bUpdateGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUpdateGameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(bCreateGame)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bUpdateGame, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bDeleteGame))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bCreateGame)
                    .addComponent(bDeleteGame)
                    .addComponent(bUpdateGame))
                .addContainerGap())
        );

        jSplitPane1.setLeftComponent(jPanel1);

        tAchievements.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tAchievements);

        bCreateAchievement.setText("Create");
        bCreateAchievement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCreateAchievementActionPerformed(evt);
            }
        });

        bDeleteAchievement.setText("Delete");
        bDeleteAchievement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDeleteAchievementActionPerformed(evt);
            }
        });

        bUpdateAchievement.setText("Update");
        bUpdateAchievement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUpdateAchievementActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(bCreateAchievement)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bUpdateAchievement, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bDeleteAchievement))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bCreateAchievement)
                    .addComponent(bDeleteAchievement)
                    .addComponent(bUpdateAchievement))
                .addContainerGap())
        );

        jSplitPane1.setRightComponent(jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void bCreateGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCreateGameActionPerformed
        CreateGamePanel panel = new CreateGamePanel();
        int result = JOptionPane.showConfirmDialog(MainForm.this, panel, "Create Game", JOptionPane.PLAIN_MESSAGE);
        switch (result) {
            case JOptionPane.OK_OPTION:
                gamesModel.create(panel.getDisplayName());
        }
    }//GEN-LAST:event_bCreateGameActionPerformed

    private void bDeleteGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDeleteGameActionPerformed
        if (!lGames.isSelectionEmpty()) {
            int result = JOptionPane.showConfirmDialog(MainForm.this, "Are you sure ?", "Delete Game", JOptionPane.YES_NO_OPTION);
            switch (result) {
                case JOptionPane.YES_OPTION:
                    Game game = lGames.getSelectedValue();
                    gamesModel.delete(game.getId());
            }
        } else {
            JOptionPane.showMessageDialog(MainForm.this, "Game must be selected!", "Game Selection Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bDeleteGameActionPerformed

    private void bUpdateGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUpdateGameActionPerformed
        if (!lGames.isSelectionEmpty()) {
            Game game = lGames.getSelectedValue();
            CreateGamePanel panel = new CreateGamePanel(game.getDisplayName());
            int result = JOptionPane.showConfirmDialog(MainForm.this, panel, "Create Game", JOptionPane.PLAIN_MESSAGE);
            switch (result) {
                case JOptionPane.OK_OPTION:
                    gamesModel.update(game.getId(), panel.getDisplayName());
            }
        } else {
            JOptionPane.showMessageDialog(MainForm.this, "Game must be selected!", "Game Selection Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bUpdateGameActionPerformed

    private void bCreateAchievementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCreateAchievementActionPerformed
        if (!lGames.isSelectionEmpty()) {
            Game game = lGames.getSelectedValue();
            CreateAchievementPanel panel = new CreateAchievementPanel(game);
            int result = JOptionPane.showConfirmDialog(MainForm.this, panel, "Create Achievement", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
            switch (result) {
                case JOptionPane.YES_OPTION:
                    achievementsModel.create(panel.getAchievement());
            }
        } else {
            JOptionPane.showMessageDialog(MainForm.this, "Game must be selected!", "Game Selection Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bCreateAchievementActionPerformed

    private void bDeleteAchievementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDeleteAchievementActionPerformed
        if (!lGames.isSelectionEmpty()) {
            int selectedRow = tAchievements.getSelectedRow();
            if (selectedRow >= 0) {
                int result = JOptionPane.showConfirmDialog(MainForm.this, "Are you sure ?", "Delete Achievement", JOptionPane.YES_NO_OPTION);
                switch (result) {
                    case JOptionPane.YES_OPTION:
                        Achievement achievement = achievementsModel.findOneBySelectedRow(selectedRow);
                        achievementsModel.delete(achievement.getId(), achievement.getGameId());
                }
            } else {
                JOptionPane.showMessageDialog(MainForm.this, "Achievement must be selected!", "Achievement Selection Error", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(MainForm.this, "Game must be selected!", "Game Selection Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bDeleteAchievementActionPerformed

    private void bUpdateAchievementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUpdateAchievementActionPerformed
        if (!lGames.isSelectionEmpty()) {
            Game game = lGames.getSelectedValue();
            int selectedRow = tAchievements.getSelectedRow();
            if (selectedRow >= 0) {
                Achievement achievement = achievementsModel.findOneBySelectedRow(selectedRow);
                CreateAchievementPanel panel = new CreateAchievementPanel(achievement, game);
                int result = JOptionPane.showConfirmDialog(MainForm.this, panel, "Create Achievement", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
                switch (result) {
                    case JOptionPane.YES_OPTION:
                        achievementsModel.update(achievement.getId(), panel.getAchievement());
                }
            } else {
                JOptionPane.showMessageDialog(MainForm.this, "Achievement must be selected!", "Achievement Selection Error", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(MainForm.this, "Game must be selected!", "Game Selection Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bUpdateAchievementActionPerformed

    public static void main(String args[]) {
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCreateAchievement;
    private javax.swing.JButton bCreateGame;
    private javax.swing.JButton bDeleteAchievement;
    private javax.swing.JButton bDeleteGame;
    private javax.swing.JButton bUpdateAchievement;
    private javax.swing.JButton bUpdateGame;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JList<Game> lGames;
    private javax.swing.JTable tAchievements;
    // End of variables declaration//GEN-END:variables
}
