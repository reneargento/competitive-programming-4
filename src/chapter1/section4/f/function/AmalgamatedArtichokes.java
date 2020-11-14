package chapter1.section4.f.function;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class AmalgamatedArtichokes {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int p = scanner.nextInt();
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();
        int d = scanner.nextInt();

        int period = scanner.nextInt();
        double maxPrice = 0;
        double maxDecline = 0;

        for (int k = 1; k <= period; k++) {
            double price = p * (Math.sin(a * k + b) + Math.cos(c * k + d) + 2.0);

            if (k == 0) {
                maxPrice = price;
            } else {
                double decline = maxPrice - price;
                if (decline > maxDecline) {
                    maxDecline = decline;
                }

                if (price > maxPrice) {
                    maxPrice = price;
                }
            }
        }
        System.out.printf("%.6f\n", maxDecline);
    }

}
