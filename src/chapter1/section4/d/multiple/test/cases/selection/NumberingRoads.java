package chapter1.section4.d.multiple.test.cases.selection;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class NumberingRoads {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int test = 1;

        while (scanner.hasNext()) {
            double roads = scanner.nextInt();
            double numbers = scanner.nextInt();

            if (roads == 0 && numbers == 0) {
                break;
            }

            int suffixes = (int) Math.ceil(roads / numbers) - 1;

            System.out.print("Case " + test + ": ");
            if (suffixes <= 26) {
                System.out.println(suffixes);
            } else {
                System.out.println("impossible");
            }
            test++;
        }
    }

}
