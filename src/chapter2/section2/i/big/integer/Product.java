package chapter2.section2.i.big.integer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 13/03/21.
 */
public class Product {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        String number1String = FastReader.getLine();

        while (number1String != null) {
            BigInteger number1 = new BigInteger(number1String);
            BigInteger number2 = new BigInteger(FastReader.next());
            System.out.println(number1.multiply(number2));

            number1String = FastReader.getLine();
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

        private static String getLine() throws IOException {
            return reader.readLine();
        }
    }
}
