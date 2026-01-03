package chapter5.section4.d.others.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 20/12/25.
 */
public class Teams {

    private static final long MOD = 1000000007;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int players = FastReader.nextInt();
            // https://oeis.org/A001787
            long numberOfWays = (players * fastExponentiation(2, players - 1)) % MOD;
            outputWriter.printLine(String.format("Case #%d: %d", t, numberOfWays));
        }
        outputWriter.flush();
    }

    private static long fastExponentiation(long base, long exponent) {
        if (exponent == 0) {
            return 1;
        }

        long baseSquared = base * base;
        long result = fastExponentiation(baseSquared % MOD, exponent / 2);
        if (exponent % 2 == 0) {
            return result;
        } else {
            return (base * result) % MOD;
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

    private static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++) {
                if (i != 0) {
                    writer.print(' ');
                }
                writer.print(objects[i]);
            }
        }

        public void printLine(Object... objects) {
            print(objects);
            writer.println();
        }

        public void flush() {
            writer.flush();
        }
    }
}
