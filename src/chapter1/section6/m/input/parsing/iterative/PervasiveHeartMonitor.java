package chapter1.section6.m.input.parsing.iterative;

import java.util.Scanner;

/**
 * Created by Rene Argento on 06/12/20.
 */
public class PervasiveHeartMonitor {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String[] data = scanner.nextLine().split(" ");
            StringBuilder name = new StringBuilder();
            double totalHeartRateMeasurements = 0;
            double measurements = 0;

            for (String value : data) {
                if (Character.isLetter(value.charAt(0))) {
                    if (name.length() != 0) {
                        name.append(" ");
                    }
                    name.append(value);
                } else {
                    measurements++;
                    totalHeartRateMeasurements += Double.parseDouble(value);
                }
            }
            double averageHeartRate = totalHeartRateMeasurements / measurements;
            System.out.printf("%f %s\n", averageHeartRate, name);
        }
    }
}
