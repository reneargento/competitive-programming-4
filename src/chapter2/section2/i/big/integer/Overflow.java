package chapter2.section2.i.big.integer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

/**
 * Created by Rene Argento on 11/03/21.
 */
public class Overflow {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        String expression = FastReader.getLine();

        while (expression != null) {
            String[] input = expression.split(" ");
            BigInteger number1 = new BigInteger(input[0]);
            BigInteger number2 = new BigInteger(input[2]);
            BigInteger result;

            if (input[1].equals("+")) {
                result = number1.add(number2);
            } else {
                result = number1.multiply(number2);
            }

            BigInteger maxInteger = BigInteger.valueOf(Integer.MAX_VALUE);

            System.out.println(expression);
            if (number1.compareTo(maxInteger) > 0) {
                System.out.println("first number too big");
            }
            if (number2.compareTo(maxInteger) > 0) {
                System.out.println("second number too big");
            }
            if (result.compareTo(maxInteger) > 0) {
                System.out.println("result too big");
            }
            expression = FastReader.getLine();
        }
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
        }

        private static String getLine() throws IOException {
            return reader.readLine();
        }
    }
}
