package chapter3.section5.e.traveling.salesman.problem;

import java.io.*;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by Rene Argento on 10/12/22.
 */
public class MaximizingYourPay {

    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int locations = inputReader.nextInt();

        while (locations != 0) {
            int streetsNumber = inputReader.nextInt();
            boolean[][] streets = new boolean[locations][locations];
            for (int i = 0; i < streetsNumber; i++) {
                int locationId1 = inputReader.nextInt();
                int locationId2 = inputReader.nextInt();
                streets[locationId1][locationId2] = true;
                streets[locationId2][locationId1] = true;
            }
            int maxLocations = Math.max(1, computeMaximumLengthTour(locations, streets));
            outputWriter.printLine(maxLocations);
            locations = inputReader.nextInt();
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

    private static class InputReader {
        private final InputStream stream;
        private final byte[] buf = new byte[8192];
        private int curChar, snumChars;

        private InputReader(InputStream stream) {
            this.stream = stream;
        }

        private int snext() throws IOException {
            if (snumChars == -1)
                throw new InputMismatchException();
            if (curChar >= snumChars) {
                curChar = 0;
                snumChars = stream.read(buf);
                if (snumChars <= 0)
                    return -1;
            }
            return buf[curChar++];
        }

        private int nextInt() throws IOException {
            int c = snext();
            while (isSpaceChar(c)) {
                c = snext();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = snext();
            }
            int res = 0;
            do {
                res *= 10;
                res += c - '0';
                c = snext();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        private boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == -1;
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
