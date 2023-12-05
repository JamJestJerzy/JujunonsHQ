package dev.j3rzy.jujunonshq.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static dev.j3rzy.jujunonshq.utils.MathUtils.getSize;

public class ConsoleUtils {
    public static Logger log = LoggerFactory.getLogger("JujunonsHQ");

    public static String getFormattedDate(String format) {
        return DateTimeFormatter.ofPattern(format).format(LocalDateTime.now());
    }

    public static String replaceLast(final String text, final String regex, final String replacement) {
        return text.replaceFirst("(?s)(.*)" + regex, "$1" + replacement);
    }

    public static String getUptime() {
        final long duration = ManagementFactory.getRuntimeMXBean().getUptime();

        final long years = duration / 31104000000L;
        final long months = duration / 2592000000L % 12;
        final long days = duration / 86400000L % 30;
        final long hours = duration / 3600000L % 24;
        final long minutes = duration / 60000L % 60;
        final long seconds = duration / 1000L % 60;

        String uptime = (years == 0 ? "" : years + "y, ") + (months == 0 ? "" : months + "M, ") + (days == 0 ? "" : days + "d, ") + (hours == 0 ? "" : hours + "h, ")
                + (minutes == 0 ? "" : minutes + "m, ") + (seconds == 0 ? "" : seconds + "s, ");

        uptime = replaceLast(uptime, ", ", "");
        uptime = replaceLast(uptime, ",", " and");

        return uptime;
    }


    public static String getMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;

        return getSize((int)usedMemory);
    }

    public static String getPackageVersion(Class<?> clazz) {
        Package clazzPackage = clazz.getPackage();
        return clazzPackage.getImplementationVersion();
    }
}
