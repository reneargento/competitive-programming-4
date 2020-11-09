package chapter1.section4.d.multiple.test.cases.selection;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class NumberFun {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int i = 0; i < tests; i++) {
            double number1 = scanner.nextInt();
            double number2 = scanner.nextInt();
            double result = scanner.nextInt();

            if (number1 + number2 == result
                    || number1 - number2 == result
                    || number2 - number1 == result
                    || number1 * number2 == result
                    || number1 / number2 == result
                    || number2 / number1 == result) {
                System.out.println("Possible");
            } else {
                System.out.println("Impossible");
            }
        }
    }

}
