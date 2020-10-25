package chapter1.section4.d.multiple.test.cases.selection;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class PackingForHoliday {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int i = 1; i <= tests; i++) {
            System.out.print("Case " + i + ": ");
            int length = scanner.nextInt();
            int width = scanner.nextInt();
            int height = scanner.nextInt();

            if (length <= 20 && width <= 20 && height <= 20) {
                System.out.println("good");
            } else {
                System.out.println("bad");
            }
        }
    }

}
