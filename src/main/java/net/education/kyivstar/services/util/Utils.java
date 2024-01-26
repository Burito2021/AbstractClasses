package net.education.kyivstar.services.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {
    private static Logger logger = LoggerFactory.getLogger(Utils.class);

    public static String currentDateTime() {
        var dateAndTime = LocalDateTime.now();

        return dateAndTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }

    public static String readSqlScriptFromFile(String filePath) {
        StringBuilder scriptContent = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                scriptContent.append(line).append("\n");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error, config reading failure: "+e);
        }

        return scriptContent.toString();
    }

    public static String readSqlScriptFromFileStream(InputStream filePath) {
        StringBuilder scriptContent = new StringBuilder();

        if (filePath == null) {
            throw new RuntimeException();
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                scriptContent.append(line);
                scriptContent.append(System.lineSeparator());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error, config reading failure: " + e.getMessage());
        }

        return scriptContent.toString();
    }

    public static void createDirectoryIfNotExists(String directoryPath) {
        var path = Paths.get(directoryPath);

        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
                logger.info("Directory created successfully");
            } catch (IOException e) {
                throw new RuntimeException("Failed to create directory: " + e.getMessage());
            }
        } else {
            logger.info("Directory already exists");
        }
    }
}
