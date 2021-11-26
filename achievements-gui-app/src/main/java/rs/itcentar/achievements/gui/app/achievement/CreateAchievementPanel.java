package rs.itcentar.achievements.gui.app.achievement;

import rs.itcentar.achievements.data.Achievement;
import rs.itcentar.achievements.data.Game;

/**
 *
 * @author Milos Stojkovic <iqoologic@gmail.com>
 */
public class CreateAchievementPanel extends javax.swing.JPanel {

    private final Game game;

    public CreateAchievementPanel(Game game) {
        this.game = game;
        initComponents();

        tfGame.setText(game.getDisplayName());
    }

    public CreateAchievementPanel(Achievement achievement, Game game) {
        this(game);
        tfDisplayName.setText(achievement.getDisplayName());
        tfDescription.setText(achievement.getDescription());
        tfIcon.setText(achievement.getIcon());
    }

    public CreateAchievementDTO getAchievement() {
        return new CreateAchievementDTO(tfDisplayName.getText(), tfDescription.getText(),
                tfIcon.getText(), System.currentTimeMillis(), -1, game.getId());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        tfDisplayName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tfDescription = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tfIcon = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        tfGame = new javax.swing.JTextField();

        jLabel1.setText("DisplayName");

        jLabel2.setText("Description");

        jLabel3.setText("Icon");

        jLabel4.setText("Game");

        tfGame.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tfGame, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addComponent(tfIcon)
                    .addComponent(tfDescription)
                    .addComponent(tfDisplayName))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tfDisplayName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tfDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tfIcon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tfGame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField tfDescription;
    private javax.swing.JTextField tfDisplayName;
    private javax.swing.JTextField tfGame;
    private javax.swing.JTextField tfIcon;
    // End of variables declaration//GEN-END:variables
}
