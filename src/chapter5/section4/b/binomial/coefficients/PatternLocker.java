package chapter5.section4.b.binomial.coefficients;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/12/25.
 */
public class PatternLocker {

    private static final long MOD = 10000000000007L;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int gridDimension = FastReader.nextInt();
            int minimumDots = FastReader.nextInt();
            int maximumDots = FastReader.nextInt();

            long sequencesCount = countSequences(gridDimension, minimumDots, maximumDots);
            outputWriter.printLine(String.format("Case %d: %s", t, sequencesCount));
        }
        outputWriter.flush();
    }

    private static long countSequences(int gridDimension, int minimumDots, int maximumDots) {
        long sequencesCount = 0;
        int totalNumbers = gridDimension * gridDimension;
        long[] permutations = computePermutations(totalNumbers);

        for (int i = minimumDots - 1; i < maximumDots; i++) {
            sequencesCount = (sequencesCount + permutations[i]) % MOD;
        }
        return sequencesCount;
    }

    private static long[] computePermutations(int totalNumbers) {
        long[] permutations = new long[totalNumbers];
        permutations[0] = totalNumbers;

        for (int i = 1; i < permutations.length; i++) {
            permutations[i] = (permutations[i - 1] * (totalNumbers - i)) % MOD;
        }
        return permutations;
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
