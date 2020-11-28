package chapter1.section6.h.time.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 17/11/20.
 */
public class MirrorClock {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            String[] time = scanner.next().split(":");
            int hours = Integer.parseInt(time[0]);
            int minutes = Integer.parseInt(time[1]);

            int mirrorMinutes = (60 - minutes) % 60;
            int mirrorHours = 12 - hours;
            if (mirrorMinutes > 0) {
                mirrorHours--;
            }

            if (mirrorHours < 0) {
                mirrorHours = 11;
            } else if (mirrorHours == 0) {
                mirrorHours = 12;
            }
            System.out.printf("%02d:%02d\n", mirrorHours, mirrorMinutes);
        }
    }
}
