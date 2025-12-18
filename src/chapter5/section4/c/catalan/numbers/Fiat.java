package chapter5.section4.c.catalan.numbers;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 15/12/25.
 */
public class Fiat {

    private static final int MOD = 1000000007;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int steps = FastReader.nextInt();
        long[] catalanNumbers = catalanNumbers();
        outputWriter.printLine(catalanNumbers[steps]);
        outputWriter.flush();
    }

    private static long[] catalanNumbers() {
        long[] catalanNumbers = new long[100001];
        catalanNumbers[0] = 1;

        for (int i = 0; i < catalanNumbers.length - 1; i++) {
            catalanNumbers[i + 1] = ((4L * i + 2) % MOD
                    * catalanNumbers[i] % MOD
                    * modPowFermat(i + 2, MOD)) % MOD;
        }
        return catalanNumbers;
    }

    private static long modPowFermat(long value, int prime) {
        return modPow(value, prime - 2, prime);
    }

    private static long modPow(long base, long power, long mod) {
        if (power == 0) {
            return 1;
        }
        long result = modPow(base, power / 2, mod);
        result = mod(result * result, mod);
        if (power % 2 == 1) {
            result = mod(result * base, mod);
        }
        return result;
    }

    private static long mod(long number, long mod) {
        return ((number % mod) + mod) % mod;
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
