package chapter1.section4.j.medium;

import java.util.Scanner;

/**
 * Created by Rene Argento on 24/09/20.
 */
public class TheSnail {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int wellHeight = scanner.nextInt();
        double climbDistance = scanner.nextInt();
        int fallDistance = scanner.nextInt();
        double fatigueFactor = (scanner.nextDouble() * 0.01) * climbDistance;

        while (wellHeight != 0) {
            double distanceClimbed = 0;
            int day = 1;
            boolean success = true;

            while (true) {
                distanceClimbed += climbDistance;

                if (distanceClimbed > wellHeight) {
                    break;
                }

                distanceClimbed -= fallDistance;
                climbDistance -= fatigueFactor;
                climbDistance = Math.max(climbDistance, 0);

                if (distanceClimbed < 0) {
                    success = false;
                    break;
                }

                day++;
            }

            if (success) {
                System.out.print("success");
            } else {
                System.out.print("failure");
            }
            System.out.printf(" on day %d\n", day);

            wellHeight = scanner.nextInt();
            climbDistance = scanner.nextInt();
            fallDistance = scanner.nextInt();
            fatigueFactor = (scanner.nextDouble() * 0.01) * climbDistance;
        }

    }

}
