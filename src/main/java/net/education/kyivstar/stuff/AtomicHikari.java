package net.education.kyivstar.stuff;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.education.kyivstar.config.ConfigDataBase;

import java.util.concurrent.atomic.AtomicBoolean;

public class AtomicHikari {
    private final ConfigDataBase configDataBase;
    private final HikariConfig hikariConfig = new com.zaxxer.hikari.HikariConfig();
    private final AtomicBoolean dataSourceLock = new AtomicBoolean(false);
    private HikariDataSource ds;

    public AtomicHikari(ConfigDataBase configDataBase, HikariDataSource ds) {
        this.configDataBase = configDataBase;
        this.ds = ds;
    }

    private void getDataSource() {
        // Ensure only one thread at a time can enter this block
        if (dataSourceLock.compareAndSet(false, true)) {
            try {
                hikariConfig.setMaxLifetime(10);
                hikariConfig.setMaximumPoolSize(50);
                hikariConfig.setConnectionTimeout(10000);
                hikariConfig.setJdbcUrl(configDataBase.getUrl() + ":" + configDataBase.getPort() + "/" + configDataBase.getDbName());
                hikariConfig.setUsername(configDataBase.getUser());
                hikariConfig.setPassword(configDataBase.getPass());
                ds = new HikariDataSource(hikariConfig);
            } finally {
                dataSourceLock.set(false); // Release the lock
            }
        } else {
            throw new IllegalStateException("Another thread is already initializing the DataSource.");
        }
    }


}
