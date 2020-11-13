package chapter1.section4.e.control.flow;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class Thanos {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int planets = scanner.nextInt();

        for (int p = 0; p < planets; p++) {
            double population = scanner.nextInt();
            int growthFactor = scanner.nextInt();
            double foodProduction = scanner.nextInt();

            // Searching for: growthFactor^x > foodProduction / population
            int low = 0;
            int high = 30;
            int years = 0;
            double foodAvailable = foodProduction / population;

            while (low <= high) {
                int middle = low + (high - low) / 2;
                int consumption = (int) Math.pow(growthFactor, middle);

                if (consumption <= foodAvailable) {
                    low = middle + 1;
                } else {
                    years = middle;
                    high = middle - 1;
                }
            }
            System.out.println(years);
        }
    }

}
