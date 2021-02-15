package chapter2.section2.b.oned.array.manipulation.harder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by Rene Argento on 30/01/21.
 */
public class Astro {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String firstStarFlash = scanner.next();
        String secondStarFlash = scanner.next();
        String firstStarInterval = scanner.next();
        String secondStarInterval = scanner.next();

        LocalDateTime firstTime = getLocalDateTime(firstStarFlash);
        LocalDateTime secondTime = getLocalDateTime(secondStarFlash);

        int firstMinutesInterval = intervalToMinutes(firstStarInterval);
        int secondMinutesInterval = intervalToMinutes(secondStarInterval);

        if (firstTime.isAfter(secondTime)) {
            LocalDateTime tempTime = firstTime;
            firstTime = secondTime;
            secondTime = tempTime;

            int tempInterval = firstMinutesInterval;
            firstMinutesInterval = secondMinutesInterval;
            secondMinutesInterval = tempInterval;
        }

        LocalDateTime intersection = getIntersection(firstTime, secondTime, firstMinutesInterval, secondMinutesInterval);
        if (intersection != null) {
            String dayOfWeek = intersection.getDayOfWeek().toString();
            System.out.println(dayOfWeek.charAt(0) + dayOfWeek.substring(1).toLowerCase());
            System.out.printf("%02d:%02d\n", intersection.getHour(), intersection.getMinute());
        } else {
            System.out.println("Never");
        }
    }

    private static LocalDateTime getLocalDateTime(String startTime) {
        String hours = startTime.substring(0, 2);
        String minutes = startTime.substring(3);
        String dateTime = String.format("30-01-2021 %s:%s", hours, minutes);
        return LocalDateTime.parse(dateTime, dateTimeFormatter);
    }

    private static int intervalToMinutes(String interval) {
        int hours = Integer.parseInt(interval.substring(0, 2));
        int minutes = Integer.parseInt(interval.substring(3));
        return hours * 60 + minutes;
    }

    private static LocalDateTime getIntersection(LocalDateTime firstTime, LocalDateTime secondTime,
                                                 int firstMinutesInterval, int secondMinutesInterval) {
        Set<LocalDateTime> firstTimesSet = new HashSet<>();

        for (int i = 0; i < 3600; i++) {
            firstTime = firstTime.plusMinutes(firstMinutesInterval);
            firstTimesSet.add(firstTime);
        }

        for (int i = 0; i < 3600; i++) {
            secondTime = secondTime.plusMinutes(secondMinutesInterval);

            if (firstTimesSet.contains(secondTime)) {
                return secondTime;
            }
        }
        return null;
    }
}
