package chapter4.section6.c.converting.general.graph.to.dag;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 26/06/24.
 */
public class AvoidingJungleInTheDark {

    private static final int INFINITE = 100000000;
    private static final int REST_HOURS = 8;
    private static final int MAX_TIME_BEFORE_REST = 16;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            String map = FastReader.getLine();
            int minimumTime = computeMinimumTime(map);
            outputWriter.printLine(String.format("Case #%d: %d", t, minimumTime));
        }
        outputWriter.flush();
    }

    private static int computeMinimumTime(String map) {
        // dp[location id][hours][time after last rest]
        int[][][] dp = new int[map.length()][24][17];
        for (int[][] values1 : dp) {
            for (int[] values2 : values1) {
                Arrays.fill(values2, -1);
            }
        }

        int minimumTime = computeMinimumTime(map, dp, 0, 6, MAX_TIME_BEFORE_REST, 0);
        if (minimumTime >= INFINITE) {
            return -1;
        }
        return minimumTime;
    }

    private static int computeMinimumTime(String map, int[][][] dp, int locationId, int hours, int timeBeforeRest,
                                          int sameLocationRestTimes) {
        if (locationId == map.length() - 1) {
            return 0;
        }
        if (sameLocationRestTimes == 4) {
            return INFINITE;
        }
        if (dp[locationId][hours][timeBeforeRest] != -1) {
            return dp[locationId][hours][timeBeforeRest];
        }

        int minimumTime = INFINITE;
        // Rest
        if (map.charAt(locationId) == '.') {
            int nextHours = advanceTime(hours, REST_HOURS);
            int candidateTimeWithRest = REST_HOURS +
                    computeMinimumTime(map, dp, locationId, nextHours, MAX_TIME_BEFORE_REST, sameLocationRestTimes + 1);
            minimumTime = Math.min(minimumTime, candidateTimeWithRest);
        }

        // Move forward
        if (timeBeforeRest >= 1 && locationId + 1 < map.length()) {
            if (canMoveForward(map, locationId, hours)) {
                int nextHours = advanceTime(hours, 1);
                int nextTimeBeforeRest = timeBeforeRest - 1;

                int candidateTimeMoving = 1 +
                        computeMinimumTime(map, dp, locationId + 1, nextHours, nextTimeBeforeRest, 0);
                minimumTime = Math.min(minimumTime, candidateTimeMoving);
            }
        }
        dp[locationId][hours][timeBeforeRest] = minimumTime;
        return dp[locationId][hours][timeBeforeRest];
    }

    private static boolean canMoveForward(String map, int locationId, int hours) {
        int nextHours = hours + 1;
        return !(map.charAt(locationId + 1) == '*' && isDarkHours(nextHours));
    }

    private static boolean isDarkHours(int hours) {
        return hours >= 18 || hours <= 6;
    }

    private static int advanceTime(int hours, int hoursToAdvance) {
        return (hours + hoursToAdvance) % 24;
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
