package net.education.kyivstar.stuff;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.education.kyivstar.config.ConfigDataBase;

import java.sql.Connection;
import java.sql.SQLException;

public class SynchronizedLockHikari {
    private final ConfigDataBase configDataBase;
    private static SynchronizedLockHikari instance;
    private final HikariConfig hikariConfig = new HikariConfig();
    private HikariDataSource ds;
    private static final Object lock = new Object();


    private SynchronizedLockHikari(ConfigDataBase configDataBase) {
        this.configDataBase = configDataBase;
        getDataSource();
    }


    public static SynchronizedLockHikari getInstance(ConfigDataBase configDataBase) {
        synchronized (lock) {
            if (instance == null) {
                instance = new SynchronizedLockHikari(configDataBase);
            }
        }
        return instance;
    }

    private void getDataSource() {

        hikariConfig.setMaxLifetime(5);
        hikariConfig.setConnectionTimeout(10000);
        hikariConfig.setMaximumPoolSize(50);
        hikariConfig.setJdbcUrl(configDataBase.getUrl() + ":" + configDataBase.getPort() + "/" + configDataBase.getDbName());
        hikariConfig.setUsername(configDataBase.getUser());
        hikariConfig.setPassword(configDataBase.getPass());
        ds = new HikariDataSource(hikariConfig);
    }

    public Connection connection() {
        synchronized (lock) {
            try {
                return ds.getConnection();
            } catch (SQLException e) {
                throw new RuntimeException("Error connecting pool to DB: " + e);
            }
        }
    }
}
