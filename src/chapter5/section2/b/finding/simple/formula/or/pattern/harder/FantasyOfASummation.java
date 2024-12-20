package chapter5.section2.b.finding.simple.formula.or.pattern.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 18/12/24.
 */
public class FantasyOfASummation {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int valuesNumber = FastReader.nextInt();
            int loops = FastReader.nextInt();
            int mod = FastReader.nextInt();
            long sum = 0;

            for (int i = 0; i < valuesNumber; i++) {
                sum += FastReader.nextInt();
            }
            long result = computeResult(valuesNumber, sum, loops, mod);
            outputWriter.printLine(String.format("Case %d: %d", t, result));
        }
        outputWriter.flush();
    }

    private static long computeResult(int valuesNumber, long sum, int loops, int mod) {
        // Adapted from https://oeis.org/A001787 replacing 2 with valuesNumber terms
        long multiples = (loops * fastExponentiation(valuesNumber, loops - 1, mod)) % mod;
        return (sum * multiples) % mod;
    }

    private static long fastExponentiation(long base, int exponent, long mod) {
        if (exponent == 0) {
            return 1;
        }

        long baseSquared = base * base;
        long result = fastExponentiation(baseSquared % mod, exponent / 2, mod);
        if (exponent % 2 == 0) {
            return result;
        } else {
            return (base * result) % mod;
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
