package chapter4.section2.c.flood.fill.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 14/03/23.
 */
public class Islands {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int rows = FastReader.nextInt();
        int columns = FastReader.nextInt();
        char[][] image = new char[rows][columns];

        for (int row = 0; row < image.length; row++) {
            image[row] = FastReader.next().toCharArray();
        }

        int minimumIslands = computeMinimumIslands(image);
        outputWriter.printLine(minimumIslands);
        outputWriter.flush();
    }

    private static int computeMinimumIslands(char[][] image) {
        int minimumIslands = 0;
        int[] neighborRows = { -1, 0, 0, 1 };
        int[] neighborColumns = { 0, -1, 1, 0 };

        for (int row = 0; row < image.length; row++) {
            for (int column = 0; column < image[0].length; column++) {
                if (image[row][column] == 'L') {
                    minimumIslands++;
                    floodFill(image, neighborRows, neighborColumns, row, column);
                }
            }
        }
        return minimumIslands;
    }

    private static void floodFill(char[][] image, int[] neighborRows, int[] neighborColumns, int row, int column) {
        image[row][column] = '*';

        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = row + neighborRows[i];
            int neighborColumn = column + neighborColumns[i];
            if (isValid(image, neighborRow, neighborColumn)
                    && (image[neighborRow][neighborColumn] == 'L' || image[neighborRow][neighborColumn] == 'C')) {
                floodFill(image, neighborRows, neighborColumns, neighborRow, neighborColumn);
            }
        }
    }

    private static boolean isValid(char[][] image, int row, int column) {
        return row >= 0 && row < image.length && column >= 0 && column < image[0].length;
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
