package chapter2.section2.i.big.integer;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * Created by Rene Argento on 20/01/21.
 */
public class BigBigRealNumbers {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            BigDecimal number1 = new BigDecimal(scanner.next());
            BigDecimal number2 = new BigDecimal(scanner.next());
            BigDecimal result = number1.add(number2);
            System.out.println(trimTrailingZeroes(result));
        }
    }

    private static String trimTrailingZeroes(BigDecimal result) {
        String value = result.toString();
        int point = value.indexOf(".");
        int endIndex = point + 2;

        for (int i = value.length() - 1; i > point; i--) {
            if (value.charAt(i) != '0') {
                endIndex = i + 1;
                break;
            }
        }
        return value.substring(0, endIndex);
    }
}