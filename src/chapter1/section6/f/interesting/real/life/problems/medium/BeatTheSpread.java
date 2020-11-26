package chapter1.section6.f.interesting.real.life.problems.medium;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/11/20.
 */
public class BeatTheSpread {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            double sum = scanner.nextInt();
            double difference = scanner.nextInt();

            // x + y = sum
            // x - y = difference
            // x = difference + y
            // difference + y + y = sum
            // difference + 2y = sum
            // y = (sum - difference) / 2
            double value1 = (sum - difference) / 2;
            double value2 = sum - value1;

            int value1Int = (int) value1;
            int value2Int = (int) value2;

            if (value1 < 0 || value2 < 0 || value1 != value1Int || value2 != value2Int) {
                System.out.println("impossible");
            } else {
                System.out.printf("%d %d\n", value2Int, value1Int);
            }
        }
    }
}
