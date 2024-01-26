package net.education.kyivstar.config;

import ch.vorburger.mariadb4j.DB;
import ch.vorburger.mariadb4j.DBConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class EmbeddedMariaDbLifeCycle {
    private final ConfigDataBase conf;
    private DB db = null;

    private final static Logger logger = LoggerFactory.getLogger(EmbeddedMariaDbLifeCycle.class);

    public EmbeddedMariaDbLifeCycle(ConfigDataBase conf) {
        this.conf = conf;
        startEmbeddedMariaDB();
    }

    public void startEmbeddedMariaDB() {
        var config = DBConfigurationBuilder.newBuilder();

        try {
            config.setPort(conf.getPort());
            config.setDataDir(conf.getDirectory());
            db = DB.newEmbeddedDB(config.build());
            db.start();
        } catch (Exception e) {
            logger.debug("Start of embedded mariaDb failed due to " + e);
            throw new RuntimeException("Start of embedded mariaDb failed due to "+e);
        }
    }

    public void stopEmbeddedMariaDB() {
        try {
            logger.info("Stopping embedded MariaDB...");
            if (db != null) {
                db.stop();
                logger.info("Embedded MariaDB stopped successfully.");
            }
        } catch (Exception e) {
            logger.debug("Error stopping embedded MariaDB", e);
            throw new RuntimeException("Error stopping embedded MariaDB " + e);
        }
    }

    public Connection connectMariaDb(boolean useDbName) {
        Connection conn;
        try {
            logger.info("Db connection initialized");

            conn = connectionBuilder(useDbName);

            logger.info("Db connection successfully completed");
        } catch (SQLException e) {
            logger.debug("Db connection failed due to " + e);
            throw new RuntimeException("Db connection failed due to " + e);
        }

        return conn;
    }

    private Connection connectionBuilder(boolean useDbName) throws SQLException {
        Connection connection;

        if (useDbName) {
            connection = DriverManager.getConnection(conf.getUrl() + ":" + conf.getPort() + "/" + conf.getDbName(), conf.getUser(), conf.getPass());
        } else {
            connection = DriverManager.getConnection(conf.getUrl() + ":" + conf.getPort());
        }

        return connection;
    }
}
