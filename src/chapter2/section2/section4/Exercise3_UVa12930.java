package chapter2.section2.section4;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * Created by Rene Argento on 20/01/21.
 */
public class Exercise3_UVa12930 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int caseNumber = 1;

        while (scanner.hasNext()) {
            BigDecimal number1 = new BigDecimal(scanner.next());
            BigDecimal number2 = new BigDecimal(scanner.next());
            int comparison = number1.compareTo(number2);

            System.out.printf("Case %d: ", caseNumber);
            if (comparison > 0) {
                System.out.println("Bigger");
            } else if (comparison < 0) {
                System.out.println("Smaller");
            } else {
                System.out.println("Same");
            }
            caseNumber++;
        }
    }
}
