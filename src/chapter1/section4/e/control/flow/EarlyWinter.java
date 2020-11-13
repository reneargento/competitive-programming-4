package chapter1.section4.e.control.flow;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class EarlyWinter {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int years = scanner.nextInt();
        int daysBeforeSnow = scanner.nextInt();
        boolean neverSnowedThisEarly = true;
        boolean isConsecutive = true;
        int consecutiveYears = 0;

        for (int y = 0; y < years; y++) {
            int days = scanner.nextInt();

            if (isConsecutive && days > daysBeforeSnow) {
                consecutiveYears++;
            } else {
                isConsecutive = false;
            }

            if (days <= daysBeforeSnow) {
                neverSnowedThisEarly = false;
            }
        }

        if (neverSnowedThisEarly) {
            System.out.println("It had never snowed this early!");
        } else {
            System.out.printf("It hadn't snowed this early in %d years!\n", consecutiveYears);
        }
    }
}
