package chapter4.section2.b.flood.fill.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 09/03/23.
 */
public class ShebasAmoebas {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] neighborRows = { -1, -1, -1, 0, 0, 1, 1, 1 };
        int[] neighborColumns = { -1, 0, 1, -1, 1, -1, 0, 1 };

        int rows = FastReader.nextInt();
        int columns = FastReader.nextInt();
        char[][] dish = new char[rows][columns];

        for (int row = 0; row < dish.length; row++) {
            dish[row] = FastReader.next().toCharArray();
        }

        int loops = countLoops(dish, neighborRows, neighborColumns);
        outputWriter.printLine(loops);
        outputWriter.flush();
    }

    private static int countLoops(char[][] dish, int[] neighborRows, int[] neighborColumns) {
        int loops = 0;
        for (int row = 0; row < dish.length; row++) {
            for (int column = 0; column < dish[0].length; column++) {
                if (dish[row][column] == '#') {
                    loops++;
                    floodFill(dish, neighborRows, neighborColumns, row, column);
                }
            }
        }
        return loops;
    }

    private static void floodFill(char[][] dish, int[] neighborRows, int[] neighborColumns, int row, int column) {
        dish[row][column] = '.';
        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = row + neighborRows[i];
            int neighborColumn = column + neighborColumns[i];
            if (isValid(dish, neighborRow, neighborColumn) && dish[neighborRow][neighborColumn] == '#') {
                floodFill(dish, neighborRows, neighborColumns, neighborRow, neighborColumn);
            }
        }
    }

    private static boolean isValid(char[][] dish, int row, int column) {
        return row >= 0 && row < dish.length && column >= 0 && column < dish[0].length;
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
