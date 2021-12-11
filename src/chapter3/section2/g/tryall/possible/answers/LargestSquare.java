package chapter3.section2.g.tryall.possible.answers;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 12/11/21.
 */
public class LargestSquare {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int rows = FastReader.nextInt();
            int columns = FastReader.nextInt();
            int queries = FastReader.nextInt();

            String[][] grid = new String[rows][columns];
            for (int i = 0; i < rows; i++) {
                grid[i] = FastReader.next().split("");
            }

            outputWriter.printLine(String.format("%d %d %d", rows, columns, queries));

            for (int q = 0; q < queries; q++) {
                int centerRow = FastReader.nextInt();
                int centerColumn = FastReader.nextInt();
                int largestSide = getLargestSide(grid, centerRow, centerColumn);
                outputWriter.printLine(largestSide);
            }
        }
        outputWriter.flush();
    }

    private static int getLargestSide(String[][] grid, int centerRow, int centerColumn) {
        int largestSide = 1;

        for (int offset = 1; true; offset++) {
            if (isValidSquare(grid, centerRow, centerColumn, offset)) {
                largestSide += 2;
            } else {
                break;
            }
        }
        return largestSide;
    }

    private static boolean isValidSquare(String[][] grid, int centerRow, int centerColumn, int offset) {
        int startRow = centerRow - offset;
        int startColumn = centerColumn - offset;
        int endRow = startRow + offset + offset;
        int endColumn = startColumn + offset + offset;

        if (startRow < 0 || startColumn < 0 || endRow >= grid.length || endColumn >= grid[endRow].length) {
            return false;
        }

        String element = grid[centerRow][centerColumn];
        for (int row = startRow; row <= endRow; row++) {
            if (!grid[row][startColumn].equals(element)
                    || !grid[row][endColumn].equals(element) ) {
                return false;
            }
        }
        for (int column = startColumn; column <= endColumn; column++) {
            if (!grid[startRow][column].equals(element)
                    || !grid[endRow][column].equals(element) ) {
                return false;
            }
        }
        return true;
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
