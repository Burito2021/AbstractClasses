package net.education.kyivstar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector {

    public Connection connectDb() {
        Connection conn = null;
        try {
            String url = "jdbc:mariadb://localhost:3310/EDUCATION";
            conn = DriverManager.getConnection(url, "root", "password");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
