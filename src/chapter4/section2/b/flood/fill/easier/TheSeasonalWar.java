package chapter4.section2.b.flood.fill.easier;

import java.io.*;

/**
 * Created by Rene Argento on 07/03/23.
 */
public class TheSeasonalWar {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] neighborRows = { -1, -1, -1, 0, 0, 1, 1, 1 };
        int[] neighborColumns = { -1, 0, 1, -1, 1, -1, 0, 1 };
        int imageNumber = 1;

        String line = FastReader.getLine();
        while (line != null) {
            int dimension = Integer.parseInt(line);
            char[][] image = new char[dimension][dimension];

            for (int row = 0; row < dimension; row++) {
                image[row] = FastReader.getLine().toCharArray();
            }
            int warEagles = countWarEagles(image, neighborRows, neighborColumns);
            outputWriter.printLine(String.format("Image number %d contains %d war eagles.", imageNumber, warEagles));

            imageNumber++;
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int countWarEagles(char[][] image, int[] neighborRows, int[] neighborColumns) {
        int warEagles = 0;

        for (int row = 0; row < image.length; row++) {
            for (int column = 0; column < image[0].length; column++) {
                if (image[row][column] == '1') {
                    warEagles++;
                    floodFill(image, neighborRows, neighborColumns, row, column);
                }
            }
        }
        return warEagles;
    }

    private static void floodFill(char[][] image, int[] neighborRows, int[] neighborColumns, int row, int column) {
        image[row][column] = '0';
        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = row + neighborRows[i];
            int neighborColumn = column + neighborColumns[i];
            if (isValid(image, neighborRow, neighborColumn)
                    && image[neighborRow][neighborColumn] == '1') {
                floodFill(image, neighborRows, neighborColumns, neighborRow, neighborColumn);
            }
        }
    }

    private static boolean isValid(char[][] image, int row, int column) {
        return row >= 0 && row < image.length && column >= 0 && column < image[0].length;
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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

        public void flush() {
            writer.flush();
        }
    }
}
