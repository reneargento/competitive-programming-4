package chapter1.section6.e.interesting.real.life.problems.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 31/10/20.
 */
public class AChangeInThermalUnit {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        double zeroDegreesFahrenheitInCelsius = (-32 * 5.0) / 9.0;
        double oneDegreeFahrenheitInCelsius = (-31 * 5.0) / 9.0;
        double unitDifference = oneDegreeFahrenheitInCelsius - zeroDegreesFahrenheitInCelsius;


        for (int t = 1; t <= tests; t++) {
            int initialTemperature = scanner.nextInt();
            int increase = scanner.nextInt();
            double finalTemperature = initialTemperature + (increase * unitDifference);
            System.out.printf("Case %d: %.2f\n", t, finalTemperature);
        }
    }
}
