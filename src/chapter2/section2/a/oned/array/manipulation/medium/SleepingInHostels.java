package chapter2.section2.a.oned.array.manipulation.medium;

import java.util.Scanner;

/**
 * Created by Rene Argento on 15/01/21.
 */
public class SleepingInHostels {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String beds = scanner.nextLine();
            int firstOccupiedBed = -1;
            int lastOccupiedBed = -1;
            int maxDistance = 0;

            for (int i = 0; i < beds.length(); i++) {
                if (beds.charAt(i) == 'X') {
                    if (firstOccupiedBed == -1) {
                        firstOccupiedBed = i;
                        maxDistance = Math.max(maxDistance, firstOccupiedBed - 1);
                    } else {
                        int distance = (i - lastOccupiedBed - 2) / 2;
                        maxDistance = Math.max(maxDistance, distance);
                    }
                    lastOccupiedBed = i;
                }
            }

            int distanceFromLastBed = beds.length() - 2 - lastOccupiedBed;
            maxDistance = Math.max(maxDistance, distanceFromLastBed);
            System.out.println(maxDistance);
        }
    }
}
