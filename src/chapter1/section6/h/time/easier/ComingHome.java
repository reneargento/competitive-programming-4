package chapter1.section6.h.time.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 17/11/20.
 */
public class ComingHome {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 1; t <= tests; t++) {
            int buses = scanner.nextInt();
            String[] time = scanner.next().split(":");
            int hours = Integer.parseInt(time[0]);
            int minutes = Integer.parseInt(time[1]);
            int minTimeToGetHome = Integer.MAX_VALUE;

            for (int bus = 0; bus < buses; bus++) {
                String[] busTime = scanner.next().split(":");
                int busHours = Integer.parseInt(busTime[0]);
                int busMinutes = Integer.parseInt(busTime[1]);

                int duration = scanner.nextInt();
                int minutesDifference = getMinutesDifference(hours, minutes, busHours, busMinutes);
                minTimeToGetHome = Math.min(minTimeToGetHome, minutesDifference + duration);
            }
            System.out.printf("Case %d: %d\n", t, minTimeToGetHome);
        }
    }

    private static int getMinutesDifference(int startHour, int startMinute, int endHour, int endMinute) {
        int hoursDifference;
        int minutesDifference;

        if (startHour < endHour
                || (startHour == endHour && startMinute <= endMinute)) {
            hoursDifference = endHour - startHour;
        } else {
            hoursDifference = (24 - startHour) + endHour;
        }
        if (endMinute < startMinute) {
            hoursDifference--;
        }

        if (startMinute <= endMinute) {
            minutesDifference = endMinute - startMinute;
        } else {
            minutesDifference = (60 - startMinute) + endMinute;
        }
        return minutesDifference + hoursDifference * 60;
    }
}
