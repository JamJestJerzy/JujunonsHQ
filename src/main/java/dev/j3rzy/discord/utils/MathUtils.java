package dev.j3rzy.discord.utils;

public class MathUtils {
    public static String getSize(int bytes) {
        double kilobytes = bytes / 1024.0;
        double megabytes = bytes / (1024.0 * 1024);
        double gigabytes = bytes / (1024.0 * 1024 * 1024);
        double terabytes = bytes / (1024.0 * 1024 * 1024 * 1024);

        if (terabytes > 1) {
            return String.format("%.2f TB", terabytes);
        } else if (gigabytes > 1) {
            return String.format("%.2f GB", gigabytes);
        } else if (megabytes > 1) {
            return String.format("%.2f MB", megabytes);
        } else if (kilobytes > 1) {
            return String.format("%.2f KB", kilobytes);
        } else {
            return String.format("%d bytes", bytes);
        }
    }
}
