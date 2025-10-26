package chapter5.section3.k.divisibility.test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/10/25.
 */
public class CocoaCoalition {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        long n = FastReader.nextLong();
        long m = FastReader.nextLong();
        long target = FastReader.nextLong();

        int numberOfSplits = computeNumberOfSplits(n, m, target);
        outputWriter.printLine(numberOfSplits);
        outputWriter.flush();
    }

    private static int computeNumberOfSplits(long n, long m, long target) {
        if (target % n == 0 || target % m == 0) {
            return 1;
        }

        long complement = n * m - target;
        if (isMultiple(n, m, target) || isMultiple(n, m, complement)) {
            return 2;
        }
        return 3;
    }

    private static boolean isMultiple(long n, long m, long value) {
        List<Long> factors = getFactors(value);
        for (long factor : factors) {
            if (factor <= n && value / factor <= m) {
                return true;
            }
            if (factor <= m && value / factor <= n) {
                return true;
            }
        }
        return false;
    }

    private static List<Long> getFactors(long number) {
        List<Long> factors = new ArrayList<>();
        int upperLimit = (int) Math.sqrt(number);

        for (int i = 1; i <= upperLimit; i++) {
            if (number % i == 0) {
                factors.add((long) i);

                if (i != number / i) {
                    factors.add(number / i);
                }
            }
        }
        return factors;
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

        private static long nextLong() throws IOException {
            return Long.parseLong(next());
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
