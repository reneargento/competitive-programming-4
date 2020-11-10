package chapter1.section4.e.control.flow;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class Statistics {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int test = 1;

        while (scanner.hasNext()) {
            int numbers = scanner.nextInt();
            int min = Integer.MAX_VALUE;
            int max = Integer.MIN_VALUE;

            for (int i = 0; i < numbers; i++) {
                int number = scanner.nextInt();
                min = Math.min(number, min);
                max = Math.max(number, max);
            }
            System.out.printf("Case %d: %d %d %d\n", test, min, max, max - min);
            test++;
        }
    }

}
