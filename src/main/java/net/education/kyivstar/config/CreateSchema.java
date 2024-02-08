package net.education.kyivstar.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Statement;

import static net.education.kyivstar.services.util.ConfigSchemaPassUtils.DATABASE_PATH;
import static net.education.kyivstar.services.util.ConfigSchemaPassUtils.TABLES_PATH;
import static net.education.kyivstar.services.util.Utils.readSqlScriptFromFileStream;


public class CreateSchema {
    private DbConnector dbConnector;
    private HikariConfiguration hikari;

    public CreateSchema(DbConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    public CreateSchema(HikariConfiguration hikari) {
        this.hikari = hikari;
    }

    private static final Logger logger = LoggerFactory.getLogger(CreateSchema.class);

   /* public void createDataBase() {

        try (Connection connDb = dbConnector.connectMariaDb(false);
             Statement statement = connDb.createStatement()) {
            logger.info("Data base creation is initialized");
            connDb.setAutoCommit(false);

            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(DATABASE_PATH);
            String sqlScript = readSqlScriptFromFileStream(inputStream);

            String[] statements = sqlScript.split(";");
            for (String singleStatement : statements) {
                if (!singleStatement.trim().isEmpty()) {
                    statement.addBatch(singleStatement);
                }

            }
            statement.executeBatch();
            connDb.commit();
            logger.info("Data base creation is successfully completed");
        } catch (Exception e) {
            throw new RuntimeException("Db creation failed " + e);
        }
    }

    public void createTables() {

        try (Connection connDb = dbConnector.connectMariaDb(true);
             Statement statement = connDb.createStatement()) {
            logger.info("Table creation initialized");
            connDb.setAutoCommit(false);

            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(TABLES_PATH);
            String sqlScript = readSqlScriptFromFileStream(inputStream);

            String[] statements = sqlScript.split(";");
            for (String singleStatement : statements) {
                if (!singleStatement.trim().isEmpty()) {
                    statement.addBatch(singleStatement);
                }

            }
            statement.executeBatch();

            connDb.commit();
            logger.info("Table creation is accomplished");
        } catch (Exception e) {
            throw new RuntimeException("Table creation failed " + e);
        }
    }*/


    public void createDataBase() {

        try (Connection connDb = hikari.connect();
             Statement statement = connDb.createStatement()) {
            logger.info("Data base creation is initialized");
            connDb.setAutoCommit(false);

            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(DATABASE_PATH);
            String sqlScript = readSqlScriptFromFileStream(inputStream);

            String[] statements = sqlScript.split(";");
            for (String singleStatement : statements) {
                if (!singleStatement.trim().isEmpty()) {
                    statement.addBatch(singleStatement);
                }

            }
            statement.executeBatch();
            connDb.commit();
            logger.info("Data base creation is successfully completed");
        } catch (Exception e) {
            throw new RuntimeException("Db creation failed " + e);
        }
    }

    public void createTables() {

        try (Connection connDb = hikari.connect();
             Statement statement = connDb.createStatement()) {
            logger.info("Table creation initialized");
            connDb.setAutoCommit(false);

            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(TABLES_PATH);
            String sqlScript = readSqlScriptFromFileStream(inputStream);

            String[] statements = sqlScript.split(";");
            for (String singleStatement : statements) {
                if (!singleStatement.trim().isEmpty()) {
                    statement.addBatch(singleStatement);
                }

            }
            statement.executeBatch();

            connDb.commit();
            logger.info("Table creation is accomplished");
        } catch (Exception e) {
            throw new RuntimeException("Table creation failed " + e);
        }
    }
}
