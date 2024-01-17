package net.education.kyivstar.services.util;

import net.education.kyivstar.config.ConfigDataBase;
import net.education.kyivstar.config.PasswordEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class Utils {
    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    ConfigDataBase configDataBase = new ConfigDataBase();

    public static String currentDateTime() {
        var dateAndTime = LocalDateTime.now();
        return dateAndTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }

    public static void loadConfig(ConfigDataBase configDataBase) {

        try (var inputStream = Utils.class.getClassLoader().getResourceAsStream("application.yml")) {
            Yaml yaml = new Yaml();
            Map<String, Map<String, Object>> yamlData = yaml.load(inputStream);

            Map<String, Object> dbConfig = yamlData.get("database");

            configDataBase.setUrl((String) dbConfig.get("url"));
            configDataBase.setPort((int) dbConfig.get("port"));
            configDataBase.setDbName((String) dbConfig.get("dbName"));
            configDataBase.setDirectory((String) dbConfig.get("directory"));
            configDataBase.setUser((String) dbConfig.get("user"));

            if (dbConfig.get("password") != null && ((String) dbConfig.get("password")).startsWith("ENC(") && ((String) dbConfig.get("password")).endsWith(")")) {
                var encryptedPassword = (String) dbConfig.get("password");
                var decryptedPassword = PasswordEncryptor.decrypt(encryptedPassword.substring(4, encryptedPassword.length() - 1));
                configDataBase.setPassword(decryptedPassword);
            } else {
                configDataBase.setPassword((String) dbConfig.get("password"));
            }
        } catch (Exception e) {
            logger.info("error " + e);
        }
    }

    public static String readSqlScriptFromFile(String filePath) {
        StringBuilder scriptContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                scriptContent.append(line).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scriptContent.toString();
    }
}
