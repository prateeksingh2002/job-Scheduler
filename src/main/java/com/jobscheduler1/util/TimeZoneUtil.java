package com.jobscheduler1.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeZoneUtil {
    public static LocalDateTime convertToUTC(LocalDateTime dateTime, String timeZone) {
        if (timeZone == null || timeZone.isEmpty()) {
            return dateTime;
        }
        ZoneId zoneId = ZoneId.of(timeZone);
        ZonedDateTime zonedDateTime = dateTime.atZone(zoneId);
        return zonedDateTime.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
    }

    public static LocalDateTime convertFromUTC(LocalDateTime dateTime, String timeZone) {
        if (timeZone == null || timeZone.isEmpty()) {
            return dateTime;
        }
        ZoneId zoneId = ZoneId.of(timeZone);
        ZonedDateTime zonedDateTime = dateTime.atZone(ZoneId.of("UTC"));
        return zonedDateTime.withZoneSameInstant(zoneId).toLocalDateTime();
    }
}