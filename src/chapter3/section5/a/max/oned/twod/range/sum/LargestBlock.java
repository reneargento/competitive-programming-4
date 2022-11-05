package chapter3.section5.a.max.oned.twod.range.sum;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 21/10/22.
 */
public class LargestBlock {

    private static final int OCCUPIED = -100;
    private static final int FREE = 1;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int boardSize = FastReader.nextInt();
            int[][] board = new int[boardSize][boardSize];
            int blocks = FastReader.nextInt();

            for (int i = 0; i < blocks; i++) {
                int startRow = FastReader.nextInt() - 1;
                int startColumn = FastReader.nextInt() - 1;
                int endRow = FastReader.nextInt() - 1;
                int endColumn = FastReader.nextInt() - 1;

                for (int row = startRow; row <= endRow; row++) {
                    for (int column = startColumn; column <= endColumn; column++) {
                        board[row][column] = OCCUPIED;
                    }
                }
            }

            fillFreeValues(board);
            int maxFreeArea = computeMaxFreeArea(board);
            outputWriter.printLine(maxFreeArea);
        }
        outputWriter.flush();
    }

    private static void fillFreeValues(int[][] board) {
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                if (board[row][column] != OCCUPIED) {
                    board[row][column] = FREE;
                }
            }
        }
    }

    private static int computeMaxFreeArea(int[][] board) {
        int maxFreeArea = 0;

        for (int startRow = 0; startRow < board.length; startRow++) {
            int[] cumulativeColumnSum = new int[board[startRow].length];

            for (int endRow = startRow; endRow < board.length; endRow++) {
                for (int column = 0; column < board[startRow].length; column++) {
                    cumulativeColumnSum[column] += board[endRow][column];
                }
                int area = kadane(cumulativeColumnSum);
                maxFreeArea = Math.max(maxFreeArea, area);
            }
        }
        return maxFreeArea;
    }

    private static int kadane(int[] values) {
        int maxSum = 0;
        int currentSum = 0;

        for (int value : values) {
            currentSum += value;
            maxSum = Math.max(maxSum, currentSum);
            currentSum = Math.max(currentSum, 0);
        }
        return maxSum;
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
