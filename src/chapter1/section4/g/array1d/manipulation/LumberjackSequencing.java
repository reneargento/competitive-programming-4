package chapter1.section4.g.array1d.manipulation;

import java.util.Scanner;

/**
 * Created by Rene Argento on 16/09/20.
 */
public class LumberjackSequencing {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int groups = scanner.nextInt();

        System.out.println("Lumberjacks:");

        for (int g = 0; g < groups; g++) {
            boolean increasing = false;
            boolean decreasing = false;
            int previous = -1;

            for (int i = 0; i < 10; i++) {
                int length = scanner.nextInt();

                if (i > 0) {
                    if (length > previous) {
                        increasing = true;
                    } else {
                        decreasing = true;
                    }
                }

                previous = length;
            }

            if ((increasing && !decreasing)
                    || (!increasing && decreasing)) {
                System.out.println("Ordered");
            } else {
                System.out.println("Unordered");
            }
        }
    }

}
