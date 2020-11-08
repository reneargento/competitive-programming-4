package chapter1.section4.d.multiple.test.cases.selection;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class NastyHacks {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int i = 0; i < tests; i++) {
            int doNotAdvertise = scanner.nextInt();
            int advertise = scanner.nextInt();
            int costOfAdvertise = scanner.nextInt();

            if (doNotAdvertise > advertise - costOfAdvertise) {
                System.out.println("do not advertise");
            } else if (doNotAdvertise < advertise - costOfAdvertise) {
                System.out.println("advertise");
            } else {
                System.out.println("does not matter");
            }
        }
    }

}
