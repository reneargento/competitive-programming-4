package chapter4.section2.c.flood.fill.harder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 16/03/23.
 */
public class MonkeysInARegularForest {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] neighborRows = { -1, -1, -1, 0, 0, 1, 1, 1 };
        int[] neighborColumns = { -1, 0, 1, -1, 1, -1, 0, 1 };

        String line = FastReader.getLine();
        while (line != null) {
            List<String> rows = new ArrayList<>();

            while (line != null && !line.equals("%")) {
                rows.add(line);
                line = FastReader.getLine();
            }

            char[][] forest = new char[rows.size()][rows.get(0).split(" ").length];
            for (int row = 0; row < rows.size(); row++) {
                String[] rowValues = rows.get(row).split(" ");
                for (int column = 0; column < rowValues.length; column++) {
                    forest[row][column] = rowValues[column].charAt(0);
                }
            }

            int[][] monkeyLocations = allocateMonkeys(forest, neighborRows, neighborColumns);
            int[] columnWidths = computeColumnWidths(monkeyLocations);

            for (int row = 0; row < monkeyLocations.length; row++) {
                for (int column = 0; column < monkeyLocations[0].length; column++) {
                    if (column > 0) {
                        outputWriter.print(" ");
                    }
                    outputWriter.print(String.format("%" + columnWidths[column] + "d", monkeyLocations[row][column]));
                }
                outputWriter.printLine();
            }
            outputWriter.printLine("%");
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int[][] allocateMonkeys(char[][] forest, int[] neighborRows, int[] neighborColumns) {
        int[][] monkeyLocations = new int[forest.length][forest[0].length];
        int monkeyFamilyID = 1;

        for (int row = 0; row < monkeyLocations.length; row++) {
            for (int column = 0; column < monkeyLocations[0].length; column++) {
                if (monkeyLocations[row][column] == 0) {
                    floodFill(forest, monkeyLocations, neighborRows, neighborColumns, monkeyFamilyID, row, column);
                    monkeyFamilyID++;
                }
            }
        }
        return monkeyLocations;
    }

    private static void floodFill(char[][] forest, int[][] monkeyLocations, int[] neighborRows, int[] neighborColumns,
                                  int monkeyFamilyID, int row, int column) {
        monkeyLocations[row][column] = monkeyFamilyID;

        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = row + neighborRows[i];
            int neighborColumn = column + neighborColumns[i];

            if (isValid(forest, neighborRow, neighborColumn)
                    && forest[neighborRow][neighborColumn] == forest[row][column]
                    && monkeyLocations[neighborRow][neighborColumn] == 0) {
                floodFill(forest, monkeyLocations, neighborRows, neighborColumns, monkeyFamilyID, neighborRow,
                        neighborColumn);
            }
        }
    }

    private static int[] computeColumnWidths(int[][] monkeyLocations) {
        int[] columnWidths = new int[monkeyLocations[0].length];

        for (int column = 0; column < monkeyLocations[0].length; column++) {
            int maxWidth = 0;
            for (int row = 0; row < monkeyLocations.length; row++) {
                int width = countDigits(monkeyLocations[row][column]);
                maxWidth = Math.max(maxWidth, width);
            }
            columnWidths[column] = maxWidth;
        }
        return columnWidths;
    }

    private static int countDigits(int number) {
        int digits = 0;

        while (number > 0) {
            digits++;
            number /= 10;
        }
        return digits;
    }

    private static boolean isValid(char[][] forest, int row, int column) {
        return row >= 0 && row < forest.length && column >= 0 && column < forest[0].length;
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
