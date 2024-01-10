package net.education.kyivstar.config;

import java.io.BufferedReader;
import java.io.FileReader;

public class ScriptFileReader {
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
