package chapter3.section2.a.precalculateable;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 19/08/21.
 */
public class EightQueensChessProblem {

    private static class QueenPositions implements Comparable<QueenPositions> {
        int[] rows;

        QueenPositions() {
            rows = new int[8];
        }

        QueenPositions(QueenPositions copyPositions) {
            rows = new int[8];
            System.arraycopy(copyPositions.rows, 0, rows, 0, rows.length);
        }

        @Override
        public int compareTo(QueenPositions other) {
            for (int i = 0; i < rows.length; i++) {
                if (rows[i] < other.rows[i]) {
                    return -1;
                } else if (rows[i] > other.rows[i]) {
                    return 1;
                }
            }
            return 0;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int queenRow = FastReader.nextInt() - 1;
            int queenColumn = FastReader.nextInt() - 1;

            List<QueenPositions> positions = placeQueens(queenRow, queenColumn);

            if (t > 0) {
                outputWriter.printLine();
            }

            outputWriter.printLine("SOLN       COLUMN");
            outputWriter.printLine(" #      1 2 3 4 5 6 7 8\n");

            for (int p = 0; p < positions.size(); p++) {
                QueenPositions position = positions.get(p);
                outputWriter.print(String.format("%2d      ", p + 1));

                for (int i = 0; i < position.rows.length; i++) {
                    outputWriter.print(position.rows[i] + 1);

                    if (i != position.rows.length - 1) {
                        outputWriter.print(" ");
                    }
                }
                outputWriter.printLine();
            }
        }
        outputWriter.flush();
    }

    private static List<QueenPositions> placeQueens(int queenRow, int queenColumn) {
        List<QueenPositions> allPositions = new ArrayList<>();
        boolean[] usedRows = new boolean[8];
        boolean[] usedLeftDiagonals = new boolean[15];
        boolean[] usedRightDiagonals = new boolean[15];

        QueenPositions queenPositions = new QueenPositions();
        queenPositions.rows[queenColumn] = queenRow;

        updateUsedTables(queenRow, queenColumn, usedRows, usedLeftDiagonals, usedRightDiagonals, true);

        int startColumn = queenColumn == 0 ? 1 : 0;
        placeQueens(startColumn, allPositions, queenPositions, usedRows, usedLeftDiagonals, usedRightDiagonals, queenColumn);

        Collections.sort(allPositions);
        return allPositions;
    }

    private static void placeQueens(int column, List<QueenPositions> allPositions,
                                    QueenPositions queenPositions, boolean[] usedRows,
                                    boolean[] usedLeftDiagonals, boolean[] usedRightDiagonals,
                                    int usedColumn) {
        if (column == 8) {
            QueenPositions queenPositionsCopy = new QueenPositions(queenPositions);
            allPositions.add(queenPositionsCopy);
            return;
        }

        for (int row = 0; row < 8; row++) {
            int leftDiagonal = getLeftDiagonal(row, column);
            int rightDiagonal = getRightDiagonal(row, column);

            if (usedRows[row] || usedLeftDiagonals[leftDiagonal] || usedRightDiagonals[rightDiagonal]) {
                continue;
            }

            updateUsedTables(row, column, usedRows, usedLeftDiagonals, usedRightDiagonals, true);
            queenPositions.rows[column] = row;

            int nextColumn = (usedColumn == column + 1) ? column + 2 : column + 1;
            placeQueens(nextColumn, allPositions, queenPositions, usedRows, usedLeftDiagonals,
                    usedRightDiagonals, usedColumn);
            updateUsedTables(row, column, usedRows, usedLeftDiagonals, usedRightDiagonals, false);
        }
    }

    private static void updateUsedTables(int row, int column, boolean[] usedRows, boolean[] usedLeftDiagonals,
                                         boolean[] usedRightDiagonals, boolean value) {
        usedRows[row] = value;
        usedLeftDiagonals[getLeftDiagonal(row, column)] = value;
        usedRightDiagonals[getRightDiagonal(row, column)] = value;
    }

    private static int getLeftDiagonal(int row, int column) {
        return (row - column) + 7;
    }

    private static int getRightDiagonal(int row, int column) {
        return row + column;
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
