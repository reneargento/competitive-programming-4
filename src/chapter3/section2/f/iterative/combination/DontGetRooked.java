package chapter3.section2.f.iterative.combination;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 02/12/21.
 */
public class DontGetRooked {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int dimension = FastReader.nextInt();

        while (dimension != 0) {
            char[][] board = new char[dimension][dimension];
            for (int i = 0; i < dimension; i++) {
                String row = FastReader.getLine();
                board[i] = row.toCharArray();
            }

            int maxRooks = countMaxRooks(board);
            outputWriter.printLine(maxRooks);
            dimension = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int countMaxRooks(char[][] board) {
        int maxIndex = board.length * board.length;
        return Math.max(0, countMaxRooks(board, 0, maxIndex, 0));
    }

    private static int countMaxRooks(char[][] board, int index, int maxIndex, int mask) {
        if (index == maxIndex) {
            char[][] newBoard = createNewBoard(board, mask, maxIndex);
            return countRooks(newBoard);
        }

        int row = index / board.length;
        int column = index % board.length;

        int rooksCount1 = countMaxRooks(board, index + 1, maxIndex, mask);
        int rooksCount2 = 0;

        if (board[row][column] != 'X') {
            int maskWithBitSet = mask | (1 << index);
            rooksCount2 = countMaxRooks(board, index + 1, maxIndex, maskWithBitSet);
        }
        return Math.max(rooksCount1, rooksCount2);
    }

    private static char[][] createNewBoard(char[][] board, int mask, int maxIndex) {
        char[][] newBoard = new char[board.length][board.length];

        for (int i = 0; i < maxIndex; i++) {
            int row = i / board.length;
            int column = i % board.length;

            if (board[row][column] == 'X') {
                newBoard[row][column] = 'X';
            } else if ((mask & (1 << i)) != 0) {
                newBoard[row][column] = 'r';
            }
        }
        return newBoard;
    }

    private static int countRooks(char[][] board) {
        int totalRooks = 0;

        for (char[] rows : board) {
            int rooks = 0;

            for (char cell : rows) {
                if (cell == 'r') {
                    rooks++;
                    totalRooks++;
                } else if (cell == 'X') {
                    rooks = 0;
                }

                if (rooks > 1) {
                    return -1;
                }
            }
        }

        for (int column = 0; column < board[0].length; column++) {
            int rooks = 0;

            for (char[] rows : board) {
                if (rows[column] == 'r') {
                    rooks++;
                } else if (rows[column] == 'X') {
                    rooks = 0;
                }

                if (rooks > 1) {
                    return -1;
                }
            }
        }
        return totalRooks;
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
