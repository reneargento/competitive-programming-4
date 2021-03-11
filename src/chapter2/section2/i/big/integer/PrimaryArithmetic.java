package chapter2.section2.i.big.integer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 10/03/21.
 */
public class PrimaryArithmetic {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        String number1 = FastReader.next();
        String number2 = FastReader.next();

        while (!number1.equals("0") || !number2.equals("0")) {
            int maxLength = Math.max(number1.length(), number2.length());
            if (number1.length() < maxLength) {
                number1 = appendZeroes(number1, maxLength);
            }
            if (number2.length() < maxLength) {
                number2 = appendZeroes(number2, maxLength);
            }

            int carryOperations = 0;
            boolean[] carried = new boolean[11];

            for (int i = 0; i < maxLength; i++) {
                int digit1 = Character.getNumericValue(number1.charAt(number1.length() - i - 1));
                int digit2 = Character.getNumericValue(number2.charAt(number2.length() - i - 1));
                int sum = digit1 + digit2;
                if (carried[i]) {
                    sum++;
                }

                if (sum >= 10) {
                    while (sum >= 10 && i < maxLength) {
                        carryOperations++;
                        carried[i + 1] = true;
                        i++;
                        if (i == maxLength) {
                            break;
                        }

                        digit1 = Character.getNumericValue(number1.charAt(number1.length() - i - 1));
                        digit2 = Character.getNumericValue(number2.charAt(number2.length() - i - 1));
                        sum = digit1 + digit2;
                        if (carried[i]) {
                            sum++;
                        }
                    }
                    i--;
                }
            }

            if (carryOperations == 0) {
                System.out.println("No carry operation.");
            } else if (carryOperations == 1) {
                System.out.println("1 carry operation.");
            } else {
                System.out.printf("%d carry operations.\n", carryOperations);
            }

            number1 = FastReader.next();
            number2 = FastReader.next();
        }
    }

    private static String appendZeroes(String number, int length) {
        StringBuilder numberBuilder = new StringBuilder(number);

        while (numberBuilder.length() < length) {
            numberBuilder.insert(0, "0");
        }
        return numberBuilder.toString();
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }
    }
}
