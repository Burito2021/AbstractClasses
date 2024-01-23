package net.education.kyivstar.config;

import net.education.kyivstar.services.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.util.Map;

public class ConfigDataBase {
    private String url;
    private int port;
    private String dbName;
    private String directory;
    private String user;
    private String password;

    private static final Logger logger = LoggerFactory.getLogger(ConfigDataBase.class);

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

    public void loadConfig() {

        try (var inputStream = Utils.class.getClassLoader().getResourceAsStream("config/application.yml")) {
            Yaml yaml = new Yaml();
            Map<String, Map<String, Object>> yamlData = yaml.load(inputStream);

            Map<String, Object> dbConfig = yamlData.get("database");

            url = (String) dbConfig.get("url");
            port = (int) dbConfig.get("port");
            dbName = (String) dbConfig.get("dbName");
            directory = (String) dbConfig.get("directory");
            user = (String) dbConfig.get("username");

            if (dbConfig.get("password") != null && ((String) dbConfig.get("password")).startsWith("ENC(") && ((String) dbConfig.get("password")).endsWith(")")) {
                var encryptedPassword = (String) dbConfig.get("password");
                var decryptedPassword = PasswordEncryptor.decrypt(encryptedPassword.substring(4, encryptedPassword.length() - 1));
                password = decryptedPassword;
            } else {
                password = (String) dbConfig.get("password");
            }
        } catch (Exception e) {
            logger.info("error " + e);
        }
    }
}
