package chapter4.section2.c.flood.fill.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 14/03/23.
 */
public class ThePATH {

    private static class GameResult {
        boolean whiteWins;
        boolean blackWins;
        boolean whiteWinsWithMove;
        boolean blackWinsWithMove;
    }

    private static final char VISITED = '*';

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int size = FastReader.nextInt();
        int[] neighborRows = { -1, 0, 0, 1 };
        int[] neighborColumns = { 0, -1, 1, 0 };

        while (size != 0) {
            char[][] board = new char[size][size];
            for (int row = 0; row < board.length; row++) {
                board[row] = FastReader.next().toCharArray();
            }

            GameResult gameResult = computeResult(board, neighborRows, neighborColumns);
            if (gameResult.whiteWins) {
                outputWriter.printLine("White has a winning path.");
            } else if (gameResult.blackWins) {
                outputWriter.printLine("Black has a winning path.");
            } else if (gameResult.whiteWinsWithMove) {
                outputWriter.printLine("White can win in one move.");
            } else if (gameResult.blackWinsWithMove) {
                outputWriter.printLine("Black can win in one move.");
            } else {
                outputWriter.printLine("There is no winning path.");
            }
            size = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static GameResult computeResult(char[][] board, int[] neighborRows, int[] neighborColumns) {
        GameResult gameResult = new GameResult();
        char[][] boardCopy = copyBoard(board);

        for (int row = 0; row < board.length; row++) {
            if (board[row][0] == 'W') {
                floodFillWithoutMove(board, neighborRows, neighborColumns, gameResult, 'W', row, 0);
            }
        }

        for (int column = 0; column < board[0].length; column++) {
            if (board[0][column] == 'B') {
                floodFillWithoutMove(board, neighborRows, neighborColumns, gameResult, 'B', 0, column);
            }
        }

        if (!gameResult.whiteWins && !gameResult.blackWins) {
            for (int row = 0; row < boardCopy.length; row++) {
                if (boardCopy[row][0] == 'W' || boardCopy[row][0] == 'U') {
                    boolean usedMove = boardCopy[row][0] == 'U';
                    floodFillWithMove(boardCopy, neighborRows, neighborColumns, gameResult, 'W', usedMove, row, 0);
                }
            }

            for (int column = 0; column < boardCopy[0].length; column++) {
                if (boardCopy[0][column] == 'B' || boardCopy[0][column] == 'U') {
                    boolean usedMove = boardCopy[0][column] == 'U';
                    floodFillWithMove(boardCopy, neighborRows, neighborColumns, gameResult, 'B',usedMove, 0, column);
                }
            }
        }
        return gameResult;
    }

    private static void floodFillWithoutMove(char[][] board, int[] neighborRows, int[] neighborColumns,
                                             GameResult gameResult, char player, int row, int column) {
        board[row][column] = VISITED;

        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = row + neighborRows[i];
            int neighborColumn = column + neighborColumns[i];

            if (isValid(board, neighborRow, neighborColumn) && board[neighborRow][neighborColumn] == player) {
                floodFillWithoutMove(board, neighborRows, neighborColumns, gameResult, player, neighborRow,
                        neighborColumn);
            } else {
                if (player == 'W' && neighborColumn == board[0].length) {
                    gameResult.whiteWins = true;
                } else if (player == 'B' && neighborRow == board.length) {
                    gameResult.blackWins = true;
                }
            }
        }
    }

    private static void floodFillWithMove(char[][] board, int[] neighborRows, int[] neighborColumns,
                                          GameResult gameResult, char player, boolean usedMove, int row, int column) {
        char originalValue = board[row][column];
        board[row][column] = VISITED;

        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = row + neighborRows[i];
            int neighborColumn = column + neighborColumns[i];

            if (isValid(board, neighborRow, neighborColumn)) {
                if (board[neighborRow][neighborColumn] == player) {
                    floodFillWithMove(board, neighborRows, neighborColumns, gameResult, player, usedMove, neighborRow,
                            neighborColumn);
                } else if (board[neighborRow][neighborColumn] == 'U' && !usedMove) {
                    floodFillWithMove(board, neighborRows, neighborColumns, gameResult, player, true, neighborRow,
                            neighborColumn);
                }
            } else {
                if (player == 'W' && neighborColumn == board[0].length) {
                    gameResult.whiteWinsWithMove = true;
                } else if (player == 'B' && neighborRow == board.length) {
                    gameResult.blackWinsWithMove = true;
                }
            }
        }
        board[row][column] = originalValue;
    }

    private static char[][] copyBoard(char[][] board) {
        char[][] boardCopy = new char[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, boardCopy[i], 0, board[i].length);
        }
        return boardCopy;
    }

    private static boolean isValid(char[][] board, int row, int column) {
        return row >= 0 && row < board.length && column >= 0 && column < board[0].length;
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
