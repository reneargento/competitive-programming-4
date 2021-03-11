package chapter2.section2.i.big.integer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 10/03/21.
 */
public class MultipleOf17 {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        String numberString = FastReader.next();
        BigInteger value17 = BigInteger.valueOf(17);

        while (!numberString.equals("0")) {
            int numberLength = numberString.length();

            if (numberLength == 1) {
                System.out.println("0");
            } else {
                BigInteger number = new BigInteger(numberString.substring(0, numberLength - 1));
                long digit = Character.getNumericValue(numberString.charAt(numberLength - 1));
                BigInteger valueToSubtract = BigInteger.valueOf(digit * 5);

                BigInteger result = number.subtract(valueToSubtract);
                if (result.mod(value17).equals(BigInteger.ZERO)) {
                    System.out.println("1");
                } else {
                    System.out.println("0");
                }
            }
            numberString = FastReader.next();
        }
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
