package chapter1.section4.b.repetition;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class QualityAdjustedLifeYear {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int periods = scanner.nextInt();
        double sum = 0;

        for (int i = 0; i < periods; i++) {
            double qualityOfLife = scanner.nextDouble();
            double years = scanner.nextDouble();
            sum += qualityOfLife * years;
        }
        System.out.printf("%.3f", sum);
    }

}
