package chapter1.section6.e.interesting.real.life.problems.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 02/11/20.
 */
public class HeartRate {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            double beats = scanner.nextDouble();
            double seconds = scanner.nextDouble();

            double calculatedBPM = 60 * beats / seconds;
            double difference = calculatedBPM / beats;
            double minPossibleBPM = calculatedBPM - difference;
            double maxPossibleBPM = calculatedBPM + difference;
            System.out.printf("%.4f %.4f %.4f\n", minPossibleBPM, calculatedBPM, maxPossibleBPM);
        }
    }

}
