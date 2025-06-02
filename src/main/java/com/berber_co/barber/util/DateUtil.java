package com.berber_co.barber.util;

import java.time.LocalTime;

public class DateUtil {

    /**
     * Saat ve dakika değerlerinden LocalTime oluşturur.
     *
     * @param hour   Saat (0-23 arası)
     * @param minute Dakika (0-59 arası)
     * @return LocalTime objesi
     */
    public static LocalTime createLocalTime(int hour, int minute) {
        return LocalTime.of(hour, minute);
    }

    /**
     * String değerinden LocalTime oluşturur.
     * Format: "HH:mm"
     *
     * @param timeString Zaman stringi
     * @return LocalTime objesi
     */
    public static LocalTime parseLocalTime(String timeString) {
        String[] parts = timeString.split(":");
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);
        return createLocalTime(hour, minute);
    }

    /**
     * LocalTime değerini String'e dönüştürür.
     * Format: "HH:mm"
     *
     * @param time LocalTime objesi
     * @return String formatında zaman
     */
    public static String formatLocalTime(LocalTime time) {
        return time.toString();
    }
}