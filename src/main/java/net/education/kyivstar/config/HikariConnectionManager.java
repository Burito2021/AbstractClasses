package net.education.kyivstar.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class HikariConnectionManager {
    private final static Logger logger = LoggerFactory.getLogger(HikariConnectionManager.class);
    private static HikariConnectionManager instance;
    private final ConfigDataBase configDataBase;
    private final HikariConfig hikariConfig = new com.zaxxer.hikari.HikariConfig();
    private HikariDataSource ds;

    public HikariConnectionManager(ConfigDataBase configDataBase) {
        this.configDataBase = configDataBase;
        getDataSource();
    }

    public static HikariConnectionManager getInstance(ConfigDataBase configDataBase) {
        if (instance == null) {
            synchronized (HikariConnectionManager.class) {
                if(instance ==null) {
                    instance = new HikariConnectionManager(configDataBase);
                }
            }
        }

        return instance;
    }

    private void getDataSource() {
        hikariConfig.setConnectionTimeout(10000);
        hikariConfig.setMaxLifetime(10000);
        hikariConfig.setMaximumPoolSize(50);
        hikariConfig.setJdbcUrl(configDataBase.getUrl() + ":"
                + configDataBase.getPort() + "/" + configDataBase.getDbName());
        hikariConfig.setPassword(configDataBase.getPass());
        hikariConfig.setUsername(configDataBase.getUser());
        ds = new HikariDataSource(hikariConfig);
    }

    public Connection connect() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting pool to DB: " + e);
        }
    }

    public void closeConnection() {
        if (ds != null
                && !ds.isClosed()) {
            logger.info("Hikari pool is closed successfully!");
            ds.close();
        }
    }
}
