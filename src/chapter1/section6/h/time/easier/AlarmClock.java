package chapter1.section6.h.time.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 17/11/20.
 */
public class AlarmClock {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int startHour = scanner.nextInt();
        int startMinute = scanner.nextInt();
        int endHour = scanner.nextInt();
        int endMinute = scanner.nextInt();

        while (startHour != 0 || startMinute != 0 || endHour != 0 || endMinute != 0) {
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
            minutesDifference += hoursDifference * 60;
            System.out.println(minutesDifference);

            startHour = scanner.nextInt();
            startMinute = scanner.nextInt();
            endHour = scanner.nextInt();
            endMinute = scanner.nextInt();
        }
    }
}
