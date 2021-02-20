package chapter2.section2.d.twod.array.manipulation.harder;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Rene Argento on 12/02/21.
 */
public class FallingApples {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int rows = scanner.nextInt();
        int columns = scanner.nextInt();

        char[][] board = new char[rows][columns];
        for (int row = 0; row < board.length; row++) {
            board[row] = scanner.next().toCharArray();
        }
        applyGravity(board);
        printBoard(board);
    }

    private static void applyGravity(char[][] board) {
        for (int column = 0; column < board[0].length; column++) {
            int applesCount = 0;
            for (int row = 0; row < board.length; row++) {
                if (board[row][column] == 'a') {
                    applesCount++;
                    board[row][column] = '.';
                } else if (board[row][column] == '#') {
                    pileApples(board, row - 1, column, applesCount);
                    applesCount = 0;
                }
            }

            if (applesCount != 0) {
                pileApples(board, board.length - 1, column, applesCount);
            }
        }
    }

    private static void pileApples(char[][] board, int row, int column, int quantity) {
        for (int r = row; r > row - quantity; r--) {
            board[r][column] = 'a';
        }
    }

    private static void printBoard(char[][] board) {
        OutputWriter outputWriter = new OutputWriter(System.out);

        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[0].length; column++) {
                outputWriter.print(board[row][column]);
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
        outputWriter.close();
    }

    private static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++) {
                if (i != 0)
                    writer.print(' ');
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
