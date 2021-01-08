package chapter2.section2.section1;

import java.util.Arrays;

/**
 * Created by Rene Argento on 29/12/20.
 */
public class Exercise1_2 {

    private static class Fraction {
        double numerator;
        double denominator;

        public Fraction(double numerator, double denominator) {
            this.numerator = numerator;
            this.denominator = denominator;
        }
    }

    public static void main(String[] args) {
        Fraction fraction1 = new Fraction(2, 4);
        Fraction fraction2 = new Fraction(1, 2);
        Fraction fraction3 = new Fraction(10, 1);
        Fraction[] values = { fraction1, fraction2, fraction3 };
        sort(values);

        for (Fraction fraction : values) {
            System.out.printf("%.3f / %.3f\n", fraction.numerator, fraction.denominator);
        }
    }

    private static void sort(Fraction[] values) {
        Arrays.sort(values, (fraction1, fraction2) -> {
            double result1 = fraction1.numerator / fraction1.denominator;
            double result2 = fraction2.numerator / fraction2.denominator;

            if (result1 != result2) {
                return Double.compare(result1, result2);
            }
            return Double.compare(fraction1.denominator, fraction2.denominator);
        });
    }
}
