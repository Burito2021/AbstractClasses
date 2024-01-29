package net.education.kyivstar.config;

import ch.vorburger.mariadb4j.DB;
import ch.vorburger.mariadb4j.DBConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MariaDbDeploy {

    private final static Logger logger = LoggerFactory.getLogger(MariaDbDeploy.class);
    private static ConfigDataBase conf;
    private DB db = null;
    public MariaDbDeploy(ConfigDataBase conf) {
        this.conf = conf;
    }

    public void startEmbeddedMariaDB() {
        try {
            DBConfigurationBuilder config = DBConfigurationBuilder.newBuilder();
            config.setPort(conf.getPort());
            config.setDataDir(conf.getDirectory());
            db = DB.newEmbeddedDB(config.build());
            db.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopEmbeddedMariaDB() {
        try {

            System.out.println("Stopping embedded MariaDB...");
            logger.info("Stopping embedded MariaDB...");
            if (db != null) {
                db.stop();
                System.out.println("Embedded MariaDB stopped successfully.");
                logger.info("Embedded MariaDB stopped successfully.");
            }
        } catch (Exception e) {
            logger.error("Error stopping embedded MariaDB", e);
        }
    }
}
