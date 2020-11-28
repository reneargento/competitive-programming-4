package chapter1.section6.h.time.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 15/11/20.
 */
public class ScheduleOfAMarriedMan {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();
        scanner.nextLine();

        for (int t = 1; t <= tests; t++) {
            String[] wifeTimes = scanner.nextLine().split(" ");
            String[] wifeStartTimes = wifeTimes[0].split(":");
            int hourStart1 = Integer.parseInt(wifeStartTimes[0]);
            int minutesStart1 = Integer.parseInt(wifeStartTimes[1]);

            String[] wifeEndTimes = wifeTimes[1].split(":");
            int hourEnd1 = Integer.parseInt(wifeEndTimes[0]);
            int minutesEnd1 = Integer.parseInt(wifeEndTimes[1]);

            String[] meetingTimes = scanner.nextLine().split(" ");
            String[] meetingStartTimes = meetingTimes[0].split(":");
            int hourStart2 = Integer.parseInt(meetingStartTimes[0]);
            int minutesStart2 = Integer.parseInt(meetingStartTimes[1]);

            String[] meetingEndTimes = meetingTimes[1].split(":");
            int hourEnd2 = Integer.parseInt(meetingEndTimes[0]);
            int minutesEnd2 = Integer.parseInt(meetingEndTimes[1]);

            boolean intersect = intersect(hourStart1, minutesStart1, hourEnd1, minutesEnd1, hourStart2, minutesStart2)
                    || intersect(hourStart1, minutesStart1, hourEnd1, minutesEnd1, hourEnd2, minutesEnd2)
                    || intersect(hourStart2, minutesStart2, hourEnd2, minutesEnd2, hourStart1, minutesStart1)
                    || intersect(hourStart2, minutesStart2, hourEnd2, minutesEnd2, hourEnd1, minutesEnd1) ;
            System.out.printf("Case %d: ", t);
            System.out.println(intersect ? "Mrs Meeting" : "Hits Meeting");
        }
    }

    private static boolean intersect(int hourStart1, int minutesStart1, int hourEnd1, int minutesEnd1, int hourStart2,
                                     int minutesStart2) {
        return (hourStart2 > hourStart1 || (hourStart2 == hourStart1 && minutesStart2 >= minutesStart1))
                && (hourStart2 < hourEnd1 || (hourStart2 == hourEnd1 && minutesStart2 <= minutesEnd1));
    }
}
