package chapter2.section2.i.big.integer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 11/03/21.
 */
public class SimpleAddition {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        BigInteger number1 = new BigInteger(FastReader.next());
        BigInteger number2 = new BigInteger(FastReader.next());
        BigInteger result = number1.add(number2);
        System.out.println(result);
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
