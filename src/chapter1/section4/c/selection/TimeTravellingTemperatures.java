package chapter1.section4.c.selection;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class TimeTravellingTemperatures {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double x = scanner.nextInt();
        double y = scanner.nextInt();

        if (x == 0 && y == 1) {
            System.out.println("ALL GOOD");
        } else if (y == 1) {
            System.out.println("IMPOSSIBLE");
        } else {
            double temperature = x / (1 - y);
            System.out.printf("%.6f\n", temperature);
        }
    }

}
