package net.education.kyivstar;


import net.education.kyivstar.services.db.DbConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.education.kyivstar.config.EducationEmbeddedMariaDb.startEmbeddedMariaDB;
import static net.education.kyivstar.config.EducationEmbeddedMariaDb.stopEmbeddedMariaDB;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        startEmbeddedMariaDB();
        DbConnector dbConnector = new DbConnector();
        stopEmbeddedMariaDB();
    }
}
