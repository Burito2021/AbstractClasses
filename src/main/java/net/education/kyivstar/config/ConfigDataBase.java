package net.education.kyivstar.config;

import net.education.kyivstar.services.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

import static net.education.kyivstar.services.util.ConfigSchemaPassUtils.*;

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

        try (var inputStream = Utils.class.getClassLoader().getResourceAsStream(CONFIG_PATH)) {

            var yaml = new Yaml();
            Map<String, Map<String, Object>> yamlData = streamNullValidator(yaml,inputStream);

            Map<String, Object> dbConfig = yamlData.get(DATABASE);

            url = stringNullValidator(dbConfig, URL);
            port = intNullValidator(dbConfig, PORT);
            dbName = stringNullValidator(dbConfig, DB_NAME);
            directory = stringNullValidator(dbConfig, DIRECTORY);
            user = stringNullValidator(dbConfig, USER_NAME);

            if (dbConfig.get(PASSWORD) != null && ((String) dbConfig.get(PASSWORD)).startsWith(ENC) && ((String) dbConfig.get(PASSWORD)).endsWith(BACK_BRACKET)) {
                var encryptedPassword = (String) dbConfig.get(PASSWORD);
                var decryptedPassword = PasswordEncryptor.decrypt(encryptedPassword.substring(4, encryptedPassword.length() - 1));
                password = decryptedPassword;
            } else {
                password = (String) dbConfig.get(PASSWORD);
            }
        } catch (Exception e) {
            throw new RuntimeException("Config build failed due to "+e);
        }
    }

    private int intNullValidator(Map<String, Object> dbConfig, String key) {
        var value = dbConfig.get(key);
        if (value == null) {
            throw new RuntimeException(key + "is null");
        }
        return (int) value;
    }

    private String stringNullValidator(Map<String, Object> dbConfig, String key) {
        var value = dbConfig.get(key);

        if (value == null) {
            throw new RuntimeException(key + "is null");
        }
        return (String) value;
    }

    private Map<String, Map<String, Object>> streamNullValidator(Yaml yaml, InputStream inputStream) {
        Map<String, Map<String, Object>> yamlData = yaml.load(inputStream);

        if (yamlData == null) {
            throw new RuntimeException( "Config is null");
        }
        return yamlData;
    }
}
