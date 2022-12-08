package chapter3.section5.e.traveling.salesman.problem;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/12/22.
 */
public class CollectingBeepersUVa {

    private static class Coordinate {
        int x;
        int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int scenarios = FastReader.nextInt();

        for (int s = 0; s < scenarios; s++) {
            int xSize = FastReader.nextInt();
            int ySize = FastReader.nextInt();
            Coordinate startingPosition = new Coordinate(FastReader.nextInt(), FastReader.nextInt());
            Coordinate[] beepers = new Coordinate[FastReader.nextInt() + 1];
            beepers[0] = startingPosition;
            for (int i = 1; i < beepers.length; i++) {
                beepers[i] = new Coordinate(FastReader.nextInt(), FastReader.nextInt());
            }

            int minimumDistance = computeMinimumDistance(startingPosition, beepers);
            outputWriter.printLine(String.format("The shortest path has length %d", minimumDistance));
        }
        outputWriter.flush();
    }

    private static int computeMinimumDistance(Coordinate startingPosition, Coordinate[] beepers) {
        int maxBitmapValue = (int) Math.pow(2, beepers.length);
        int[][] dp = new int[beepers.length][maxBitmapValue];
        for (int[] values : dp) {
            Arrays.fill(values, -1);
        }

        int beepersBitmask = 1;
        return computeMinimumDistance(startingPosition, beepers, dp, 0, beepersBitmask);
    }

    private static int computeMinimumDistance(Coordinate startingPosition, Coordinate[] beepers, int[][] dp,
                                              int currentLocationId, int beepersBitmask) {
        if (beepersBitmask == dp[0].length - 1) {
            return computeCoordinateDistance(beepers[currentLocationId], startingPosition);
        }

        if (dp[currentLocationId][beepersBitmask] != -1) {
            return dp[currentLocationId][beepersBitmask];
        }

        int minimumDistance = Integer.MAX_VALUE;
        for (int locationId = 0; locationId < beepers.length; locationId++) {
            if ((beepersBitmask & (1 << locationId)) == 0) {
                int bitmaskWithLocation = beepersBitmask | (1 << locationId);
                int distanceFromNextLocation = computeCoordinateDistance(beepers[currentLocationId], beepers[locationId]);

                int totalDistance = distanceFromNextLocation +
                        computeMinimumDistance(startingPosition, beepers, dp, locationId, bitmaskWithLocation);
                minimumDistance = Math.min(minimumDistance, totalDistance);
            }
        }
        dp[currentLocationId][beepersBitmask] = minimumDistance;
        return dp[currentLocationId][beepersBitmask];
    }

    private static int computeCoordinateDistance(Coordinate coordinate1, Coordinate coordinate2) {
        return Math.abs(coordinate1.x - coordinate2.x) + Math.abs(coordinate1.y - coordinate2.y);
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
