package chapter1.section4.d.multiple.test.cases.selection;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class OneTwoThree {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int test = scanner.nextInt();

        for (int i = 0; i < test; i++) {
            String number = scanner.next();

            if (number.length() == 5) {
                System.out.println(3);
            } else if ((number.charAt(0) == 'o' && number.charAt(1) == 'n')
                    || (number.charAt(0) == 'o' && number.charAt(2) == 'e')
                    || (number.charAt(1) == 'n' && number.charAt(2) == 'e')) {
                System.out.println(1);
            } else {
                System.out.println(2);
            }
        }
    }

}
