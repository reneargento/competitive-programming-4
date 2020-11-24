package chapter1.section6.e.interesting.real.life.problems.easier;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

/**
 * Created by Rene Argento on 31/10/20.
 */
public class FinancialManagement {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 1; t <= tests; t++) {
            double sum = 0;
            for (int i = 0; i < 12; i++) {
                sum += scanner.nextDouble();
            }

            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
            double roundedValue = roundValuePrecisionDigits(sum / 12.0, 2);

            String formattedValue = numberFormat.format(roundedValue);

            // Edge case for values with 0 unit cents
            int commaIndex = formattedValue.indexOf('.');
            if (commaIndex == formattedValue.length() - 2) {
                formattedValue += "0";
            } else if (commaIndex == -1) {
                formattedValue += ".00";
            }

            System.out.printf("%d $%s\n", t, formattedValue);
        }
    }

    private static double roundValuePrecisionDigits(double value, int digits) {
        long valueToMultiply = (long) Math.pow(10, digits);
        return (double) Math.round(value * valueToMultiply) / valueToMultiply;
    }
}
