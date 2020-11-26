package chapter1.section6.g.interesting.real.life.problems.harder;

import java.util.Scanner;

/**
 * Created by Rene Argento on 12/11/20.
 */
public class TaxCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 1; t <= tests; t++) {
            long income = scanner.nextLong();
            long tax = computeTax(income);
            System.out.printf("Case %d: %d\n", t, tax);
        }
    }

    private static long computeTax(long income) {
        long tax = 0;
        int[] ranges = {180000, 300000, 400000, 300000};
        double[] percentages = {0, 0.1, 0.15, 0.2};

        for (int i = 0; i < ranges.length; i++) {
            if (income <= ranges[i]) {
                if (percentages[i] != 0) {
                    long possibleTax = (long) Math.ceil(tax + income * percentages[i]);
                    return Math.max(possibleTax, 2000);
                } else {
                    return 0;
                }
            }
            income -= ranges[i];
            tax += (long) Math.ceil(ranges[i] * percentages[i]);
        }
        tax += (long) Math.ceil(income * 0.25);
        return Math.max(tax, 2000);
    }

}
