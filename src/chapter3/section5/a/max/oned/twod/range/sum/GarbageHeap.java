package chapter3.section5.a.max.oned.twod.range.sum;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 11/09/22.
 */
// Based on https://www.redgreencode.com/three-dimensional-dynamic-programming-uva-10755/
public class GarbageHeap {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            if (t > 0) {
                outputWriter.printLine();
            }
            int rows = FastReader.nextInt();
            int columns = FastReader.nextInt();
            int sections = FastReader.nextInt();
            long[][][] memoTable = new long[rows][columns][sections];

            for (int row = 0; row < rows; row++) {
                for (int column = 0; column < columns; column++) {
                    for (int section = 0; section < sections; section++) {
                        long value = FastReader.nextLong();

                        if (row > 0) {
                            value += memoTable[row - 1][column][section];
                        }
                        if (column > 0) {
                            value += memoTable[row][column - 1][section];
                        }
                        if (section > 0) {
                            value += memoTable[row][column][section - 1];
                        }

                        if (row > 0 && column > 0) {
                            value -= memoTable[row - 1][column - 1][section];
                        }
                        if (column > 0 && section > 0) {
                            value -= memoTable[row][column - 1][section - 1];
                        }
                        if (row > 0 && section > 0) {
                            value -= memoTable[row - 1][column][section - 1];
                        }

                        if (row > 0 && column > 0 && section > 0) {
                            value += memoTable[row - 1][column - 1][section - 1];
                        }
                        memoTable[row][column][section] = value;
                    }
                }
            }
            long maxValue = computeMaxSum(memoTable);
            outputWriter.printLine(maxValue);
        }
        outputWriter.flush();
    }

    private static long computeMaxSum(long[][][] memoTable) {
        long maxSum = Long.MIN_VALUE;

        int rowCount = memoTable.length;
        int columnCount = memoTable[0].length;
        int sectionCount = memoTable[0][0].length;

        for (int startRow = 0; startRow < rowCount; startRow++) {
            for (int startColumn = 0; startColumn < columnCount; startColumn++) {
                for (int startSection = 0; startSection < sectionCount; startSection++) {
                    for (int endRow = startRow; endRow < rowCount; endRow++) {
                        for (int endColumn = startColumn; endColumn < columnCount; endColumn++) {
                            for (int endSection = startSection; endSection < sectionCount; endSection++) {
                                long sum = memoTable[endRow][endColumn][endSection];
                                if (startRow > 0) {
                                    sum -= memoTable[startRow - 1][endColumn][endSection];
                                }
                                if (startColumn > 0) {
                                    sum -= memoTable[endRow][startColumn - 1][endSection];
                                }
                                if (startSection > 0) {
                                    sum -= memoTable[endRow][endColumn][startSection - 1];
                                }

                                if (startRow > 0 && startColumn > 0) {
                                    sum += memoTable[startRow - 1][startColumn - 1][endSection];
                                }
                                if (startColumn > 0 && startSection > 0) {
                                    sum += memoTable[endRow][startColumn - 1][startSection - 1];
                                }
                                if (startRow > 0 && startSection > 0) {
                                    sum += memoTable[startRow - 1][endColumn][startSection - 1];
                                }

                                if (startRow > 0 && startColumn > 0 && startSection > 0) {
                                    sum -= memoTable[startRow - 1][startColumn - 1][startSection - 1];
                                }
                                maxSum = Math.max(maxSum, sum);
                            }
                        }
                    }
                }
            }
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

        private static long nextLong() throws IOException {
            return Long.parseLong(next());
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
