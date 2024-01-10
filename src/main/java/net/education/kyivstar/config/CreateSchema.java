package net.education.kyivstar.config;

import net.education.kyivstar.services.db.DbConnector;

import java.sql.Connection;
import java.sql.Statement;

import static net.education.kyivstar.services.util.Utils.readSqlScriptFromFile;

public class CreateSchema extends DbConnector {

    public void createDataBase() {

        try (Connection connection = connectMariaDb(false);
             Statement statement = connection.createStatement()) {

            String sqlScript = readSqlScriptFromFile("src/main/resources/mariaDb/init.sql");

            String[] statements = sqlScript.split(";");
            for (String singleStatement : statements) {
                if (!singleStatement.trim().isEmpty()) {
                    statement.addBatch(singleStatement);
                }
            }

            statement.executeBatch();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createTables() {

        try (Connection connDb = connectMariaDb(true);
             Statement statement = connDb.createStatement()) {

            connDb.setAutoCommit(false);

            String sqlScript = readSqlScriptFromFile("src/main/resources/mariaDb/schema.sql");

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
