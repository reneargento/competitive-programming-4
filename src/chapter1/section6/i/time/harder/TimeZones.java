package chapter1.section6.i.time.harder;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Rene Argento on 18/11/20.
 */
public class TimeZones {

    private static class Time {
        int hour;
        int minute;

        public Time(int hour, int minute) {
            this.hour = hour;
            this.minute = minute;
        }

        private void update(Time time) {
            if (time.hour >= 0) {
                add(time);
            } else {
                subtract(time);
            }
        }

        private void add(Time time) {
            if (minute + time.minute < 60) {
                minute += time.minute;
            } else {
                minute = time.minute - (60 - minute);

                hour++;
                hour %= 24;
            }

            if (hour + time.hour < 24) {
                hour += time.hour;
            } else {
                hour = time.hour - (24 - hour);
            }
        }

        private void subtract(Time time) {
            if (minute + time.minute >= 0) {
                minute += time.minute;
            } else {
                minute = 60 - (Math.abs(time.minute) - minute);

                hour--;
                if (hour < 0) {
                    hour = 23;
                }
            }

            if (hour + time.hour >= 0) {
                hour += time.hour;
            } else {
                hour = 24 - (Math.abs(time.hour) - hour);
            }
        }

        private String getTimeDescription() {
            if (hour == 12 && minute == 0) {
                return "noon";
            }
            if (hour == 0 && minute == 0) {
                return "midnight";
            }

            String suffix = hour < 12 ? "a.m." : "p.m.";
            if (hour == 0) {
                hour = 12;
            }

            if (hour > 12) {
                hour -= 12;
            }
            return String.format("%d:%02d %s", hour, minute, suffix);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();
        scanner.nextLine();

        Map<String, Time> timezonesToUTCMap = createTimezonesToUTCMap();

        for (int t = 0; t < tests; t++) {
            Time time;
            String timeZone1;
            String timeZone2;

            String[] data = scanner.nextLine().split(" ");
            if (data[0].equals("noon")) {
                time = getTimeFromInput("12:00", true);
            } else if (data[0].equals("midnight")) {
                time = getTimeFromInput("00:00", false);
            } else {
                time = getTimeFromInput(data[0], data[1].charAt(0) == 'p');
            }

            if (data.length == 4) {
                timeZone1 = data[2];
                timeZone2 = data[3];
            } else {
                timeZone1 = data[1];
                timeZone2 = data[2];
            }

            convertTime(time, timeZone1, timeZone2, timezonesToUTCMap);
            System.out.println(time.getTimeDescription());
        }
    }

    private static Time getTimeFromInput(String hourMinutes, boolean isPM) {
        String[] hourMinutesData = hourMinutes.split(":");
        int hours = Integer.parseInt(hourMinutesData[0]);
        int minutes = Integer.parseInt(hourMinutesData[1]);

        if (!isPM && hours == 12) {
            hours = 0;
        } else if (isPM && hours != 12) {
            hours += 12;
        }
        return new Time(hours, minutes);
    }

    private static void convertTime(Time time, String timeZone, String targetTimeZone,
                                    Map<String, Time> timezonesToUTCMap) {
        convertTimeFromTimeZoneToUTC(time, timeZone, timezonesToUTCMap);
        convertFromUTCToTimeZone(time, targetTimeZone, timezonesToUTCMap);
    }

    private static void convertTimeFromTimeZoneToUTC(Time time, String timeZone, Map<String, Time> timezonesToUTCMap) {
        Time timeDifference = timezonesToUTCMap.get(timeZone);
        time.update(new Time(timeDifference.hour * -1, timeDifference.minute * -1));
    }

    private static void convertFromUTCToTimeZone(Time time, String timeZone, Map<String, Time> timezonesToUTCMap) {
        Time timeDifference = timezonesToUTCMap.get(timeZone);
        time.update(timeDifference);
    }

    private static Map<String, Time> createTimezonesToUTCMap() {
        Map<String, Time> timezonesToUTCMap = new HashMap<>();
        timezonesToUTCMap.put("UTC", new Time(0, 0));
        timezonesToUTCMap.put("GMT", new Time(0 ,0));
        timezonesToUTCMap.put("BST", new Time(1, 0));
        timezonesToUTCMap.put("IST", new Time(1, 0));
        timezonesToUTCMap.put("WET", new Time(0, 0));
        timezonesToUTCMap.put("WEST", new Time(1, 0));
        timezonesToUTCMap.put("CET", new Time(1, 0));
        timezonesToUTCMap.put("CEST", new Time(2, 0));
        timezonesToUTCMap.put("EET", new Time(2, 0));
        timezonesToUTCMap.put("EEST", new Time(3, 0));
        timezonesToUTCMap.put("MSK", new Time(3, 0));
        timezonesToUTCMap.put("MSD", new Time(4, 0));
        timezonesToUTCMap.put("AST", new Time(-4, 0));
        timezonesToUTCMap.put("ADT", new Time(-3, 0));
        timezonesToUTCMap.put("NST", new Time(-3, -30));
        timezonesToUTCMap.put("NDT", new Time(-2, -30));
        timezonesToUTCMap.put("EST", new Time(-5, 0));
        timezonesToUTCMap.put("EDT", new Time(-4, 0));
        timezonesToUTCMap.put("CST", new Time(-6, 0));
        timezonesToUTCMap.put("CDT", new Time(-5, 0));
        timezonesToUTCMap.put("MST", new Time(-7, 0));
        timezonesToUTCMap.put("MDT", new Time(-6, 0));
        timezonesToUTCMap.put("PST", new Time(-8, 0));
        timezonesToUTCMap.put("PDT", new Time(-7, 0));
        timezonesToUTCMap.put("HST", new Time(-10, 0));
        timezonesToUTCMap.put("AKST", new Time(-9, 0));
        timezonesToUTCMap.put("AKDT", new Time(-8, 0));
        timezonesToUTCMap.put("AEST", new Time(10, 0));
        timezonesToUTCMap.put("AEDT", new Time(11, 0));
        timezonesToUTCMap.put("ACST", new Time(9, 30));
        timezonesToUTCMap.put("ACDT", new Time(10, 30));
        timezonesToUTCMap.put("AWST", new Time(8, 0));
        return timezonesToUTCMap;
    }
}
