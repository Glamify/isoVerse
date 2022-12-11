package net.isoverse.isocore.utills;

import org.bukkit.Bukkit;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {

    public static String format(long time) {
        time = time / 1000;
        int remainder = (int) (time % 86400);

        int days = (int) (time / 86400);
        int hours = remainder / 3600;
        int minutes	= (remainder / 60) - (hours * 60);
        int seconds	= (remainder % 3600) - (minutes * 60);

        String fDays = (days > 0 	? " " + days + " day" 		+ (days > 1 ? "s" : "") 	: "");
        String fHours = (hours > 0 	? " " + hours + " hour" 	+ (hours > 1 ? "s" : "") 	: "");
        String fMinutes = (minutes > 0 	? " " + minutes + " minute"	+ (minutes > 1 ? "s" : "") 	: "");
        String fSeconds = (seconds > 0 	? " " + seconds + " second"	+ (seconds > 1 ? "s" : "") 	: "");
        if (days > 0) return new StringBuilder().append(fDays).append(fHours).toString();
        if (hours > 0) return new StringBuilder().append(fHours).append(fMinutes).toString();
        if (minutes > 0) return new StringBuilder().append(fMinutes).append(fSeconds).toString();
        if (seconds > 0) return new StringBuilder().append(fSeconds).toString();
        return new StringBuilder().append(fDays).append(fHours).append(fMinutes).append(fSeconds).toString();
    }


    public static String timestamp(long time) {
        DateFormat simple = new SimpleDateFormat("MM/dd/yyyy hh:mm aaa");
        Date result = new Date(time);

        return simple.format(result);
    }

    public static String left(Long time) {
        return Time.format(time - System.currentTimeMillis());
    }

    public static long toMilliSec(String type) {
        String[] typelist = type.toLowerCase().split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
        long i = Long.parseLong(typelist[0]);
        switch (typelist[1]) {
            case "s":
                return i * 1000;
            case "m":
                return i * 1000 * 60;
            case "h":
                return i * 1000 * 60 * 60;
            case "d":
                return i * 1000 * 60 * 60 * 24;
            case "w":
                return i * 1000 * 60 * 60 * 24 * 7;
            case "mo":
                return i * 1000 * 60 * 60 * 24 * 30;
            case "yr":
                return i * 1000 * 60 * 60 * 24 * 30 * 12;
            default:
                return -1;
        }
    }
}
