package rs.itcentar.achievements.rest.api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rs.itcentar.achievements.rest.api.di.DI;
import rs.itcentar.achievemvents.dal.repo.impl.AchievementRepositoryImpl;
import rs.itcentar.achievemvents.dal.repo.impl.GameRepositoryImpl;

/**
 *
 * @author Milos Stojkovic <iqoologic@gmail.com>
 */
public class AchievementsApp extends ResourceConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(AchievementsApp.class);

    public AchievementsApp() {
        LOGGER.info(">>> ACHIEVEMENTS REST API <<<");

        initDataBase();

        packages("rs.itcentar.achievements.rest.api.resources");
//        register(AchievementsResource.class);
//        register(GamesResource.class);
    }

    private void initDataBase() {
        try {
            DBConfig dbConfig = new DBConfig();
            Class.forName(dbConfig.getJDBCDriver()).newInstance();
            LOGGER.info("CONNECT TO {}:{}", dbConfig.getHost(), dbConfig.getPort());
            Connection connectionGame = DriverManager.getConnection(String.format(dbConfig.getDBUrlPattern(),
                    dbConfig.getHost(), dbConfig.getPort(), dbConfig.getDBName()), dbConfig.getUsername(), dbConfig.getPassword());
            Connection connectionAchievement = DriverManager.getConnection(String.format(dbConfig.getDBUrlPattern(),
                    dbConfig.getHost(), dbConfig.getPort(), dbConfig.getDBName()), dbConfig.getUsername(), dbConfig.getPassword());
            LOGGER.info("CONNECTED TO {}:{}", dbConfig.getHost(), dbConfig.getPort());
            DI.setGameRepo(new GameRepositoryImpl(connectionGame));// nije preporuceno da se radi kroz setter vec kroz constructor jer setter moze da se zaboravi a constructor ne
            DI.setAchievementRepo(new AchievementRepositoryImpl(connectionAchievement));
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            LOGGER.error("Error connecting to database", ex);
        }
    }
}
