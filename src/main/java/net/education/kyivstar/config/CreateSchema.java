package net.education.kyivstar.config;

import net.education.kyivstar.DbConnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static net.education.kyivstar.config.ScriptFileReader.readSqlScriptFromFile;

public class CreateSchema extends DbConnector {

    DbConnector dbConnector = new DbConnector();
    Connection connDb = null;
    Connection connection = null;
    ConfigDataBase config = new ConfigDataBase();

    public Connection connectMariaDbWithoutDbName() {
        Connection conn = null;
        try {
            String url = config.getUrl() + ":" + config.getPort();

            conn = DriverManager.getConnection(url);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void createDataBase() throws SQLException {
        connection = connectMariaDbWithoutDbName();
        Statement statement = null;
        try {
            statement = connection.createStatement();
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
        } finally {
            statement.close();
            connection.close();
        }

    }

    public void createTables() throws SQLException {
        Statement statement = null;
        connDb = dbConnector.connectMariaDb();
        try {
            statement = connDb.createStatement();

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
        } finally {
            connDb.setAutoCommit(true);
            statement.close();
            connDb.close();
        }
    }
}
