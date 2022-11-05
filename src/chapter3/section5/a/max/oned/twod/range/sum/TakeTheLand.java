package chapter3.section5.a.max.oned.twod.range.sum;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 26/09/22.
 */
public class TakeTheLand {

    private static final int INFINITE_NEGATIVE = -100000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int rows = FastReader.nextInt();
        int columns = FastReader.nextInt();

        while (rows != 0 || columns != 0) {
            int[][] forestSum = new int[rows][columns];

            for (int row = 0; row < rows; row++) {
                for (int column = 0; column < columns; column++) {
                    int value = FastReader.nextInt();
                    if (value == 0) {
                        value = 1;
                    } else {
                        value = INFINITE_NEGATIVE;
                    }
                    forestSum[row][column] = value;
                }
            }

            int largestArea = computeLargestArea(forestSum);
            outputWriter.printLine(largestArea);
            rows = FastReader.nextInt();
            columns = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeLargestArea(int[][] forestSum) {
        int largestArea = 0;

        for (int startRow = 0; startRow < forestSum.length; startRow++) {
            int[] cumulativeColumnSum = new int[forestSum[0].length];

            for (int endRow = startRow; endRow < forestSum.length; endRow++) {
                for (int column = 0; column < cumulativeColumnSum.length; column++) {
                    cumulativeColumnSum[column] += forestSum[endRow][column];
                }

                int area = maxSum1D(cumulativeColumnSum);
                largestArea = Math.max(largestArea, area);
            }
        }
        return largestArea;
    }

    private static int maxSum1D(int[] forestRow) {
        int maxSum = 0;
        int currentMaxSum = 0;

        for (int value : forestRow) {
            currentMaxSum += value;
            maxSum = Math.max(maxSum, currentMaxSum);
            currentMaxSum = Math.max(currentMaxSum, 0);
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
