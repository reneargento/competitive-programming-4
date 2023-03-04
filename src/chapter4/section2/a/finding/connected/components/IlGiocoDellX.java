package chapter4.section2.a.finding.connected.components;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/03/23.
 */
public class IlGiocoDellX {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int dimension = FastReader.nextInt();
        int gameID = 1;
        int[] neighborRows = { -1, -1, 0, 0, 1, 1 };
        int[] neighborColumns = { -1, 0, -1, 1, 0, 1 };

        while (dimension != 0) {
            char[][] board = new char[dimension][dimension];
            for (int row = 0; row < board.length; row++) {
                board[row] = FastReader.next().toCharArray();
            }

            char winner = computeWinner(board, neighborRows, neighborColumns);
            outputWriter.printLine(String.format("%d %c", gameID, winner));

            gameID++;
            dimension = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static char computeWinner(char[][] board, int[] neighborRows, int[] neighborColumns) {
        boolean[][] visited = new boolean[board.length][board.length];

        for (int column = 0; column < board[0].length; column++) {
            if (board[0][column] == 'b' && !visited[0][column]) {
                boolean wonGame = depthFirstSearch(board, neighborRows, neighborColumns, visited, 0, column);
                if (wonGame) {
                    return Character.toUpperCase(board[0][column]);
                }
            }
        }
        return 'W';
    }

    private static boolean depthFirstSearch(char[][] board, int[] neighborRows, int[] neighborColumns,
                                            boolean[][] visited, int row, int column) {
        boolean wonGame = false;

        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = row + neighborRows[i];
            int neighborColumn = column + neighborColumns[i];

            if (neighborRow == board.length) {
                return true;
            }

            if (isValid(board, neighborRow, neighborColumn)
                    && !visited[neighborRow][neighborColumn]
                    && board[neighborRow][neighborColumn] == board[row][column]) {
                visited[neighborRow][neighborColumn] = true;
                wonGame |= depthFirstSearch(board, neighborRows, neighborColumns, visited, neighborRow,
                        neighborColumn);
            }
        }
        return wonGame;
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
