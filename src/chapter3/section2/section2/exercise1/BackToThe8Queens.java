package chapter3.section2.section2.exercise1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 21/08/21.
 */
public class BackToThe8Queens {

    private static class CompleteChessboard {
        int[] queenPositions;

        CompleteChessboard(int[] queensInRowsPerColumn) {
            queenPositions = new int[8];
            System.arraycopy(queensInRowsPerColumn, 0, queenPositions, 0, queenPositions.length);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        List<CompleteChessboard> validConfigurations = getAllValidConfigurations();
        int caseNumber = 1;

        while (line != null) {
            String[] data = line.split(" ");
            int[] queensInRowsPerColumn = new int[8];
            for (int i = 0; i < queensInRowsPerColumn.length; i++) {
                queensInRowsPerColumn[i] = Integer.parseInt(data[i]) - 1;
            }

            int minimumMoves = countMinimumMoves(queensInRowsPerColumn, validConfigurations);
            outputWriter.printLine(String.format("Case %d: %d", caseNumber, minimumMoves));

            caseNumber++;
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static List<CompleteChessboard> getAllValidConfigurations() {
        List<CompleteChessboard> validConfigurations = new ArrayList<>();
        boolean[] usedRows = new boolean[8];
        boolean[] usedLeftDiagonals = new boolean[15];
        boolean[] usedDiRightDiagonals = new boolean[15];

        int[] queensInRowsPerColumn = new int[8];
        placeQueens(0, validConfigurations, queensInRowsPerColumn, usedRows,
                usedLeftDiagonals, usedDiRightDiagonals);
        return validConfigurations;
    }

    private static void placeQueens(int column, List<CompleteChessboard> validConfigurations,
                                    int[] queensInRowsPerColumn, boolean[] usedRows,
                                    boolean[] usedLeftDiagonals, boolean[] usedRightDiagonals) {
        if (column == 8) {
            CompleteChessboard completeChessboard = new CompleteChessboard(queensInRowsPerColumn);
            validConfigurations.add(completeChessboard);
            return;
        }

        for (int row = 0; row < 8; row++) {
            int leftDiagonal = getLeftDiagonal(row, column);
            int rightDiagonal = getRightDiagonal(row, column);

            if (usedRows[row] || usedLeftDiagonals[leftDiagonal] || usedRightDiagonals[rightDiagonal]) {
                continue;
            }

            updateUsedTables(row, column, usedRows, usedLeftDiagonals, usedRightDiagonals, true);
            queensInRowsPerColumn[column] = row;
            placeQueens(column + 1, validConfigurations, queensInRowsPerColumn, usedRows,
                    usedLeftDiagonals, usedRightDiagonals);
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
        return row - column + 7;
    }

    private static int getRightDiagonal(int row, int column) {
        return row + column;
    }

    private static int countMinimumMoves(int[] queensInRowsPerColumn, List<CompleteChessboard> validConfigurations) {
        int minimumMoves = Integer.MAX_VALUE;

        for (CompleteChessboard configuration : validConfigurations) {
            int moves = countMoves(queensInRowsPerColumn, configuration.queenPositions);
            minimumMoves = Math.min(minimumMoves, moves);
        }
        return minimumMoves;
    }

    private static int countMoves(int[] queensInRowsPerColumn, int[] queenPositions) {
        int moves = 0;

        for (int i = 0; i < queensInRowsPerColumn.length; i++) {
            if (queensInRowsPerColumn[i] != queenPositions[i]) {
                moves++;
            }
        }
        return moves;
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
