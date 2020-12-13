package chapter1.section6.n.output.formatting.easier;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Rene Argento on 12/12/20.
 */
public class Border {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = scanner.nextInt();

        for (int t = 1; t <= tests; t++) {
            int startColumn = scanner.nextInt();
            int startRow = 31 - scanner.nextInt();
            String moves = scanner.next();
            char[][] grid = new char[32][32];

            initGrid(grid);
            markCells(grid, startRow, startColumn, moves.substring(0, moves.length() - 1));
            outputWriter.printLine("Bitmap #" + t);
            printGrid(grid, outputWriter);
            outputWriter.printLine();
        }
        outputWriter.flush();
        outputWriter.close();
    }

    private static void markCells(char[][] grid, int row, int column, String moves) {
        for (char move : moves.toCharArray()) {
            switch (move) {
                case 'E':
                    if (isValid(grid, row + 1, column)) {
                        grid[row + 1][column] = 'X';
                    }
                    column++;
                    break;
                case 'W':
                    column--;
                    grid[row][column] = 'X';
                    break;
                case 'N':
                    grid[row][column] = 'X';
                    row--;
                    break;
                default:
                    row++;
                    if (isValid(grid, row, column - 1)) {
                        grid[row][column - 1] = 'X';
                    }
            }
        }
    }

    private static void initGrid(char[][] grid) {
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[0].length; column++) {
                grid[row][column] = '.';
            }
        }
    }

    private static void printGrid(char[][] grid, OutputWriter outputWriter) {
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[0].length; column++) {
                outputWriter.print(grid[row][column]);
            }
            outputWriter.printLine();
        }
    }

    private static boolean isValid(char[][] grid, int row, int column) {
        return row >= 0 && row < grid.length && column >= 0 && column < grid[0].length;
    }

    private static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++)
            {
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
