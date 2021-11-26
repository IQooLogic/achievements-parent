package rs.itcentar.achievements.rest.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Milos Stojkovic <iqoologic@gmail.com>
 */
public class DBConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(DBConfig.class);
    private Properties prop = new Properties();

    public DBConfig() {
        try (InputStream is = DBConfig.class.getClassLoader().getResourceAsStream("database.properties")) {
            prop.load(is);
        } catch (IOException ex) {
            LOGGER.error("Error parsing database.properties", ex);
        }
    }

    public String getJDBCDriver() {
        return prop.getProperty("driver");
    }

    public String getHost() {
        return prop.getProperty("host");
    }

    public String getPort() {
        return prop.getProperty("port");
    }

    public String getDBName() {
        return prop.getProperty("dbname");
    }

    public String getUsername() {
        return prop.getProperty("username");
    }

    public String getPassword() {
        return prop.getProperty("password");
    }

    public String getDBUrlPattern() {
        return prop.getProperty("dburl");
    }
}
