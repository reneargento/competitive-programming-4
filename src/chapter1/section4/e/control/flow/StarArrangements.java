package chapter1.section4.e.control.flow;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class StarArrangements {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int stars = scanner.nextInt();
        System.out.printf("%d:\n", stars);

        for (int firstRow = 2; firstRow <= stars / 2 + 1; firstRow++) {
            int remainder = stars % (2 * firstRow - 1);

            if (remainder == 0 || remainder - firstRow == 0) {
                System.out.printf("%d,%d\n", firstRow, firstRow - 1);
            }
            if (stars % firstRow == 0) {
                System.out.printf("%d,%d\n", firstRow, firstRow);
            }
        }
    }

}
