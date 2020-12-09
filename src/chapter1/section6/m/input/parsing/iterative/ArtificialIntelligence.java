package chapter1.section6.m.input.parsing.iterative;

import java.util.Scanner;

/**
 * Created by Rene Argento on 08/12/20.
 */
public class ArtificialIntelligence {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();
        scanner.nextLine();

        for (int t = 1; t <= tests; t++) {
            Double power = null;
            Double voltage = null;
            Double current = null;

            String problem = scanner.nextLine();
            for (int i = 0; i < problem.length(); i++) {
                if (problem.charAt(i) == '=') {
                    char dimension = problem.charAt(i - 1);
                    StringBuilder value = new StringBuilder();
                    double finalValue = 0;

                    for (int j = i + 1; j < problem.length(); j++) {
                        char symbol = problem.charAt(j);

                        if (symbol == 'W' || symbol == 'V' || symbol == 'A') {
                            finalValue = Double.parseDouble(value.toString());
                            break;
                        }

                        if (symbol == 'm' || symbol == 'k' || symbol == 'M') {
                            finalValue = Double.parseDouble(value.toString());

                            if (symbol == 'm') {
                                finalValue *= 0.001;
                            } else if (symbol == 'k') {
                                finalValue *= 1000;
                            } else {
                                finalValue *= 1000000;
                            }
                            break;
                        }
                        value.append(symbol);
                    }

                    if (dimension == 'P') {
                        power = finalValue;
                    } else if (dimension == 'U') {
                        voltage = finalValue;
                    } else {
                        current = finalValue;
                    }
                }
            }

            System.out.printf("Problem #%d\n", t);
            solveEquation(power, voltage, current);
            System.out.println();
        }
    }

    private static void solveEquation(Double power, Double voltage, Double current) {
        if (power == null) {
            power = voltage * current;
            System.out.printf("P=%.2fW\n", power);
        } else if (voltage == null) {
            voltage = power / current;
            System.out.printf("U=%.2fV\n", voltage);
        } else {
            current = power / voltage;
            System.out.printf("I=%.2fA\n", current);
        }
    }
}
