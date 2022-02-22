package chapter3.section2.l.recursive.backtracking.harder;

import java.io.*;

/**
 * Created by Rene Argento on 20/02/22.
 */
public class PlaceTheGuards {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            int placeSize = Integer.parseInt(line);
            int[] guardRows = computeGuardRows(placeSize);

            if (guardRows != null) {
                outputWriter.print(guardRows[0]);
                for (int i = 1; i < guardRows.length; i++) {
                    outputWriter.print(" " + guardRows[i]);
                }
                outputWriter.printLine();
            } else {
                outputWriter.printLine("Impossible");
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int[] computeGuardRows(int placeSize) {
        int[] currentGuardRows = new int[placeSize];
        int guardIndex = 0;

        if ((placeSize - 2) % 6 != 0 && (placeSize - 3) % 6 != 0) {
            for (int position = 2; position <= placeSize; position += 2) {
                currentGuardRows[guardIndex++] = position;
            }
            for (int position = 1; position <= placeSize; position += 2) {
                currentGuardRows[guardIndex++] = position;
            }
            return currentGuardRows;
        } else {
            boolean[] rowsTaken = new boolean[placeSize];
            boolean[] leftDiagonalsTaken = new boolean[placeSize * 2];
            boolean[] rightDiagonalsTaken = new boolean[placeSize * 2];

            int multiplier = placeSize / 6;
            int patternDivisor = multiplier * 2 + 2;
            for (int position = patternDivisor; position <= placeSize; position += 2) {
                updateTakenPositions(placeSize, position - 1, guardIndex, rowsTaken, leftDiagonalsTaken,
                        rightDiagonalsTaken, true);
                currentGuardRows[guardIndex++] = position;
            }
            for (int position = 2; position < patternDivisor; position += 2) {
                updateTakenPositions(placeSize, position - 1, guardIndex, rowsTaken, leftDiagonalsTaken,
                        rightDiagonalsTaken, true);
                currentGuardRows[guardIndex++] = position;
            }

            int numberOfOddRows;
            if (placeSize % 2 == 0) {
                numberOfOddRows = placeSize / 2;
            } else {
                numberOfOddRows = placeSize / 2 + 1;
            }
            int[] oddRows = new int[numberOfOddRows];
            int oddRowsIndex = 0;
            for (int position = 0; position < placeSize; position += 2) {
                oddRows[oddRowsIndex++] = position;
            }

            for (int i = 0; i < oddRows.length; i++) {
                if (canPlaceGuardOddRows(placeSize, rowsTaken, leftDiagonalsTaken, rightDiagonalsTaken,
                        currentGuardRows, oddRows, guardIndex, i)) {
                    return currentGuardRows;
                }
            }
            return null;
        }
    }

    private static boolean canPlaceGuardOddRows(int placeSize, boolean[] rowsTaken, boolean[] leftDiagonalsTaken,
                                                boolean[] rightDiagonalsTaken, int[] currentGuardRows,
                                                int[] oddRows, int column, int oddRowIndex) {
        if (column == placeSize) {
            return true;
        }
        if (oddRowIndex == oddRows.length) {
            oddRowIndex = 0;
        }

        int row = oddRows[oddRowIndex];
        int leftDiagonal = row - column + placeSize;
        int rightDiagonal = row + column;

        if (!rowsTaken[row]
                && !leftDiagonalsTaken[leftDiagonal]
                && !rightDiagonalsTaken[rightDiagonal]) {
            updateTakenPositions(placeSize, row, column, rowsTaken, leftDiagonalsTaken,
                    rightDiagonalsTaken, true);
            currentGuardRows[column] = row + 1;
            boolean canPlace = canPlaceGuardOddRows(placeSize, rowsTaken, leftDiagonalsTaken, rightDiagonalsTaken,
                    currentGuardRows, oddRows, column + 1, oddRowIndex + 1);
            if (canPlace) {
                return true;
            }
            updateTakenPositions(placeSize, row, column, rowsTaken, leftDiagonalsTaken,
                    rightDiagonalsTaken, false);
        }
        return false;
    }

    private static void updateTakenPositions(int dimension, int row, int column, boolean[] rowsTaken,
                                             boolean[] leftDiagonalsTaken, boolean[] rightDiagonalsTaken,
                                             boolean taken) {
        int leftDiagonal = row - column + dimension;
        int rightDiagonal = row + column;
        rowsTaken[row] = taken;
        leftDiagonalsTaken[leftDiagonal] = taken;
        rightDiagonalsTaken[rightDiagonal] = taken;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
