package chapter3.section2.d.three.or.more.nested.loops.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 22/11/21.
 */
public class Misa {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int rows = FastReader.nextInt();
        int columns = FastReader.nextInt();
        String[][] church = new String[rows][columns];

        for (int row = 0; row < rows; row++) {
            church[row] = FastReader.getLine().split("");
        }

        int maxHandshakes = countMaxHandshakes(church);
        outputWriter.printLine(maxHandshakes);
        outputWriter.flush();
    }

    private static int countMaxHandshakes(String[][] church) {
        int maxHandshakes = 0;
        int[] neighborRows = { 0, 1, 1, 1 };
        int[] neighborColumns = { 1, -1, 0, 1 };
        boolean isFull = true;

        for (int row = 0; row < church.length; row++) {
            for (int column = 0; column < church[0].length; column++) {
                if (church[row][column].equals(".")) {
                    isFull = false;
                    int handshakes = countHandshakes(church, row, column, neighborRows, neighborColumns);
                    maxHandshakes = Math.max(maxHandshakes, handshakes);
                }
            }
        }

        if (isFull) {
            maxHandshakes = countHandshakes(church, -1, -1, neighborRows, neighborColumns);
        }
        return maxHandshakes;
    }

    private static int countHandshakes(String[][] church, int mirkoRow, int mirkoColumn,
                                       int[] neighborRows, int[] neighborColumns) {
        int handshakes = 0;

        for (int row = 0; row < church.length; row++) {
            for (int column = 0; column < church[0].length; column++) {
                if (church[row][column].equals("o")
                        || (row == mirkoRow && column == mirkoColumn)) {
                    for (int i = 0; i < neighborRows.length; i++) {
                        int neighborRow = row + neighborRows[i];
                        int neighborColumn = column + neighborColumns[i];

                        if (isValid(church, neighborRow, neighborColumn)
                                && (church[neighborRow][neighborColumn].equals("o")
                                || (neighborRow == mirkoRow && neighborColumn == mirkoColumn))) {
                            handshakes++;
                        }
                    }
                }
            }
        }
        return handshakes;
    }

    private static boolean isValid(String[][] church, int row, int column) {
        return row >= 0 && row < church.length && column >= 0 && column < church[0].length;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}