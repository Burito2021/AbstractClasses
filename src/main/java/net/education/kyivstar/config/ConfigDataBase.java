package net.education.kyivstar.config;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class ConfigDataBase {
    private String url;
    private int port;
    private String dbName;
    private String directory;
    private String user;
    private String password;
    public ConfigDataBase() {
        loadConfig();
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

    private void loadConfig() {

        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.yml");
            Yaml yaml = new Yaml();
            Map<String, Map<String, Object>> yamlData = yaml.load(inputStream);

            Map<String, Object> dbConfig = yamlData.get("database");

            url = (String) dbConfig.get("url");
            port = (int) dbConfig.get("port");
            dbName = (String) dbConfig.get("dbName");
            directory = (String) dbConfig.get("directory");
            password = (String) dbConfig.get("password");
            user = (String) dbConfig.get("user");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
