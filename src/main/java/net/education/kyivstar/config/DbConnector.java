package net.education.kyivstar.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector {
    private static final Logger logger = LoggerFactory.getLogger(DbConnector.class);

    ConfigDataBase config;

    public DbConnector(ConfigDataBase config) {
        this.config = config;
    }

    public Connection connectMariaDb(boolean useDbName) {
        Connection conn;
        try {
            logger.info("Db connection initialized");

            conn = connectionBuilder(useDbName);

            logger.info("Db connection successfully completed");
        } catch (SQLException e) {
            throw new RuntimeException("Db connection failed due to " + e);
        }
        return conn;
    }

    private Connection connectionBuilder(boolean useDbName) throws SQLException {
        if (useDbName) {
            return DriverManager.getConnection(config.getUrl() + ":" + config.getPort() + "/" + config.getDbName(), config.getUser(), config.getPass());
        } else {
            return DriverManager.getConnection(config.getUrl() + ":" + config.getPort());
        }
    }
}
