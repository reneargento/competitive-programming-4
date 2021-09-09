package chapter3.section2.a.precalculateable;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 09/09/21.
 */
public class LittleBishops {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int boardSize = FastReader.nextInt();
        int bishops = FastReader.nextInt();

        long[][] dp = new long[9][65];
        boolean[] usedLeftDiagonals = new boolean[16];
        boolean[] usedRightDiagonals = new boolean[16];

        while (boardSize != 0 || bishops != 0) {
            long bishopConfigurations = 0;
            if (dp[boardSize][bishops] != 0) {
                bishopConfigurations = dp[boardSize][bishops];
            } else {
                if (bishops <= boardSize * 2 - 1) {
                    if (bishops == 0 || (boardSize == 1 && bishops == 1)) {
                        bishopConfigurations = 1;
                    } else {
                        for (int bishopsWhite = 0; bishopsWhite <= bishops; bishopsWhite++) {
                            int bishopsBlack = bishops - bishopsWhite;
                            long bishopConfigurationsWhite = placeBishops(boardSize, bishopsWhite, 0, 0,
                                    usedLeftDiagonals, usedRightDiagonals, true);
                            long bishopConfigurationsBlack = placeBishops(boardSize, bishopsBlack, 0, 1,
                                    usedLeftDiagonals, usedRightDiagonals, false);
                            bishopConfigurations += (bishopConfigurationsWhite * bishopConfigurationsBlack);
                        }
                    }
                }
                dp[boardSize][bishops] = bishopConfigurations;
            }
            outputWriter.printLine(bishopConfigurations);

            boardSize = FastReader.nextInt();
            bishops = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long placeBishops(int boardSize, int bishops, int row, int column,
                                     boolean[] usedLeftDiagonals, boolean[] usedRightDiagonals,
                                     boolean isWhiteBoard) {
        int positionsLeft = (boardSize - 1 - column) + ((boardSize - 1 - row) * boardSize);
        if (positionsLeft < bishops) {
            return 0;
        }

        if (bishops == 0) {
            return 1;
        }
        long configurations = 0;

        for (int currentRow = row; currentRow < boardSize; currentRow++) {
            int startColumn = getStartColumn(currentRow, isWhiteBoard);

            for (int currentColumn = startColumn; currentColumn < boardSize; currentColumn += 2) {
                if (currentRow == row && currentColumn < column) {
                    continue;
                }

                int leftDiagonal = getLefDiagonal(currentRow, currentColumn, boardSize);
                int rightDiagonal = getRightDiagonal(currentRow, currentColumn);

                if (usedLeftDiagonals[leftDiagonal] || usedRightDiagonals[rightDiagonal]) {
                    continue;
                }

                usedLeftDiagonals[leftDiagonal] = true;
                usedRightDiagonals[rightDiagonal] = true;

                configurations += placeBishops(boardSize, bishops - 1, currentRow, currentColumn,
                        usedLeftDiagonals, usedRightDiagonals, isWhiteBoard);

                usedLeftDiagonals[leftDiagonal] = false;
                usedRightDiagonals[rightDiagonal] = false;
            }
        }
        return configurations;
    }

    private static int getStartColumn(int row, boolean isWhiteBoard) {
        if ((row % 2 == 0 && isWhiteBoard) || (row % 2 == 1 && !isWhiteBoard)) {
            return 0;
        }
        return 1;
    }

    private static int getLefDiagonal(int row, int column, int boardSize) {
        return row - column + boardSize;
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
