package chapter2.section2.i.big.integer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 18/03/21.
 */
public class DisastrousDoubling {

    private static final BigInteger MOD = BigInteger.valueOf(1000000007);

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int experiments = FastReader.nextInt();
        BigInteger bigInteger2 = BigInteger.valueOf(2);
        BigInteger bacteria = bigInteger2;
        boolean errorOccurred = false;

        for (int i = 0; i < experiments; i++) {
            BigInteger bacteriaUsed = new BigInteger(FastReader.next());
            bacteria = bacteria.subtract(bacteriaUsed);

            if (bacteria.compareTo(BigInteger.ZERO) < 0) {
                System.out.println("error");
                errorOccurred = true;
                break;
            }

            if (i != experiments - 1) {
                bacteria = bacteria.multiply(bigInteger2);
            }
        }

        if (!errorOccurred) {
            System.out.println(bacteria.mod(MOD));
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
