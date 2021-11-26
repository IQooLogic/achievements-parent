package rs.itcentar.achievements.data;

/**
 *
 * @author Milos Stojkovic <iqoologic@gmail.com>
 */
public class Game {

    public static final String COL_ID = "id";
    public static final String COL_DISPLAY_NAME = "displayName";

    private String id;
    private String displayName;

    public Game() {
    }

    public Game(String id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return "Game{" + "id=" + id + ", displayName=" + displayName + '}';
    }
}
