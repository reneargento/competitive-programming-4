package chapter1.section6.h.time.easier;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by Rene Argento on 17/11/20.
 */
public class HoursAndMinutes {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Set<Integer> angles = getAllAngles();

        while (scanner.hasNext()) {
            int angle = scanner.nextInt();
            System.out.println(angles.contains(angle) ? "Y" : "N");
        }
    }

    private static Set<Integer> getAllAngles() {
        Set<Integer> allAngles = new HashSet<>();
        int hourPointer = 0;

        while (hourPointer <= 60) {
            for (int m = 0; m <= 60; m++) {
                allAngles.add(getAngle(hourPointer, m));

                if (m % 12 == 0) {
                    hourPointer++;
                }
            }
        }
        return allAngles;
    }

    private static int getAngle(int hoursPointer, int minutes) {
        return Math.abs(hoursPointer - minutes) * 6;
    }
}
