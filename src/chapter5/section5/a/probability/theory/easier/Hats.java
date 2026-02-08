package chapter5.section5.a.probability.theory.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 07/02/26.
 */
public class Hats {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int tests = FastReader.nextInt();
        long[] factorials = computeFactorial();

        for (int t = 0; t < tests; t++) {
            int hats = FastReader.nextInt();
            String probability = computeProbability(factorials, hats);
            outputWriter.printLine(probability);
        }
        outputWriter.flush();
    }

    private static String computeProbability(long[] factorials, int hats) {
        long possibleCases = factorials[hats];
        long events = computeEvents(hats);
        return events + "/" + possibleCases;
    }

    private static long computeEvents(int hats) {
        if (hats <= 1) {
            return 0;
        }
        if (hats == 2) {
            return 1;
        }
        return (hats - 1) * (computeEvents(hats - 1) + computeEvents(hats - 2));
    }

    private static long[] computeFactorial() {
        long[] factorials = new long[13];
        factorials[0] = 1;
        for (int i = 1; i < 13; i++) {
            factorials[i] = factorials[i - 1] * i;
        }
        return factorials;
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
