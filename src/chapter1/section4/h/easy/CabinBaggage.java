package chapter1.section4.h.easy;

import java.util.Scanner;

/**
 * Created by Rene Argento on 16/09/20.
 */
public class CabinBaggage {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();
        int allowedBags = 0;

        for (int t = 0; t < tests; t++) {
            double length = scanner.nextDouble();
            double width = scanner.nextDouble();
            double depth = scanner.nextDouble();
            double weight = scanner.nextDouble();

            double sum = length + width + depth;

            if (((length <= 56 && width <= 45 && depth <= 25) || sum <= 125)
                    && weight <= 7) {
                System.out.println(1);
                allowedBags++;
            } else {
                System.out.println(0);
            }
        }
        System.out.println(allowedBags);
    }

}
