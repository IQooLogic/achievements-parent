package rs.itcentar.achievements.data;

import java.util.List;

/**
 *
 * @author Milos Stojkovic <iqoologic@gmail.com>
 */
public class ListAchievementsResponse {

    private List<Achievement> achievements;
    private long totalCount;

    public ListAchievementsResponse() {
    }

    public ListAchievementsResponse(List<Achievement> achievements, long totalCount) {
        this.achievements = achievements;
        this.totalCount = totalCount;
    }

    public List<Achievement> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<Achievement> achievements) {
        this.achievements = achievements;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }
}
