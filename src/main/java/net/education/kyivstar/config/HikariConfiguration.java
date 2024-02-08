package net.education.kyivstar.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class HikariConfiguration {
    private HikariConfig hikariConfig = new HikariConfig();
    private HikariDataSource ds;
    private ConfigDataBase configDataBase;

    public HikariConfiguration(ConfigDataBase configDataBase, boolean useDbName) {
        this.configDataBase = configDataBase;
        getDataSource(useDbName);
    }

    private void getDataSource(boolean useDbName) {
        hikariConfig.setConnectionTimeout(10000);
        hikariConfig.setMaxLifetime(10000);
        hikariConfig.setMaximumPoolSize(2);
            configBuilder(useDbName);
        ds = new HikariDataSource(hikariConfig);
    }

    public Connection connect() {
        try {

            return ds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Db error", e);
        }
    }

    private void configBuilder(boolean useDbName) {
        if (useDbName) {
            hikariConfig.setJdbcUrl(configDataBase.getUrl() + ":" + configDataBase.getPort() + "/" + configDataBase.getDbName());
            hikariConfig.setPassword(configDataBase.getPass());
            hikariConfig.setUsername(configDataBase.getUser());
        } else {
            hikariConfig.setJdbcUrl(configDataBase.getUrl() + ":" + configDataBase.getPort());
        }
    }

    public void closeConnection() {
        if (ds != null && !ds.isClosed()) {
            ds.close();
        }
    }

}
