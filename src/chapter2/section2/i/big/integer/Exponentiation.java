package chapter2.section2.i.big.integer;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;

/**
 * Created by Rene Argento on 12/03/21.
 */
public class Exponentiation {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            BigDecimal number = new BigDecimal(scanner.next());
            int power = Integer.parseInt(scanner.next());
            BigDecimal result = number.pow(power);

            result = result.stripTrailingZeros();
            String formattedResult = removeLeadingZeroIfNeeded(result.toPlainString());
            System.out.println(formattedResult);
        }
    }

    private static String removeLeadingZeroIfNeeded(String number) {
        if (number.startsWith("0.")) {
            return number.substring(1);
        }
        return number;
    }
}
