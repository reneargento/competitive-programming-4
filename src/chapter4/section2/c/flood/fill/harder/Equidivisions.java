package chapter4.section2.c.flood.fill.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 19/03/23.
 */
public class Equidivisions {

    private static final int VISITED = -1;
    private static final int[] neighborRows = { -1, 0, 0, 1 };
    private static final int[] neighborColumns = { 0, -1, 1, 0 };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int dimension = FastReader.nextInt();

        while (dimension != 0) {
            int[][] array = new int[dimension][dimension];

            for (int region = 1; region < dimension; region++) {
                String[] data = FastReader.getLine().split(" ");

                for (int i = 0; i < data.length; i += 2) {
                    int row = Integer.parseInt(data[i]) - 1;
                    int column = Integer.parseInt(data[i + 1]) - 1;
                    array[row][column] = region;
                }
            }

            boolean isEquidivisionGood = isEquidivisionGood(array);
            outputWriter.printLine(isEquidivisionGood ? "good" : "wrong");
            dimension = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static boolean isEquidivisionGood(int[][] array) {
        boolean[] regionsChecked = new boolean[array.length];

        for (int row = 0; row < array.length; row++) {
            for (int column = 0; column < array[0].length; column++) {
                if (array[row][column] != VISITED) {
                    int section = array[row][column];
                    if (regionsChecked[section]) {
                        return false;
                    }

                    floodFill(array, section, row, column);
                    regionsChecked[section] = true;
                }
            }
        }
        return true;
    }

    private static void floodFill(int[][] array, int section, int row, int column) {
        array[row][column] = VISITED;

        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = row + neighborRows[i];
            int neighborColumn = column + neighborColumns[i];

            if (isValid(array, neighborRow, neighborColumn)
                    && array[neighborRow][neighborColumn] == section) {
                floodFill(array, section, neighborRow, neighborColumn);
            }
        }
    }

    private static boolean isValid(int[][] array, int row, int column) {
        return row >= 0 && row < array.length && column >= 0 && column < array[0].length;
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

        public void flush() {
            writer.flush();
        }
    }
}
