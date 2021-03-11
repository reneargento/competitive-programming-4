package chapter2.section2.i.big.integer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 09/03/21.
 */
public class AddingReversedNumbers {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String number1String = new StringBuilder(FastReader.next()).reverse().toString();
            String number2String = new StringBuilder(FastReader.next()).reverse().toString();

            BigInteger number1 = new BigInteger(number1String);
            BigInteger number2 = new BigInteger(number2String);
            StringBuilder reverseResult = new StringBuilder(number1.add(number2).toString());

            BigInteger finalResult = new BigInteger(reverseResult.reverse().toString());
            System.out.println(finalResult);
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

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
