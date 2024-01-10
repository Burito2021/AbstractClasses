package net.education.kyivstar;

import net.education.kyivstar.config.ConfigDataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector {
    ConfigDataBase config = new ConfigDataBase();

    public Connection connectMariaDb() {
        Connection conn = null;
        try {
            String url = config.getUrl()+":"+config.getPort()+"/"+config.getDbName();
            conn = DriverManager.getConnection(url,config.getUser(), config.getPass());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
