package chapter1.section4.f.function;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class OpenCreditSystem {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            int scores = scanner.nextInt();
            int highestDifference = Integer.MIN_VALUE;
            int max = Integer.MIN_VALUE;

            for (int i = 0; i < scores; i++) {
                int score = scanner.nextInt();

                if (i == 0) {
                    max = score;
                } else {
                    int difference = max - score;

                    if (difference > highestDifference) {
                        highestDifference = difference;
                    }

                    if (score > max) {
                        max = score;
                    }
                }
            }
            System.out.println(highestDifference);
        }
    }

}
