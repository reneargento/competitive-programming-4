package chapter3.section4.e.non.classical.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 24/07/22.
 */
public class SimpleAdjacencyMaximization {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int numberOfOnes = FastReader.nextInt();
            int numberOfZeroes = FastReader.nextInt();
            long value = computeValue(numberOfOnes, numberOfZeroes);
            outputWriter.printLine(value);
        }
        outputWriter.flush();
    }

    private static long computeValue(int numberOfOnes, int numberOfZeroes) {
        long value = 0;
        long bitValue = 1;
        String bits = buildBits(numberOfOnes, numberOfZeroes);

        for (int i = bits.length() - 1; i >= 0; i--) {
            if (bits.charAt(i) == '1') {
                value += bitValue;
            }
            bitValue *= 2;
        }
        return value;
    }

    private static String buildBits(int numberOfOnes, int numberOfZeroes) {
        StringBuilder bits = new StringBuilder();
        int totalBits = numberOfOnes + numberOfZeroes;
        int index = 0;
        boolean shouldReverse = numberOfZeroes > 0 && (numberOfOnes / (double) numberOfZeroes <= 2.0);

        for (int i = 0; i < totalBits; i++) {
            if (numberOfOnes == 0) {
                break;
            }

            if (i == 0 && numberOfOnes > 0) {
                bits.append("1");
                numberOfOnes--;
                continue;
            }

            if (index == 0 && numberOfZeroes > 0) {
                bits.append("0");
                numberOfZeroes--;
            } else {
                bits.append("1");
                numberOfOnes--;
            }
            index++;
            index = index % 3;
        }

        if (shouldReverse) {
            bits = bits.reverse();
        }
        return bits.toString();
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
