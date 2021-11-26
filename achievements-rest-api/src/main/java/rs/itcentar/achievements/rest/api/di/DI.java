package rs.itcentar.achievements.rest.api.di;

import rs.itcentar.achievemvents.dal.repo.AchievementRepository;
import rs.itcentar.achievemvents.dal.repo.GameRepository;

/**
 *
 * @author Milos Stojkovic <iqoologic@gmail.com>
 */
public class DI {

    private static GameRepository gameRepo;
    private static AchievementRepository achievementRepo;

    private DI() {
    }

    public static GameRepository getGameRepo() {
        return gameRepo;
    }

    public static void setGameRepo(GameRepository repo) {
        gameRepo = repo;
    }

    public static AchievementRepository getAchievementRepo() {
        return achievementRepo;
    }

    public static void setAchievementRepo(AchievementRepository repo) {
        achievementRepo = repo;
    }
}
