package chapter3.section5.a.max.oned.twod.range.sum;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/11/22.
 */
public class TobbyAndQuery {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null && !line.isEmpty()) {
            int sequenceSize = Integer.parseInt(line);
            int[][] numbersFrequency = new int[10][sequenceSize];

            for (int i = 0; i < sequenceSize; i++) {
                int value = FastReader.nextInt();
                numbersFrequency[value][i] = 1;
            }
            computePrefixSums(numbersFrequency);

            int queries = FastReader.nextInt();
            for (int i = 0; i < queries; i++) {
                int left = FastReader.nextInt() - 1;
                int right = FastReader.nextInt() - 1;

                int differentNumbers = computeDifferentNumbers(numbersFrequency, left, right);
                outputWriter.printLine(differentNumbers);
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static void computePrefixSums(int[][] numbersFrequency) {
        for (int value = 0; value < numbersFrequency.length; value++) {
            for (int i = 1; i < numbersFrequency[value].length; i++) {
                numbersFrequency[value][i] += numbersFrequency[value][i - 1];
            }
        }
    }

    private static int computeDifferentNumbers(int[][] prefixSums, int left, int right) {
        int differentNumbers = 0;
        for (int[] prefixSum : prefixSums) {
            int sum = prefixSum[right];
            if (left > 0) {
                sum -= prefixSum[left - 1];
            }

            if (sum > 0) {
                differentNumbers++;
            }
        }
        return differentNumbers;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
