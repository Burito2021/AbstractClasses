package net.education.kyivstar.stuff;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.education.kyivstar.config.ConfigDataBase;

import java.sql.Connection;
import java.sql.SQLException;

public class SynchronizedHikari {
    private final ConfigDataBase configDataBase;
    private static SynchronizedHikari instance;
    private final HikariConfig hikariConfig = new HikariConfig();
    private volatile HikariDataSource ds;

    private SynchronizedHikari(ConfigDataBase configDataBase) {
        this.configDataBase = configDataBase;
    }

    public synchronized static SynchronizedHikari getInstance(ConfigDataBase configDataBase){
        if(instance==null) {
            instance = new SynchronizedHikari(configDataBase);
        }
        return instance;
    }

 public  void delay(){

 }

    public synchronized Connection connection() {
            try {
                return ds.getConnection();
            } catch (SQLException e) {
                throw new RuntimeException("Error connecting pool to DB: " + e);
        }
    }
}
