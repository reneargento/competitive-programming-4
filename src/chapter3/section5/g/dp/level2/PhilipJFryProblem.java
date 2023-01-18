package chapter3.section5.g.dp.level2;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 16/01/23.
 */
public class PhilipJFryProblem {

    private static class Trip {
        int duration;
        int spheres;

        public Trip(int duration, int spheres) {
            this.duration = duration;
            this.spheres = spheres;
        }
    }

    private static final int INFINITE = 10000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tripsNumber = FastReader.nextInt();

        while (tripsNumber != 0) {
            Trip[] trips = new Trip[tripsNumber];
            for (int i = 0; i < trips.length; i++) {
                trips[i] = new Trip(FastReader.nextInt(), FastReader.nextInt());
            }
            int minimumTime = computeMinimumTime(trips);
            outputWriter.printLine(minimumTime);
            tripsNumber = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeMinimumTime(Trip[] trips) {
        // dp[tripId][spheresAvailable]
        int[][] dp = new int[trips.length][1001];
        for (int[] values : dp) {
            Arrays.fill(values, -1);
        }
        return computeMinimumTime(trips, dp, 0, 0);
    }

    private static int computeMinimumTime(Trip[] trips, int[][] dp, int tripId, int spheresAvailable) {
        if (tripId == dp.length) {
            return 0;
        }
        if (dp[tripId][spheresAvailable] != -1) {
            return dp[tripId][spheresAvailable];
        }

        int newSpheresAvailable = spheresAvailable + trips[tripId].spheres;
        if (newSpheresAvailable >= dp[0].length) {
            newSpheresAvailable = dp[0].length - 1;
        }

        int timeNotUsingSphere = trips[tripId].duration + computeMinimumTime(trips, dp, tripId + 1, newSpheresAvailable);
        int timeUsingSphere = INFINITE;
        if (spheresAvailable > 0) {
            timeUsingSphere = (trips[tripId].duration / 2) + computeMinimumTime(trips, dp, tripId + 1, newSpheresAvailable - 1);
        }
        dp[tripId][spheresAvailable] = Math.min(timeNotUsingSphere, timeUsingSphere);
        return dp[tripId][spheresAvailable];
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
