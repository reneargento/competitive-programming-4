package chapter1.section4.i.still.easy;

import java.util.Scanner;

/**
 * Created by Rene Argento on 22/09/20.
 */
public class Sok {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double orangeLiters = scanner.nextDouble();
        double appleLiters = scanner.nextDouble();
        double pineappleLiters = scanner.nextDouble();

        double orangeRatio = scanner.nextDouble();
        double appleRatio = scanner.nextDouble();
        double pineappleRatio = scanner.nextDouble();

        while (true) {
            double orangeRatioUsed = orangeLiters / orangeRatio;
            double appleRatioUsed = appleLiters / appleRatio;
            double pineappleRatioUsed = pineappleLiters / pineappleRatio;

            if (orangeLiters - orangeRatio >= 0
                    && appleLiters - appleRatio >= 0
                    && pineappleLiters - pineappleRatio >= 0) {
                orangeLiters -= orangeRatio;
                appleLiters -= appleRatio;
                pineappleLiters -= pineappleRatio;
            } else if (orangeRatioUsed == appleRatioUsed
                    && orangeRatioUsed == pineappleRatioUsed) {
                orangeLiters = 0;
                appleLiters = 0;
                pineappleLiters = 0;
                break;
            } else {
                if (orangeRatioUsed < appleRatioUsed && orangeRatioUsed < pineappleRatioUsed) {
                    orangeLiters = 0;
                    appleLiters -= appleRatio * orangeRatioUsed;
                    pineappleLiters -= pineappleRatio * orangeRatioUsed;
                } else if (appleRatioUsed < orangeRatioUsed && appleRatioUsed < pineappleRatioUsed) {
                    appleLiters = 0;
                    orangeLiters -= orangeRatio * appleRatioUsed;
                    pineappleLiters -= pineappleRatio * appleRatioUsed;
                } else {
                    pineappleLiters = 0;
                    orangeLiters -= orangeRatio * pineappleRatioUsed;
                    appleLiters -= appleRatio * pineappleRatioUsed;
                }
                break;
            }
        }
        System.out.printf("%6f %6f %6f\n", orangeLiters, appleLiters, pineappleLiters);
    }

}
