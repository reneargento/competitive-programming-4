package chapter5.section6;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 21/03/26.
 */
public class FriezePatterns {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int rows = FastReader.nextInt();
        while (rows != 0) {
            int targetRow = FastReader.nextInt() - 1;
            int targetColumn = (FastReader.nextInt() - 1) % (rows + 1);
            int[] firstElements = new int[rows];
            for (int i = 0; i < rows; i++) {
                firstElements[i] = FastReader.nextInt();
            }

            long friezePatternValue = computeFriezePatternValue(firstElements, targetRow, targetColumn);
            outputWriter.printLine(friezePatternValue);
            rows = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long computeFriezePatternValue(int[] firstElements, int targetRow, int targetColumn) {
        long[][] values = new long[firstElements.length][targetColumn + 1];
        Arrays.fill(values[0], 1);
        Arrays.fill(values[values.length - 1], 1);

        for (int row = 0; row < firstElements.length; row++) {
            values[row][0] = firstElements[row];
        }

        for (int column = 1; column < values[0].length; column++) {
            for (int row = 1; row < values.length - 1; row++) {
                values[row][column] = (values[row - 1][column] * values[row + 1][column - 1] + 1)
                        / values[row][column - 1];
            }
        }
        return values[targetRow][targetColumn];
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
