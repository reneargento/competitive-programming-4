package chapter1.section6.h.time.easier;

import java.util.Scanner;

 /**
 * Created by Rene Argento on 16/11/20.
 */
public class TheDecadaryWatch {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String originalTime = scanner.next();
            int hours = Integer.parseInt(originalTime.substring(0, 2));
            int minutes = Integer.parseInt(originalTime.substring(2, 4));
            int seconds = Integer.parseInt(originalTime.substring(4, 6));
            int centiseconds = Integer.parseInt(originalTime.substring(6, 8));

            double maxCentiseconds = 24 * 60 * 60 * 100;
            double maxCentisecondsDecimal = 10 * 100 * 100 * 100;
            double centisecondToDecimalCentisecond = maxCentisecondsDecimal / maxCentiseconds;

            long originalCentiseconds = getCentiseconds(hours, minutes, seconds, centiseconds);
            int decimalCentiseconds = (int) (originalCentiseconds * centisecondToDecimalCentisecond);
            System.out.printf("%07d\n", decimalCentiseconds);
        }
    }

    private static long getCentiseconds(int hours, int minutes, int seconds, int centiseconds) {
        return centiseconds + (seconds * 100) + (minutes * 60 * 100) + (hours * 60 * 60 * 100);
    }
}
