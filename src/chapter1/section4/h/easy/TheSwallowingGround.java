package chapter1.section4.h.easy;

import java.util.Scanner;

/**
 * Created by Rene Argento on 16/09/20.
 */
public class TheSwallowingGround {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            int columns = scanner.nextInt();
            int targetGap = 0;
            boolean possibleToClose = true;

            for (int c = 0; c < columns; c++) {
                int start = scanner.nextInt();
                int end = scanner.nextInt();

                int gap = start - end + 1;

                if (c == 0) {
                    targetGap = gap;
                } else {
                    if (gap != targetGap) {
                        possibleToClose = false;
                    }
                }
            }
            System.out.println(possibleToClose ? "yes" : "no");

            if (t != tests - 1) {
                System.out.println();
            }
        }

    }

}
