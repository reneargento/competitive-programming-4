package chapter1.section4.d.multiple.test.cases.selection;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class Oddities {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int i = 0; i < tests; i++) {
            int number = scanner.nextInt();
            if (number % 2 == 0) {
                System.out.println(number + " is even");
            } else {
                System.out.println(number + " is odd");
            }
        }
    }

}
