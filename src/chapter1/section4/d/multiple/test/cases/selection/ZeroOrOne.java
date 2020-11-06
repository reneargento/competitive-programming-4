package chapter1.section4.d.multiple.test.cases.selection;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class ZeroOrOne {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            int aliceValue = scanner.nextInt();
            int betoValue = scanner.nextInt();
            int claraValue = scanner.nextInt();

            if (aliceValue != betoValue && aliceValue != claraValue) {
                System.out.println("A");
            } else if (betoValue != aliceValue && betoValue != claraValue) {
                System.out.println("B");
            } else if (claraValue != aliceValue && claraValue != betoValue) {
                System.out.println("C");
            } else {
                System.out.println("*");
            }
        }
    }

}
