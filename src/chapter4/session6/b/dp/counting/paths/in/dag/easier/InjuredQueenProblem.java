package chapter4.session6.b.dp.counting.paths.in.dag.easier;

import java.io.*;
import java.util.Arrays;

/**
 * Created by Rene Argento on 21/05/24.
 */
public class InjuredQueenProblem {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String boardStatus = FastReader.getLine();
        while (boardStatus != null) {
            long possibleArrangements = countPossibleArrangements(boardStatus);
            outputWriter.printLine(possibleArrangements);

            boardStatus = FastReader.getLine();
            while (boardStatus != null && boardStatus.isEmpty()) {
                boardStatus = FastReader.getLine();
            }
        }
        outputWriter.flush();
    }

    private static long countPossibleArrangements(String boardStatus) {
        int boardDimension = boardStatus.length();
        long[][] dp = new long[boardDimension][boardDimension];
        for (long[] values : dp) {
            Arrays.fill(values, -1);
        }
        return countPossibleArrangements(boardStatus, dp, 0, 0);
    }

    private static long countPossibleArrangements(String boardStatus, long[][] dp, int row, int column) {
        int boardDimension = boardStatus.length();
        if (column == boardDimension) {
            return 1;
        }
        if (dp[row][column] != -1) {
            return dp[row][column];
        }

        long arrangements = 0;

        char symbol = boardStatus.charAt(column);
        if (symbol != '?') {
            int placementRow = getRowFromStatus(symbol);
            if (canPlaceQueen(boardStatus, row, placementRow, column)) {
                arrangements = countPossibleArrangements(boardStatus, dp, placementRow, column + 1);
            }
        } else {
            for (int nextRow = 0; nextRow < boardDimension; nextRow++) {
                if (canPlaceQueen(boardStatus, row, nextRow, column)) {
                    arrangements += countPossibleArrangements(boardStatus, dp, nextRow, column + 1);
                }
            }
        }
        dp[row][column] = arrangements;
        return dp[row][column];
    }

    private static boolean canPlaceQueen(String boardStatus, int currentRow, int nextRow, int column) {
        if (column == 0 || column == boardStatus.length()) {
            return true;
        }

        char symbol = boardStatus.charAt(column);
        if (symbol != '?') {
            int placementRow = getRowFromStatus(symbol);
            int rowDifference = Math.abs(placementRow - currentRow);
            return placementRow == nextRow && rowDifference >= 2;
        }

        int rowDifference = Math.abs(nextRow - currentRow);
        return rowDifference >= 2;
    }

    private static int getRowFromStatus(char symbol) {
        int row;
        if (Character.isDigit(symbol)) {
            row = Character.getNumericValue(symbol) - 1;
        } else {
            row = 9 + (symbol - 'A');
        }
        return row;
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
