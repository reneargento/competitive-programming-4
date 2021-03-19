package chapter2.section2.i.big.integer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 18/03/21.
 */
public class BeesAncestors {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        BigInteger[] ancestors = precomputeAncestors();
        int generation = FastReader.nextInt();

        while (generation != 0) {
            System.out.println(ancestors[generation]);
            generation = FastReader.nextInt();
        }
    }

    private static BigInteger[] precomputeAncestors() {
        BigInteger[] ancestors = new BigInteger[81];

        BigInteger female = BigInteger.ONE;
        BigInteger male = BigInteger.ZERO;

        for (int i = 1; i < ancestors.length; i++) {
            ancestors[i] = female.add(male);

            male = female;
            female = ancestors[i];
        }
        return ancestors;
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

        private static String getLine() throws IOException {
            return reader.readLine();
        }
    }
}
