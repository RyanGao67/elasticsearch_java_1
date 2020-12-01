package com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.zone.ZoneRulesException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtils {
    private static final Pattern VALID_PATTERN = Pattern.compile("[0-9]+|[a-zA-Z]+");
    private static final Logger LOG = LoggerFactory.getLogger(DateUtils.class);

    public DateUtils() {
    }

    public static ZoneId toZoneId(String timezoneId) {
        try {
            if (timezoneId == null || timezoneId.isEmpty()) {
                timezoneId = "UTC";
            }

            ZoneId timezone;
            try {
                timezone = ZoneId.of(timezoneId);
            } catch (ZoneRulesException var3) {
                timezone = ZoneId.of((String)ZoneId.SHORT_IDS.getOrDefault(timezoneId, timezoneId));
            }

            return timezone;
        } catch (DateTimeException var4) {
            LOG.error("Cannot parse ZoneId: {}", var4.getLocalizedMessage());
            throw new DateTimeException("Invalid ID for region-based ZoneId, invalid format.");
        }
    }

    public static String parseAsDateAndFormatIfPossible(String value, ZoneId timezone) {
        try {
            return toFormattedDate(ZonedDateTime.parse(value, DateTimeFormatter.ISO_DATE_TIME).toInstant().toEpochMilli(), timezone);
        } catch (Exception var3) {
            return value;
        }
    }

    public static String toFormattedDate(long millis, ZoneId timezone) {
        return Instant.ofEpochMilli(millis).atZone(timezone).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    public static long roundToEnclosingHour(long millis) {
        ZonedDateTime date = Instant.ofEpochMilli(millis).atZone(ZoneOffset.UTC);
        if (date.getMinute() > 0) {
            date = date.truncatedTo(ChronoUnit.HOURS).plusHours(1L);
        } else {
            date = date.truncatedTo(ChronoUnit.HOURS);
        }

        return date.toInstant().toEpochMilli();
    }

    public static long atStartOfDay(long millis) {
        return Instant.ofEpochMilli(millis).atZone(ZoneOffset.UTC).toLocalDate().atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
    }

    public static long atEndOfDay(long millis) {
        return Instant.ofEpochMilli(millis).atZone(ZoneOffset.UTC).toLocalDate().atTime(LocalTime.MAX).toInstant(ZoneOffset.UTC).toEpochMilli();
    }

    public static boolean isRangeWithinSameDay(long tsMillis, long teMillis) {
        return teMillis - tsMillis < 86400000L;
    }

    public static long atStartOfHour(long millis) {
        ZonedDateTime date = Instant.ofEpochMilli(millis).atZone(ZoneOffset.UTC);
        return date.truncatedTo(ChronoUnit.HOURS).toInstant().toEpochMilli();
    }

    public static long atEndOfHour(long millis) {
        ZonedDateTime date = Instant.ofEpochMilli(millis).atZone(ZoneOffset.UTC);
        return date.truncatedTo(ChronoUnit.HOURS).plusHours(1L).minusSeconds(1L).toInstant().toEpochMilli();
    }

    public static int toSecondsFromMillis(long millis) {
        ZonedDateTime date = Instant.ofEpochMilli(millis).atZone(ZoneOffset.UTC);
        return (int)date.toEpochSecond();
    }

    public static long calculateKibanaEndTime(long timestamp, BucketSize bucketSize) {
        return timestamp + bucketSize.getSizeInSeconds() * 1000L - 1L;
    }

    public static long calculateKibanaStartTime(List<Integer> anomalyTypes, long timestamp, Double observed) {
        if (anomalyTypes.contains(4)) {
            long observedTime = observed.longValue() * 60000L;
            return timestamp - observedTime;
        } else {
            return timestamp;
        }
    }

    public static String calendarToFixed(String toParse) {
        Matcher matcher = VALID_PATTERN.matcher(toParse);
        long num = matcher.find() ? Long.parseLong(matcher.group()) : 1L;
        String units = matcher.find() ? matcher.group().trim() : "";
        byte var6 = -1;
        switch(units.hashCode()) {
            case -1074026988:
                if (units.equals("minute")) {
                    var6 = 2;
                }
                break;
            case -1068487181:
                if (units.equals("months")) {
                    var6 = 13;
                }
                break;
            case -906279820:
                if (units.equals("second")) {
                    var6 = 0;
                }
                break;
            case 77:
                if (units.equals("M")) {
                    var6 = 14;
                }
                break;
            case 100:
                if (units.equals("d")) {
                    var6 = 8;
                }
                break;
            case 104:
                if (units.equals("h")) {
                    var6 = 7;
                }
                break;
            case 109:
                if (units.equals("m")) {
                    var6 = 6;
                }
                break;
            case 119:
                if (units.equals("w")) {
                    var6 = 11;
                }
                break;
            case 121:
                if (units.equals("y")) {
                    var6 = 17;
                }
                break;
            case 99228:
                if (units.equals("day")) {
                    var6 = 4;
                }
                break;
            case 3076183:
                if (units.equals("days")) {
                    var6 = 5;
                }
                break;
            case 3645428:
                if (units.equals("week")) {
                    var6 = 9;
                }
                break;
            case 3704893:
                if (units.equals("year")) {
                    var6 = 15;
                }
                break;
            case 104080000:
                if (units.equals("month")) {
                    var6 = 12;
                }
                break;
            case 113008383:
                if (units.equals("weeks")) {
                    var6 = 10;
                }
                break;
            case 114851798:
                if (units.equals("years")) {
                    var6 = 16;
                }
                break;
            case 1064901855:
                if (units.equals("minutes")) {
                    var6 = 3;
                }
                break;
            case 1970096767:
                if (units.equals("seconds")) {
                    var6 = 1;
                }
        }

        switch(var6) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                return toParse;
            case 9:
            case 10:
            case 11:
                return String.format("%dd", num * 7L);
            case 12:
            case 13:
            case 14:
                return String.format("%dd", num * 30L);
            case 15:
            case 16:
            case 17:
                return String.format("%dd", num * 365L);
            default:
                throw new IllegalArgumentException("Unsupported unit.");
        }
    }
}

