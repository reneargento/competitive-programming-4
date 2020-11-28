package chapter1.section6.h.time.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 16/11/20.
 */
public class SavingDaylight {

    private static class Time {
        int hours;
        int minutes;

        public Time(int hours, int minutes) {
            this.hours = hours;
            this.minutes = minutes;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String[] input = scanner.nextLine().split(" ");
            String month = input[0];
            String day = input[1];
            String year = input[2];

            String[] startTime = input[3].split(":");
            int startHour = Integer.parseInt(startTime[0]);
            int startMinute = Integer.parseInt(startTime[1]);

            String[] endTime = input[4].split(":");
            int endHour = Integer.parseInt(endTime[0]);
            int endMinute = Integer.parseInt(endTime[1]);

            Time totalTime = getTimeDifference(startHour, startMinute, endHour, endMinute);
            System.out.printf("%s %s %s %d hours %d minutes\n", month, day, year, totalTime.hours, totalTime.minutes);
        }
    }

    private static Time getTimeDifference(int startHour, int startMinute, int endHour, int endMinute) {
        int hours;
        int minutes;

        if (startMinute <= endMinute) {
            hours = endHour - startHour;
            minutes = endMinute - startMinute;
        } else {
            hours = endHour - startHour - 1;
            minutes = 60 - startMinute + endMinute;
        }
        return new Time(hours, minutes);
    }
}
