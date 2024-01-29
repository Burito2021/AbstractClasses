package net.education.kyivstar.config;

import net.education.kyivstar.services.util.Utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;

import static net.education.kyivstar.services.util.Utils.*;


public class CreateSchema {
    private DbConnector dbConnector;

    public CreateSchema(DbConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    public void createDataBase() {

        try (Connection connDb = dbConnector.connectMariaDb(true);
             Statement statement = connDb.createStatement()) {

            connDb.setAutoCommit(false);

            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("scripts/init.sql");
            String sqlScript = readSqlScriptFromFileStream(inputStream);

            String[] statements = sqlScript.split(";");
            for (String singleStatement : statements) {
                if (!singleStatement.trim().isEmpty()) {
                    statement.addBatch(singleStatement);
                }

            }
            statement.executeBatch();

            connDb.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createTables() {

        try (Connection connDb = dbConnector.connectMariaDb(true);
             Statement statement = connDb.createStatement()) {

            connDb.setAutoCommit(false);

            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("scripts/schema.sql");
            String sqlScript = readSqlScriptFromFileStream(inputStream);

            String[] statements = sqlScript.split(";");
            for (String singleStatement : statements) {
                if (!singleStatement.trim().isEmpty()) {
                    statement.addBatch(singleStatement);
                }

            }
            statement.executeBatch();

            connDb.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
