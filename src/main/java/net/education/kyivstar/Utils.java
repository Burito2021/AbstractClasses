package net.education.kyivstar;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

    public static String currentDateTime() {
        var dateAndTime = LocalDateTime.now();
        return dateAndTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }

}
