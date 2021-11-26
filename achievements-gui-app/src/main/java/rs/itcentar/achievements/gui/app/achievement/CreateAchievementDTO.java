package rs.itcentar.achievements.gui.app.achievement;

/**
 *
 * @author Milos Stojkovic <iqoologic@gmail.com>
 */
public class CreateAchievementDTO {

    private final String displayName;
    private final String description;
    private final String icon;
    private final long created;
    private final long updated;
    private final String gameId;

    public CreateAchievementDTO(String displayName, String description,
            String icon, long created, long updated, String gameId) {
        this.displayName = displayName;
        this.description = description;
        this.icon = icon;
        this.created = created;
        this.updated = updated;
        this.gameId = gameId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    public long getCreated() {
        return created;
    }

    public long getUpdated() {
        return updated;
    }

    public String getGameId() {
        return gameId;
    }

    @Override
    public String toString() {
        return "Achievement{" + "displayName=" + displayName + ", description=" + description + ", icon=" + icon + ", created=" + created + ", updated=" + updated + ", gameId=" + gameId + '}';
    }
}
