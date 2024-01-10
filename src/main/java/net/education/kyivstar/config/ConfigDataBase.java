package net.education.kyivstar.config;

import static net.education.kyivstar.services.util.Utils.loadConfig;

public class ConfigDataBase {
    private String url;
    private int port;
    private String dbName;
    private String directory;
    private String user;
    private String password;

    public ConfigDataBase() {
        loadConfig(this);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return password;
    }

    public String getDirectory() {
        return directory;
    }

    public String getUrl() {
        return url;
    }

    public Integer getPort() {
        return port;
    }

    public String getDbName() {
        return dbName;
    }
}
