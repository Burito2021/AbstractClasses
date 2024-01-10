package net.education.kyivstar;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

import static net.education.kyivstar.config.EducationEmbeddedMariaDb.startEmbeddedMariaDB;
import static net.education.kyivstar.config.EducationEmbeddedMariaDb.stopEmbeddedMariaDB;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws SQLException {
      // stopEmbeddedMariaDB();
     /*   startEmbeddedMariaDB();
        CreateSchema createSchema = new CreateSchema();
        createSchema.createDataBase();
        createSchema.createTables();
        //stopEmbeddedMariaDB();*/
      // final var f =  PasswordEncryptor.encrypt("password");
        startEmbeddedMariaDB();
        DbConnector dbConnector = new DbConnector();
        dbConnector.connectMariaDb();
        stopEmbeddedMariaDB();
    }

}
