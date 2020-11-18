package chapter1.section4.i.still.easy;

import java.util.Scanner;

/**
 * Created by Rene Argento on 19/09/20.
 */
public class DAsInDaedalus {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] points = { 1, 10, 100, 1000, 10000 };

        while (scanner.hasNext()) {
            int players = scanner.nextInt();
            int rounds = scanner.nextInt();
            long totalExtraPoints = 0;

            for (int r = 0; r < rounds; r++) {
                int budget = scanner.nextInt();
                int daedalusPoints = scanner.nextInt();
                long totalPointsWithoutDaedalus = 0;
                long extraPoints = 0;

                for (int p = 1; p < players; p++) {
                    totalPointsWithoutDaedalus += scanner.nextInt();
                }

                if (totalPointsWithoutDaedalus + daedalusPoints > budget) {
                    daedalusPoints = 0;
                }

                for (int point : points) {
                    long newScore = totalPointsWithoutDaedalus + point;
                    if (newScore > totalPointsWithoutDaedalus + daedalusPoints
                            && newScore <= budget) {
                        extraPoints = point - daedalusPoints;
                    }
                }
                totalExtraPoints += extraPoints;
            }
            System.out.println(totalExtraPoints);
        }
    }

}
