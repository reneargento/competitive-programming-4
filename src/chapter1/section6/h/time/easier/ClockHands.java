package chapter1.section6.h.time.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 14/11/20.
 */
public class ClockHands {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String[] time = scanner.next().split(":");
            int hours = Integer.parseInt(time[0]);
            int minutes = Integer.parseInt(time[1]);

            if (hours == 0 && minutes == 0) {
                break;
            }

            double hoursAngle = (hours * 60 + minutes) * 0.5;
            double minutesAngle = 6 * minutes;
            double angle = hoursAngle - minutesAngle;

            if (angle < 0) {
                angle *= -1;
            }
            if (angle > 180) {
                angle = 360 - angle;
            }
            System.out.printf("%.3f\n", angle);
        }
    }
}
