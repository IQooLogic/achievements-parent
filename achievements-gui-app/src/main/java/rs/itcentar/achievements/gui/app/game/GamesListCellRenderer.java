package rs.itcentar.achievements.gui.app.game;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import rs.itcentar.achievements.data.Game;

/**
 *
 * @author Milos Stojkovic <iqoologic@gmail.com>
 */
public class GamesListCellRenderer extends JLabel implements ListCellRenderer<Game> {

    public GamesListCellRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Game> list, Game game, int index, boolean isSelected, boolean cellHasFocus) {
        setText(game.getDisplayName());

        if (isSelected || cellHasFocus) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        return this;
    }

}
