package chapter1.section6.e.interesting.real.life.problems.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 03/11/20.
 */
public class TransitWoes {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int startTime = scanner.nextInt();
        int classTime = scanner.nextInt();
        int routes = scanner.nextInt();
        int[] walkTime = new int[routes];
        int[] rideTime = new int[routes];
        int[] busIntervals = new int[routes];

        int time = startTime + scanner.nextInt();

        for (int i = 0; i < routes; i++) {
            walkTime[i] = scanner.nextInt();
        }

        for (int i = 0; i < routes; i++) {
            rideTime[i] = scanner.nextInt();
        }

        for (int i = 0; i < routes; i++) {
            busIntervals[i] = scanner.nextInt();
        }

        for (int i = 0; i < routes; i++) {
            int timeToCatchBus = getNextMultiple(time, busIntervals[i]);
            time = timeToCatchBus + rideTime[i] + walkTime[i];

            if (time > classTime) {
                break;
            }
        }
        System.out.println(time <= classTime ? "yes" : "no");
    }

    private static int getNextMultiple(int value, int multiple) {
        if (value < multiple) {
            return multiple;
        }

        int division = value / multiple;
        if (value % multiple == 0) {
            return division;
        } else {
            return division + 1;
        }
    }
}
