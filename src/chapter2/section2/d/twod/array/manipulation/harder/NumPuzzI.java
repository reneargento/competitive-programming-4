package chapter2.section2.d.twod.array.manipulation.harder;

import java.io.*;
import java.util.Scanner;

/**
 * Created by Rene Argento on 12/02/21.
 */
public class NumPuzzI {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int caseNumber = 1;
        int[] neighborRows = { -1, 0, 0, 1, 0 };
        int[] neighborColumns = { 0, -1, 1, 0, 0 };

        while (scanner.hasNextLine()) {
            String moves = scanner.nextLine();
            int[][] numPuzz = computeInitialConfiguration(moves, neighborRows, neighborColumns);

            outputWriter.printLine("Case #" + caseNumber + ":");
            printNumPuzz(numPuzz, outputWriter);
            caseNumber++;
        }
        outputWriter.flush();
        outputWriter.close();
    }

    private static int[][] computeInitialConfiguration(String moves, int[] neighborRows, int[] neighborColumns) {
        int[][] numPuzz = new int[3][3];

        for (char move : moves.toCharArray()) {
            int index = move - 'a';
            int row = index / 3;
            int column = index % 3;
            incrementCellAndNeighbors(numPuzz, row, column, neighborRows, neighborColumns);
        }
        return numPuzz;
    }

    private static void incrementCellAndNeighbors(int[][] numPuzz, int row, int column, int[] neighborRows,
                                                  int[] neighborColumns) {
        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = row + neighborRows[i];
            int neighborColumn = column + neighborColumns[i];

            if (isValid(numPuzz, neighborRow, neighborColumn)) {
                numPuzz[neighborRow][neighborColumn]++;
                numPuzz[neighborRow][neighborColumn] %= 10;
            }
        }
    }

    private static boolean isValid(int[][] numPuzz, int row, int column) {
        return row >= 0 && row < numPuzz.length && column >= 0 && column < numPuzz[0].length;
    }

    private static void printNumPuzz(int[][] numPuzz, OutputWriter outputWriter) {
        for (int row = 0; row < numPuzz.length; row++) {
            for (int column = 0; column < numPuzz[0].length; column++) {
                outputWriter.print(numPuzz[row][column]);

                if (column != numPuzz[0].length - 1) {
                    outputWriter.print(" ");
                }
            }
            outputWriter.printLine();
        }
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
