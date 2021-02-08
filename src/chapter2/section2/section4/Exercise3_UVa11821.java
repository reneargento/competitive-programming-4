package chapter2.section2.section4;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * Created by Rene Argento on 20/01/21.
 */
public class Exercise3_UVa11821 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            BigDecimal sum = BigDecimal.ZERO;
            String value = scanner.next();

            while (!value.equals("0")) {
                sum = sum.add(new BigDecimal(value));
                value = scanner.next();
            }
            System.out.println(sum.stripTrailingZeros().toString());
        }
    }
}
