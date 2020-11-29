package chapter1.section6.i.time.harder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

/**
 * Created by Rene Argento on 19/11/20.
 */
public class Natrij {

    // Change to hh:mm:ss aa for AM/PM format
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int day = 18;
        int month = 4;
        int year = 1989;

        String[] startTimeValues = scanner.next().split(":");
        int startHour = Integer.parseInt(startTimeValues[0]);
        int startMinute = Integer.parseInt(startTimeValues[1]);
        int startSecond = Integer.parseInt(startTimeValues[2]);

        String[] endTimeValues = scanner.next().split(":");
        int endHour = Integer.parseInt(endTimeValues[0]);
        int endMinute = Integer.parseInt(endTimeValues[1]);
        int endSecond = Integer.parseInt(endTimeValues[2]);

        LocalDateTime startTime = getLocalDateTime(day, month, year, startHour, startMinute, startSecond);
        if (endHour < startHour
                || (endHour == startHour && endMinute <= startMinute)) {
            day++;
        }
        LocalDateTime endTime = getLocalDateTime(day, month, year, endHour, endMinute, endSecond);

        long secondsDifference = ChronoUnit.SECONDS.between(startTime, endTime);
        long hours = secondsDifference / 3600;
        long minutes = (secondsDifference % 3600) / 60;
        long seconds = (secondsDifference % 3600) % 60;
        System.out.printf("%02d:%02d:%02d\n", hours, minutes, seconds);
    }

    private static LocalDateTime getLocalDateTime(int day, int month, int year, int hour, int minute, int second) {
        String formattedDay = String.format("%02d", day);
        String formattedMonth = String.format("%02d", month);
        String formattedHour = String.format("%02d", hour);
        String formattedMinute = String.format("%02d", minute);
        String formattedSecond = String.format("%02d", second);

        String dateTime = String.format("%s-%s-%s %s:%s:%s", formattedDay, formattedMonth, year, formattedHour,
                formattedMinute, formattedSecond);
        return LocalDateTime.parse(dateTime, dateTimeFormatter);
    }
}
