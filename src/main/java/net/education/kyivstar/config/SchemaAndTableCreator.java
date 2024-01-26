package net.education.kyivstar.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static net.education.kyivstar.services.util.Utils.readSqlScriptFromFileStream;

public class SchemaAndTableCreator {
    private final static String DATABASE_PATH = "scripts/init.sql";
    private final static String TABLES_PATH = "scripts/schema.sql";
    private static final Logger logger = LoggerFactory.getLogger(SchemaAndTableCreator.class);
    private final EmbeddedMariaDbLifeCycle embeddedMariaDbLifeCycle;

    public SchemaAndTableCreator(EmbeddedMariaDbLifeCycle embeddedMariaDbLifeCycle) {
        this.embeddedMariaDbLifeCycle = embeddedMariaDbLifeCycle;
    }

    public void createDataBase() {try (var connDb = embeddedMariaDbLifeCycle.connectMariaDb(false);
             var statement = connDb.createStatement();
             var inputStream = getClass().getClassLoader().getResourceAsStream(DATABASE_PATH)) {

            create(inputStream, statement, connDb);
        } catch (Exception e) {
            logger.debug("Db creation failed " + e);
            throw new RuntimeException("Db creation failed " + e);
        }
    }

    public void createTables() {
        try (var connDb = embeddedMariaDbLifeCycle.connectMariaDb(true);
             var statement = connDb.createStatement();
             var inputStream = getClass().getClassLoader().getResourceAsStream(TABLES_PATH)) {

            create(inputStream, statement, connDb);
        } catch (Exception e) {
            logger.debug("Table creation failed " + e);
            throw new RuntimeException("Table creation failed " + e);
        }
    }

    private void create(InputStream inputStream, Statement statement, Connection connDb) throws SQLException {
        var sqlScript = readSqlScriptFromFileStream(inputStream);
        var statements = sqlScript.split(";");
        connDb.setAutoCommit(false);

        for (String singleStatement : statements) {
            if (!singleStatement.trim().isEmpty()) {
                statement.addBatch(singleStatement);
            }
        }

        statement.executeBatch();
        connDb.commit();
        logger.info("Table creation is accomplished");
    }
}
