package chapter1.section4.d.multiple.test.cases.selection;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class RelationalOperators {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int i = 0; i < tests; i++) {
            int number1 = scanner.nextInt();
            int number2 = scanner.nextInt();

            if (number1 < number2) {
                System.out.println("<");
            } else if (number1 > number2) {
                System.out.println(">");
            } else {
                System.out.println("=");
            }
        }
    }

}
