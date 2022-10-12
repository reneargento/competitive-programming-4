package chapter3.section5.a.max.oned.twod.range.sum;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 22/09/22.
 */
// Based on https://github.com/morris821028/UVa/blob/master/volume011/1105%20-%20Coffee%20Central.cpp
public class CoffeeCentral {

    private static class Result {
        int maxCoffeeShopsReachable;
        Cell optimalLocation;

        public Result(int maxCoffeeShopsReachable, Cell optimalLocation) {
            this.maxCoffeeShopsReachable = maxCoffeeShopsReachable;
            this.optimalLocation = optimalLocation;
        }
    }

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    private static final int MAX_DIMENSION = 2001;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int cityWidth = FastReader.nextInt();
        int cityHeight = FastReader.nextInt();
        int coffeeShops = FastReader.nextInt();
        int queries = FastReader.nextInt();
        int caseNumber = 1;

        while (cityWidth != 0 || cityHeight != 0 || coffeeShops != 0 || queries != 0) {
            int[][] cumulativeSum = new int[MAX_DIMENSION][MAX_DIMENSION];
            for (int i = 0; i < coffeeShops; i++) {
                int column = FastReader.nextInt();
                int row = FastReader.nextInt();
                Cell rotatedCell = rotate(row, column, cityHeight);
                cumulativeSum[rotatedCell.row][rotatedCell.column]++;
            }

            int totalDimension = cityWidth + cityHeight;
            for (int row = 1; row <= totalDimension; row++) {
                int sum = 0;
                for (int column = 1; column <= totalDimension; column++) {
                    sum += cumulativeSum[row][column];
                    cumulativeSum[row][column] = cumulativeSum[row - 1][column] + sum;
                }
            }

            outputWriter.printLine(String.format("Case %d:", caseNumber));
            for (int q = 0; q < queries; q++) {
                int maxDistance = FastReader.nextInt();
                Result result = computeBestLocation(cumulativeSum, maxDistance, cityWidth, cityHeight);
                outputWriter.printLine(String.format("%d (%d,%d)", result.maxCoffeeShopsReachable,
                        result.optimalLocation.column, result.optimalLocation.row));
            }

            cityWidth = FastReader.nextInt();
            cityHeight = FastReader.nextInt();
            coffeeShops = FastReader.nextInt();
            queries = FastReader.nextInt();
            caseNumber++;
        }
        outputWriter.flush();
    }

    private static Result computeBestLocation(int[][] cumulativeSum, int maxDistance, int cityWidth, int cityHeight) {
        int maxCoffeeShopsReachable = 0;
        int optimalRow = 1;
        int optimalColumn = 1;
        int totalDimension = cityWidth + cityHeight;

        for (int row = 1; row <= cityHeight; row++) {
            for (int column = 1; column <= cityWidth; column++) {
                Cell rotatedCell = rotate(row, column, cityHeight);
                int startRow = Math.max(rotatedCell.row - maxDistance, 1);
                int endRow = Math.min(rotatedCell.row + maxDistance, totalDimension);
                int startColumn = Math.max(rotatedCell.column - maxDistance, 1);
                int endColumn = Math.min(rotatedCell.column + maxDistance, totalDimension);

                int coffeeShopsReachable = cumulativeSum[endRow][endColumn] - cumulativeSum[endRow][startColumn - 1]
                        - cumulativeSum[startRow - 1][endColumn] + cumulativeSum[startRow - 1][startColumn - 1];
                if (coffeeShopsReachable > maxCoffeeShopsReachable) {
                    maxCoffeeShopsReachable = coffeeShopsReachable;
                    optimalRow = row;
                    optimalColumn = column;
                }
            }
        }
        return new Result(maxCoffeeShopsReachable, new Cell(optimalRow, optimalColumn));
    }

    private static Cell rotate(int row, int column, int cityHeight) {
        int rotatedRow = row + column - 1;
        int rotatedColumn = column - row + cityHeight;
        return new Cell(rotatedRow, rotatedColumn);
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

        public void flush() {
            writer.flush();
        }
    }
}
