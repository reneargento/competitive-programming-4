package chapter3.section3.a.binary.search;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 11/03/22.
 */
public class Grapevine {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int rows = FastReader.nextInt();
        int columns = FastReader.nextInt();

        while (rows != 0 || columns != 0) {
            int[][] region = new int[rows][columns];
            for (int row = 0; row < region.length; row++) {
                for (int column = 0; column < region[0].length; column++) {
                    region[row][column] = FastReader.nextInt();
                }
            }
            int queries = FastReader.nextInt();
            for (int i = 0; i < queries; i++) {
                int lowerAltitude = FastReader.nextInt();
                int higherAltitude = FastReader.nextInt();
                int[] rangeStart = computeColumnsRangeStart(region, lowerAltitude);

                int largestSquare = computeLargestSquare(region, higherAltitude, rangeStart);
                outputWriter.printLine(largestSquare);
            }
            outputWriter.printLine("-");

            rows = FastReader.nextInt();
            columns = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int[] computeColumnsRangeStart(int[][] region, int lowerAltitude) {
        int[] rangeStart = new int[region[0].length];
        for (int column = 0; column < region[0].length; column++) {
            if (region[0][column] >= lowerAltitude) {
                rangeStart[column] = 0;
                continue;
            } else if (region[region.length - 1][column] < lowerAltitude) {
                rangeStart[column] = -1;
                continue;
            }

            int start = binarySearch(region, column, lowerAltitude);
            rangeStart[column] = start;
        }
        return rangeStart;
    }

    private static int binarySearch(int[][] region, int column, int target) {
        int low = 0;
        int high = region.length - 1;
        int result = -1;

        while (low <= high) {
            int middle = low + (high - low) / 2;

            if (region[middle][column] < target) {
                low = middle + 1;
            } else {
                result = middle;
                high = middle - 1;
            }
        }
        return result;
    }

    private static int computeLargestSquare(int[][] region, int higherAltitude, int[] rangeStart) {
        int largestSquare = 0;

        for (int column = 0; column < region[0].length; column++) {
            if (rangeStart[column] == -1) {
                continue;
            }

            int sizeFound = 0;
            int startRow = rangeStart[column];

            for (int s = largestSquare; s < region.length; s++) {
                if (startRow + s >= region.length
                        || column + s >= region[0].length
                        || region[startRow][column + s] > higherAltitude
                        || region[startRow + s][column + s] > higherAltitude) {
                    break;
                }
                sizeFound = s + 1;
            }
            largestSquare = Math.max(largestSquare, sizeFound);
        }
        return largestSquare;
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
