package chapter5.section2.b.finding.simple.formula.or.pattern.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 15/01/25.
 */
public class LimboPart2 {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int row = FastReader.nextInt() + 1;
            int column = FastReader.nextInt() + 1;
            long blockNumber = computeBlockNumber(row, column);
            outputWriter.printLine(blockNumber);
        }
        outputWriter.flush();
    }

    private static long computeBlockNumber(int row, int column) {
        int log2Row = log2Adjusted(row);
        int log2Column = log2Adjusted(column);

        if (log2Row >= log2Column) {
            long base = (int) (Math.pow(2, log2Row - 1));
            return 2 * base * base + (2 * base) * (row - base - 1) + column - 1;
        } else {
            long base = (int) (Math.pow(2, log2Column - 1));
            return base * base + base * (column - base - 1) + row - 1;
        }
    }

    // Log 2 adjusted for floating point errors
    private static int log2Adjusted(int value) {
        int log2 = (int) Math.ceil(Math.log(value) / Math.log(2));
        int power = (int) Math.pow(2, log2 - 1);
        if (power >= value) {
            log2--;
        }
        return log2;
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
