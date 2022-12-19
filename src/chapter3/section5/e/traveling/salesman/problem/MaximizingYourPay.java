package chapter3.section5.e.traveling.salesman.problem;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 10/12/22.
 */
public class MaximizingYourPay {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int locations = FastReader.nextInt();

        while (locations != 0) {
            int streetsNumber = FastReader.nextInt();
            boolean[][] streets = new boolean[locations][locations];
            for (int i = 0; i < streetsNumber; i++) {
                int locationId1 = FastReader.nextInt();
                int locationId2 = FastReader.nextInt();
                streets[locationId1][locationId2] = true;
                streets[locationId2][locationId1] = true;
            }
            int maxLocations = Math.max(1, computeMaximumLengthTour(locations, streets));
            outputWriter.printLine(maxLocations);
            locations = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeMaximumLengthTour(int locations, boolean[][] streets) {
        int bitmaskSize = 1 << locations;
        int[][] dp = new int[locations][bitmaskSize];
        for (int[] values : dp) {
            Arrays.fill(values, -1);
        }

        int bitmask = 1;
        return computeMaximumLengthTour(streets, dp, 0, bitmask);
    }

    private static int computeMaximumLengthTour(boolean[][] streets, int[][] dp, int currentLocationId, int bitmask) {
        if (bitmask == dp[0].length - 1) {
            if (streets[currentLocationId][0]) {
                return 1;
            }
            return -1;
        }
        if (dp[currentLocationId][bitmask] != -1) {
            return dp[currentLocationId][bitmask];
        }

        int maxLength = -1;
        for (int locationId = 0; locationId < dp.length; locationId++) {
            if (streets[currentLocationId][locationId] && (bitmask & (1 << locationId)) == 0) {
                int nextBitmask = bitmask | (1 << locationId);
                int length = computeMaximumLengthTour(streets, dp, locationId, nextBitmask);
                if (length != -1) {
                    length++;
                }
                maxLength = Math.max(maxLength, length);
            }
        }

        if (maxLength == -1) {
            if (streets[currentLocationId][0]) {
                return 1;
            }
            return -1;
        }
        dp[currentLocationId][bitmask] = maxLength;
        return dp[currentLocationId][bitmask];
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
