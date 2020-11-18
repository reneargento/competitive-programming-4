package chapter1.section4.i.still.easy;

import java.util.Scanner;

/**
 * Created by Rene Argento on 19/09/20.
 */
public class EarnForFuture {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 1; t <= tests; t++) {
            int cards = scanner.nextInt();
            int maxAmount = 0;

            for (int c = 0; c < cards; c++) {
                maxAmount = Math.max(maxAmount, scanner.nextInt());
            }
            System.out.printf("Case %d: %d\n", t, maxAmount);
        }
    }
}
