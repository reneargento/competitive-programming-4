package chapter3.section5.a.max.oned.twod.range.sum;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 25/09/22.
 */
public class LargestSubmatrix {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            FastReader.getLine();
            String line = FastReader.getLine();
            int size = line.length();
            int[][] cumulativeSum = new int[size][size];

            fillRow(cumulativeSum, line, 0);
            for (int row = 1; row < size; row++) {
                fillRow(cumulativeSum, FastReader.getLine(), row);
            }

            int elementsInLargestSubmatrix = computeElementsInLargestSubmatrix(cumulativeSum);
            if (t > 0) {
                outputWriter.printLine();
            }
            outputWriter.printLine(elementsInLargestSubmatrix);
        }
        outputWriter.flush();
    }

    private static void fillRow(int[][] cumulativeSum, String line, int row) {
        for (int column = 0; column < cumulativeSum[0].length; column++) {
            int value = (line.charAt(column) == '1') ? 1 : 0;
            if (row > 0) {
                value += cumulativeSum[row - 1][column];
            }
            if (column > 0) {
                value += cumulativeSum[row][column - 1];
            }
            if (row > 0 && column > 0) {
                value -= cumulativeSum[row - 1][column - 1];
            }
            cumulativeSum[row][column] = value;
        }
    }

    private static int computeElementsInLargestSubmatrix(int[][] cumulativeSum) {
        int elementsInLargestSubmatrix = 0;

        for (int startRow = 0; startRow < cumulativeSum.length; startRow++) {
            for (int endRow = startRow; endRow < cumulativeSum.length; endRow++) {
                for (int startColumn = 0; startColumn < cumulativeSum[0].length; startColumn++){
                    for (int endColumn = startColumn; endColumn < cumulativeSum[0].length; endColumn++) {
                        int sum = cumulativeSum[endRow][endColumn];
                        if (startRow > 0) {
                            sum -= cumulativeSum[startRow - 1][endColumn];
                        }
                        if (startColumn > 0) {
                            sum -= cumulativeSum[endRow][startColumn - 1];
                        }
                        if (startRow > 0 && startColumn > 0) {
                            sum += cumulativeSum[startRow - 1][startColumn - 1];
                        }

                        int sumWithAllOnes = (endRow - startRow + 1) * (endColumn - startColumn + 1);
                        if (sum == sumWithAllOnes && sum > elementsInLargestSubmatrix) {
                            elementsInLargestSubmatrix = sum;
                        }
                    }
                }
            }
        }
        return elementsInLargestSubmatrix;
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

        public void flush() {
            writer.flush();
        }
    }
}
