package chapter3.section2.b.iterative.two.nested.loops;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 06/11/21.
 */
public class Peg {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String[][] board = new String[7][7];

        for (int i = 0; i < board.length; i++) {
            String boardLine = FastReader.next();
            if (i < 2 || i > 4) {
                boardLine = "  " + boardLine + "  ";
            }
            board[i] = boardLine.split("");
        }

        int validMoves = countValidMoves(board);
        outputWriter.printLine(validMoves);

        outputWriter.flush();
    }

    private static int countValidMoves(String[][] board) {
        int validMoves = 0;

        int[] neighborRows = { -1, 0, 0, 1 };
        int[] neighborColumns = { 0, -1, 1, 0 };

        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {

                if (board[row][column].equals(".")) {
                    for (int i = 0; i < neighborRows.length; i++) {
                        int neighborRow = row + neighborRows[i];
                        int neighborColumn = column + neighborColumns[i];

                        int previousNeighborRow = row + (2 * neighborRows[i]);
                        int previousNeighborColumn = column + (2 * neighborColumns[i]);

                        if (isValid(previousNeighborRow, previousNeighborColumn, board)
                                && board[neighborRow][neighborColumn].equals("o")
                                && board[previousNeighborRow][previousNeighborColumn].equals("o")) {
                            validMoves++;
                        }
                    }
                }
            }
        }
        return validMoves;
    }

    private static boolean isValid(int row, int column, String[][] board) {
        return row >= 0 && row < board.length && column >= 0 && column < board[row].length;
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
