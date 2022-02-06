package chapter3.section2.k.recursive.backtracking.easy;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/02/22.
 */
public class Marcus {

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        char[] sequence = "IEHOVA#".toCharArray();
        int[] neighborRows = { -1, 0, 0 };
        int[] neighborColumns = { 0, -1, 1 };

        for (int t = 0; t < tests; t++) {
            Cell start = null;

            char[][] path = new char[FastReader.nextInt()][FastReader.nextInt()];
            for (int row = 0; row < path.length; row++) {
                path[row] = FastReader.getLine().toCharArray();
            }
            for (int column = 0; column < path[0].length; column++) {
                if (path[path.length - 1][column] == '@') {
                    start = new Cell(path.length - 1, column);
                }
            }
            String[] commands = new String[7];
            getCommands(path, neighborRows, neighborColumns, commands, sequence, 0, start);

            outputWriter.print(commands[0]);
            for (int i = 1; i < commands.length; i++) {
                outputWriter.print(" " + commands[i]);
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
    }

    private static void getCommands(char[][] path, int[] neighborRows, int[] neighborColumns,
                                    String[] commands, char[] sequence, int index,
                                    Cell currentCell) {
        if (path[currentCell.row][currentCell.column] == '#') {
            return;
        }

        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = currentCell.row + neighborRows[i];
            int neighborColumn = currentCell.column + neighborColumns[i];

            if (isValid(path, neighborRow, neighborColumn)
                    && path[neighborRow][neighborColumn] == sequence[index]) {
                if (i == 0) {
                    commands[index] = "forth";
                } else if (i == 1) {
                    commands[index] = "left";
                } else {
                    commands[index] = "right";
                }
                Cell nextCell = new Cell(neighborRow, neighborColumn);
                getCommands(path, neighborRows, neighborColumns, commands, sequence, index + 1,
                        nextCell);
            }
        }
    }

    private static boolean isValid(char[][] path, int row, int column) {
        return row >= 0 && row < path.length && column >= 0 && column < path[0].length;
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
