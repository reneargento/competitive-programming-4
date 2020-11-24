package chapter1.section6.e.interesting.real.life.problems.easier;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Rene Argento on 29/10/20.
 */
public class MolarMass {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();
        Map<Character, Double> atomWeights = new HashMap<>();
        atomWeights.put('C', 12.01);
        atomWeights.put('H', 1.008);
        atomWeights.put('O', 16.00);
        atomWeights.put('N', 14.01);

        for (int t = 0; t < tests; t++) {
            String formula = scanner.next();
            double molarMass = 0;

            for (int i = 0; i < formula.length(); i++) {
                char atom = formula.charAt(i);

                int digits = 0;
                if (i < formula.length() - 1 && Character.isDigit(formula.charAt(i + 1))) {
                    digits++;

                    if (i < formula.length() - 2 && Character.isDigit(formula.charAt(i + 2))) {
                        digits++;
                    }
                }

                String number = "1";
                if (digits != 0) {
                    number = formula.substring(i + 1, i + 1 + digits);
                }
                int multiplier = Integer.parseInt(number);
                double weight = atomWeights.get(atom);
                molarMass += weight * multiplier;

                i += digits;
            }
            System.out.printf("%.3f\n", molarMass);
        }
    }

}
