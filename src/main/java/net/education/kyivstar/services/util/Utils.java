package net.education.kyivstar.services.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

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
            e.printStackTrace();
        }
        return scriptContent.toString();
    }

    public static String readSqlScriptFromFileStream(InputStream filePath) {
        StringBuilder scriptContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                scriptContent.append(line);
                scriptContent.append(System.lineSeparator());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scriptContent.toString();
    }
}
