package net.education.kyivstar.config;

import ch.vorburger.mariadb4j.DB;
import ch.vorburger.mariadb4j.DBConfigurationBuilder;

public class EducationEmbeddedMariaDb {
    private static ConfigDataBase conf = new ConfigDataBase();

    private static DB db = null;

    public static void startEmbeddedMariaDB() {
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

    public static void stopEmbeddedMariaDB() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                db.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
    }
}
