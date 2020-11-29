package chapter1.section6.i.time.harder;

import java.util.Scanner;

/**
 * Created by Rene Argento on 21/11/20.
 */
public class WatchingWatches {

    private static final double EPSILON = .0000001;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double secondsIn12Hours = 12 * 60 * 60;
        double secondsIn24Hours = secondsIn12Hours * 2;
        double minutesIn24Hours = 24 * 60;

        while (scanner.hasNext()) {
            String[] line = scanner.nextLine().split(" ");

            int slowK = Integer.parseInt(line[0]);
            int slowM = Integer.parseInt(line[1]);

            int difference = Math.abs(slowK - slowM);
            double days = secondsIn12Hours / difference;

            double secondsPassedInKWatch = days * (secondsIn24Hours - slowK);
            double seconds = (secondsPassedInKWatch / secondsIn24Hours) + EPSILON;

            int minutes = (int) Math.round(minutesIn24Hours * (seconds % 0.5));
            int hours = minutes / 60;
            if (hours == 0) {
                hours = 12;
            }
            minutes = minutes % 60;
            System.out.printf("%d %d %02d:%02d\n", slowK, slowM, hours, minutes);
        }
    }
}
