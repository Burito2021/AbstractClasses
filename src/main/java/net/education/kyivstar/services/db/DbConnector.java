package net.education.kyivstar.services.db;

import net.education.kyivstar.config.ConfigDataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector {
    ConfigDataBase config = new ConfigDataBase();

    public Connection connectMariaDb(boolean useDbName) {
        Connection conn = null;
        try {
            if (useDbName) {
                String url = config.getUrl() + ":" + config.getPort() + "/" + config.getDbName();
                conn = DriverManager.getConnection(url, config.getUser(), config.getPass());
                System.out.println("!!!!! " + config.getPass());
            } else {
                String url = config.getUrl() + ":" + config.getPort();
                conn = DriverManager.getConnection(url);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
