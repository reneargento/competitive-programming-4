package chapter3.section3.c.ternary.search.and.others;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 20/04/22.
 */
public class SylvesterConstruction {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            long matrixIndex = FastReader.nextLong();
            int startColumn = FastReader.nextInt();
            int startRow = FastReader.nextInt();
            int width = FastReader.nextInt();
            int height = FastReader.nextInt();
            printPartialHadamardMatrix(matrixIndex, startColumn, startRow, width, height, outputWriter);
        }
        outputWriter.flush();
    }

    private static void printPartialHadamardMatrix(long matrixIndex, int startColumn, int startRow,
                                                   int width, int height, OutputWriter outputWriter) {
        int endRow = startRow + height;
        int endColumn = startColumn + width;
        for (int row = startRow; row < endRow; row++) {
            for (int column = startColumn; column < endColumn; column++) {
                int value = getValue(matrixIndex, row, column);
                outputWriter.print(value);
                if (column != endColumn - 1) {
                    outputWriter.print(" ");
                }
            }
            outputWriter.printLine();
        }
        outputWriter.printLine();
    }

    private static int getValue(long matrixIndex, int row, int column) {
        int value = 1;

        while (matrixIndex >= 1) {
            if (row < matrixIndex) {
                if (column >= matrixIndex) {
                    column -= matrixIndex;
                }
            } else {
                row -= matrixIndex;
                if (column >= matrixIndex) {
                    column -= matrixIndex;
                    value *= -1;
                }
            }
            matrixIndex /= 2;
        }
        return value;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
