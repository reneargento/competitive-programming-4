package chapter4.section2.b.flood.fill.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 11/03/23.
 */
public class CountingStarsUVa {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int rows = FastReader.nextInt();
        int columns = FastReader.nextInt();
        int[] neighborRows = { -1, -1, -1, 0, 0, 1, 1, 1 };
        int[] neighborColumns = { -1, 0, 1, -1, 1, -1, 0, 1 };

        while (rows != 0 || columns != 0) {
            char[][] sky = new char[rows][columns];
            for (int row = 0; row < sky.length; row++) {
                sky[row] = FastReader.next().toCharArray();
            }
            int stars = countStars(sky, neighborRows, neighborColumns);
            outputWriter.printLine(stars);

            rows = FastReader.nextInt();
            columns = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int countStars(char[][] sky, int[] neighborRows, int[] neighborColumns) {
        int stars = 0;

        for (int row = 0; row < sky.length; row++) {
            for (int column = 0; column < sky[0].length; column++) {
                if (sky[row][column] == '*') {
                    int blackPixels = countBlackPixels(sky, neighborRows, neighborColumns, row, column);
                    if (blackPixels == 1) {
                        stars++;
                    }
                }
            }
        }
        return stars;
    }

    private static int countBlackPixels(char[][] sky, int[] neighborRows, int[] neighborColumns, int row, int column) {
        int blackPixels = 1;
        sky[row][column] = '.';

        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = row + neighborRows[i];
            int neighborColumn = column + neighborColumns[i];
            if (isValid(sky, neighborRow, neighborColumn) && sky[neighborRow][neighborColumn] == '*') {
                blackPixels += countBlackPixels(sky, neighborRows, neighborColumns, neighborRow, neighborColumn);
            }
        }
        return blackPixels;
    }

    private static boolean isValid(char[][] sky, int row, int column) {
        return row >= 0 && row < sky.length && column >= 0 && column < sky[0].length;
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
