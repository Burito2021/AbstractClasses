package net.education.kyivstar.config;

import net.education.kyivstar.services.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

import static java.lang.String.valueOf;
import static net.education.kyivstar.services.util.Utils.createDirectoryIfNotExists;

public class ConfigDataBase {
    private final static String URL = "url";
    private final static String PORT = "port";
    private final static String DATABASE = "database";
    private final static String DB_NAME = "dbName";
    private final static String USER_NAME = "username";
    private final static String ENC = "ENC(";
    private final static String BACK_BRACKET = ")";
    private final static String PASSWORD = "password";
    private final static String DIRECTORY = "directory";
    private final static String CONFIG_PATH = "config/application.yml";

    private String url;
    private int port;
    private String dbName;
    private String directory;
    private String user;
    private String password;

    private static final Logger logger = LoggerFactory.getLogger(ConfigDataBase.class);

    public ConfigDataBase() {
        loadConfig();
        createDirectoryIfNotExists(valueOf(getDirectory()));
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

    public int getPort() {
        return port;
    }

    public String getDbName() {
        return dbName;
    }

    public void loadConfig() {
        try (var inputStream = Utils.class.getClassLoader().getResourceAsStream(CONFIG_PATH)) {
            var yaml = new Yaml();
            Map<String, Map<String, Object>> yamlData = yaml.load(inputStream);

            var dbConfig = yamlData.get(DATABASE);

            Optional.of(dbConfig.get(URL)).ifPresent(urlValue -> url = (String) dbConfig.get(URL));
            Optional.of(dbConfig.get(PORT)).ifPresent(portValue -> port = (int) dbConfig.get(PORT));
            Optional.of(dbConfig.get(DB_NAME)).ifPresent(dbNameValue -> dbName = (String) dbConfig.get(DB_NAME));
            Optional.of(dbConfig.get(DIRECTORY)).ifPresent(directoryValue -> directory = (String) dbConfig.get(DIRECTORY));
            Optional.of(dbConfig.get(USER_NAME)).ifPresent(userNameValue -> user = (String) dbConfig.get(USER_NAME));
            Optional.of(dbConfig.get(PASSWORD)).isPresent();

            if (((String) dbConfig.get(PASSWORD)).startsWith(ENC)
                    && ((String) dbConfig.get(PASSWORD)).endsWith(BACK_BRACKET)) {
                var encryptedPassword = (String) dbConfig.get(PASSWORD);
                password = PasswordEncryptor.decrypt(encryptedPassword.substring(4, encryptedPassword.length() - 1));
            } else {
                password = (String) dbConfig.get(PASSWORD);
            }
        } catch (Exception e) {
            logger.debug("Config build failed due to " + e);
            throw new RuntimeException("Config build failed due to "+e);
        }
    }
}
