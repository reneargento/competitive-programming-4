package chapter3.section2.a.precalculateable;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 21/08/21.
 */
public class TheSultansSuccessors {

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
        int chessboards = FastReader.nextInt();
        List<CompleteChessboard> validConfigurations = getAllValidConfigurations();

        for (int c = 0; c < chessboards; c++) {
            int[][] chessboard = new int[8][8];

            for (int row = 0; row < chessboard.length; row++) {
                for (int column = 0; column < chessboard[0].length; column++) {
                    chessboard[row][column] = FastReader.nextInt();
                }
            }

            int highestScore = computeHighestScore(validConfigurations, chessboard);
            outputWriter.printLine(String.format("%5d", highestScore));
        }
        outputWriter.flush();
    }

    private static List<CompleteChessboard> getAllValidConfigurations() {
        List<CompleteChessboard> validConfigurations = new ArrayList<>();
        boolean[] usedRows = new boolean[8];
        boolean[] usedLeftDiagonals = new boolean[15];
        boolean[] usedRightDiagonals = new boolean[15];

        int[] queensInRowsPerColumn = new int[8];
        placeQueens(0, validConfigurations, queensInRowsPerColumn, usedRows, usedLeftDiagonals, usedRightDiagonals);
        return validConfigurations;
    }

    private static void placeQueens(int column, List<CompleteChessboard> validConfigurations,
                                   int[] queensInRowsPerColumn, boolean[] usedRows, boolean[] usedLeftDiagonals,
                                   boolean[] usedRightDiagonals) {
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

    private static int computeHighestScore(List<CompleteChessboard> validConfigurations, int[][] chessboard) {
        int highestScore = 0;

        for (CompleteChessboard configuration : validConfigurations) {
            int score = computeScore(chessboard, configuration.queenPositions);
            highestScore = Math.max(highestScore, score);
        }
        return highestScore;
    }

    private static int computeScore(int[][] chessboard, int[] queensInRowsPerColumn) {
        int score = 0;

        for (int column = 0; column < queensInRowsPerColumn.length; column++) {
            int row = queensInRowsPerColumn[column];
            score += chessboard[row][column];
        }
        return score;
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
