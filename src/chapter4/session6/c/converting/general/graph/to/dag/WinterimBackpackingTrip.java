package chapter4.session6.c.converting.general.graph.to.dag;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 28/05/24.
 */
public class WinterimBackpackingTrip {

    private static final long INFINITE = 100000000000L;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            String[] data = line.split(" ");
            int campsites = Integer.parseInt(data[0]);
            int nights = Integer.parseInt(data[1]);

            long[] consecutiveDistances = new long[campsites + 4];
            for (int i = 0; i < consecutiveDistances.length - 3; i++) {
                consecutiveDistances[i] = FastReader.nextInt();
            }

            long minimumMaximumWalk = computeMinimumMaximumWalk(consecutiveDistances, nights);
            outputWriter.printLine(minimumMaximumWalk);

            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static long computeMinimumMaximumWalk(long[] consecutiveDistances, int nights) {
        long[][] distances = getDistances(consecutiveDistances);
        // dp[campsiteId][nightsLeft]
        long[][] dp = new long[consecutiveDistances.length + 1][nights + 1];
        for (long[] values : dp) {
            Arrays.fill(values, -1);
        }
        return computeMinimumMaximumWalk(consecutiveDistances, distances, dp, 0, nights);
    }

    private static long computeMinimumMaximumWalk(long[] consecutiveDistances, long[][] distances, long[][] dp,
                                                  int campsiteId, int nightsLeft) {
        if (campsiteId == distances.length) {
            return 0;
        }
        if (nightsLeft == 0) {
            return distances[campsiteId][distances.length - 1];
        }

        if (dp[campsiteId][nightsLeft] != -1) {
            return dp[campsiteId][nightsLeft];
        }

        long minimumMaximumWalk = INFINITE;
        long distanceFromCampsiteId = consecutiveDistances[campsiteId];

        for (int nextCampsiteId = campsiteId + 1; nextCampsiteId < consecutiveDistances.length; nextCampsiteId++) {
            long distanceCandidate = computeMinimumMaximumWalk(consecutiveDistances, distances, dp,
                    nextCampsiteId, nightsLeft - 1);
            long maxWalkDistance = Math.max(distanceFromCampsiteId, distanceCandidate);
            minimumMaximumWalk = Math.min(minimumMaximumWalk, maxWalkDistance);

            distanceFromCampsiteId += consecutiveDistances[nextCampsiteId];
            if (minimumMaximumWalk < distanceFromCampsiteId) {
                break;
            }
        }
        dp[campsiteId][nightsLeft] = minimumMaximumWalk;
        return dp[campsiteId][nightsLeft];
    }

    private static long[][] getDistances(long[] consecutiveDistances) {
        long[][] distances = new long[consecutiveDistances.length + 1][consecutiveDistances.length + 1];

        for (int campsiteId = 0; campsiteId < distances.length; campsiteId++) {
            for (int i = campsiteId; i < consecutiveDistances.length; i++) {
                distances[campsiteId][i + 1] = distances[campsiteId][i] + consecutiveDistances[i];
                distances[i + 1][campsiteId] = distances[campsiteId][i + 1];
            }
        }
        return distances;
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
