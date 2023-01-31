package chapter3.section5.g.dp.level2;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 29/01/23.
 */
public class DrivingLanes {

    private static class Curve {
        int stretch;
        int curvature;

        public Curve(int stretch, int curvature) {
            this.stretch = stretch;
            this.curvature = curvature;
        }
    }

    private static final long INFINITE = 100000000000L;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] straightawayLengths = new int[FastReader.nextInt()];
        Curve[] curves = new Curve[straightawayLengths.length - 1];

        int lanes = FastReader.nextInt();
        int forwardDistanceChangingLane = FastReader.nextInt();
        int horizontalDistanceChangingLane = FastReader.nextInt();

        for (int i = 0; i < straightawayLengths.length; i++) {
            straightawayLengths[i] = FastReader.nextInt();
        }
        for (int i = 0; i < curves.length; i++) {
            curves[i] = new Curve(FastReader.nextInt(), FastReader.nextInt());
        }

        long minimumDistance = computeMinimumDistance(straightawayLengths, curves, lanes, forwardDistanceChangingLane,
                horizontalDistanceChangingLane);
        outputWriter.printLine(minimumDistance);
        outputWriter.flush();
    }

    private static long computeMinimumDistance(int[] straightawayLengths, Curve[] curves, int lanes,
                                               int forwardDistanceChangingLane, int horizontalDistanceChangingLane) {
        // dp[straightawayID][laneID] = minimum distance
        long[][] dp = new long[straightawayLengths.length][lanes];
        for (long[] values : dp) {
            Arrays.fill(values, -1);
        }
        return computeMinimumDistance(straightawayLengths, curves, lanes, forwardDistanceChangingLane,
                horizontalDistanceChangingLane, dp, 0, 0);
    }

    private static long computeMinimumDistance(int[] straightawayLengths, Curve[] curves, int lanes,
                                               int forwardDistanceChangingLane, int horizontalDistanceChangingLane,
                                               long[][] dp, int currentStraightaway, int currentLane) {
        if (currentStraightaway == straightawayLengths.length) {
            if (currentLane == 0) {
                return 0;
            }
            return INFINITE;
        }
        if (dp[currentStraightaway][currentLane] != -1) {
            return dp[currentStraightaway][currentLane];
        }

        long minimumDistance = INFINITE;
        for (int laneID = 0; laneID < lanes; laneID++) {
            int laneDistance = Math.abs(currentLane - laneID);
            int distanceHorizontalToSwitch = laneDistance * horizontalDistanceChangingLane;
            int distanceForwardToSwitch = laneDistance * forwardDistanceChangingLane;
            if (distanceForwardToSwitch <= straightawayLengths[currentStraightaway]) {
                int curveDistance = 0;
                if (currentStraightaway != straightawayLengths.length - 1) {
                    Curve curve = curves[currentStraightaway];
                    curveDistance = curve.stretch + (curve.curvature * (laneID + 1));
                }
                long distance = straightawayLengths[currentStraightaway] + distanceHorizontalToSwitch + curveDistance +
                        computeMinimumDistance(straightawayLengths, curves, lanes, forwardDistanceChangingLane,
                                horizontalDistanceChangingLane, dp, currentStraightaway + 1, laneID);
                minimumDistance = Math.min(minimumDistance, distance);
            }
        }
        dp[currentStraightaway][currentLane] = minimumDistance;
        return dp[currentStraightaway][currentLane];
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
