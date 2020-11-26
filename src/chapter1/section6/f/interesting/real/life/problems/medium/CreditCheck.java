package chapter1.section6.f.interesting.real.life.problems.medium;

import java.util.Scanner;

/**
 * Created by Rene Argento on 03/11/20.
 */
public class CreditCheck {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            StringBuilder number = new StringBuilder();

            for (int i = 0; i < 4; i++) {
                number.append(scanner.next());
            }

            int sum = 0;
            int count = 0;

            for (int i = number.length() - 1; i >= 0; i--) {
                int value = Character.getNumericValue(number.charAt(i));

                if (count % 2 != 0) {
                    value *= 2;

                    if (value > 9) {
                        String valueString = String.valueOf(value);
                        value = Character.getNumericValue(valueString.charAt(0))
                                + Character.getNumericValue(valueString.charAt(1));
                    }
                }
                sum += value;
                count++;
            }

            System.out.println(sum % 10 == 0 ? "Valid" : "Invalid");
        }
    }

}
