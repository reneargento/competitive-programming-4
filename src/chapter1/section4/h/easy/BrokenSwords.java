package chapter1.section4.h.easy;

import java.util.Scanner;

/**
 * Created by Rene Argento on 16/09/20.
 */
public class BrokenSwords {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int swords = scanner.nextInt();
        int[] totals = new int[4];

        for (int i = 0; i < swords; i++) {
            String count = scanner.next();

            if (Character.getNumericValue(count.charAt(0)) == 0) {
                totals[0]++;
            }
            if (Character.getNumericValue(count.charAt(1)) == 0) {
                totals[1]++;
            }
            if (Character.getNumericValue(count.charAt(2)) == 0) {
                totals[2]++;
            }
            if (Character.getNumericValue(count.charAt(3)) == 0) {
                totals[3]++;
            }
        }

        int usedTopBottom = (totals[0] + totals[1]) / 2;
        int usedLeftRight = (totals[2] + totals[3]) / 2;
        int swordsPossible = Math.min(usedTopBottom, usedLeftRight);

        int totalTopBottom = totals[0] + totals[1] - (swordsPossible * 2);
        int totalLeftRight = totals[2] + totals[3] - (swordsPossible * 2);

        System.out.printf("%d %d %d\n", swordsPossible, totalTopBottom, totalLeftRight);
    }

}
