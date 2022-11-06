package chapter3.section5.a.max.oned.twod.range.sum;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/11/22.
 */
public class Area {

    private static class Result {
        int maxArea;
        int totalCost;

        public Result(int maxArea, int totalCost) {
            this.maxArea = maxArea;
            this.totalCost = totalCost;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int[][] land = new int[FastReader.nextInt()][FastReader.nextInt()];
            int money = FastReader.nextInt();

            for (int row = 0; row < land.length; row++) {
                for (int column = 0; column < land[row].length; column++) {
                    land[row][column] = FastReader.nextInt();
                }
            }
            Result result = computeMaxArea(land, money);
            outputWriter.printLine(String.format("Case #%d: %d %d", t, result.maxArea, result.totalCost));
        }
        outputWriter.flush();
    }

    private static Result computeMaxArea(int[][] land, int money) {
        Result bestResult = null;

        for (int startRow = 0; startRow < land.length; startRow++) {
            int[] cumulativeColumnSum = new int[land[startRow].length];
            for (int endRow = startRow; endRow < land.length; endRow++) {
                for (int column = 0; column < cumulativeColumnSum.length; column++) {
                    cumulativeColumnSum[column] += land[endRow][column];
                }

                int rowsCount = endRow - startRow + 1;
                Result result = kadane(cumulativeColumnSum, money, rowsCount);
                if (bestResult == null
                        || result.maxArea > bestResult.maxArea
                        || (result.maxArea == bestResult.maxArea && result.totalCost < bestResult.totalCost)) {
                    bestResult = result;
                }
            }
        }
        return bestResult;
    }

    private static Result kadane(int[] array, int money, int rowsCount) {
        int maxArea = 0;
        int selectedCost = 0;
        int currentCost = 0;
        int startIndex = 0;

        for (int i = 0; i < array.length; i++) {
            currentCost += array[i];

            while (currentCost > money) {
                currentCost -= array[startIndex];
                startIndex++;
            }

            int area = rowsCount * (i - startIndex + 1);
            if (area > maxArea
                    || (area == maxArea && currentCost < selectedCost)) {
                maxArea = area;
                selectedCost = currentCost;
            }
        }
        return new Result(maxArea, selectedCost);
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
