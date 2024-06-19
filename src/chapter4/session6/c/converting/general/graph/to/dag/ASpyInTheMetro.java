package chapter4.session6.c.converting.general.graph.to.dag;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 06/06/24.
 */
public class ASpyInTheMetro {

    private static final int INFINITE = 100000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int stations = FastReader.nextInt();
        int caseId = 1;

        while (stations != 0) {
            int appointmentTime = FastReader.nextInt();
            int[] travelTimes = new int[stations - 1];
            for (int i = 0; i < travelTimes.length; i++) {
                travelTimes[i] = FastReader.nextInt();
            }

            int[] firstStationTrainTimes = new int[FastReader.nextInt()];
            for (int i = 0; i < firstStationTrainTimes.length; i++) {
                firstStationTrainTimes[i] = FastReader.nextInt();
            }
            int[] lastStationTrainTimes = new int[FastReader.nextInt()];
            for (int i = 0; i < lastStationTrainTimes.length; i++) {
                lastStationTrainTimes[i] = FastReader.nextInt();
            }

            int leastWaitingTime = computeLeastWaitingTime(appointmentTime, travelTimes, firstStationTrainTimes,
                    lastStationTrainTimes);
            outputWriter.print(String.format("Case Number %d: ", caseId));
            if (leastWaitingTime >= INFINITE) {
                outputWriter.printLine("impossible");
            } else {
                outputWriter.printLine(leastWaitingTime);
            }

            caseId++;
            stations = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeLeastWaitingTime(int appointmentTime, int[] travelTimes, int[] firstStationTrainTimes,
                                               int[] lastStationTrainTimes) {
        boolean[][] hasTrainLeftToRight = computeHasTrain(travelTimes, firstStationTrainTimes, true, appointmentTime);
        boolean[][] hasTrainRightToLeft = computeHasTrain(travelTimes, lastStationTrainTimes, false, appointmentTime);

        // dp[stationId][time left]
        int[][] dp = new int[travelTimes.length + 1][appointmentTime + 1];
        for (int[] values : dp) {
            Arrays.fill(values, -1);
        }
        return computeLeastWaitingTime(travelTimes, hasTrainLeftToRight, hasTrainRightToLeft, dp, 0, 0,
                appointmentTime);
    }

    private static int computeLeastWaitingTime(int[] travelTimes, boolean[][] hasTrainLeftToRight,
                                               boolean[][] hasTrainRightToLeft, int[][] dp, int timePassed,
                                               int stationId, int timeLeft) {
        if (timeLeft < 0) {
            return INFINITE;
        }
        if (stationId == travelTimes.length && timeLeft == 0) {
            return 0;
        }
        if (dp[stationId][timeLeft] != -1) {
            return dp[stationId][timeLeft];
        }

        int waitingTimeLeftToRight = INFINITE;
        int waitingTimeRightToLeft = INFINITE;
        int waitingTimeNotMoving;

        if (hasTrainLeftToRight[stationId][timePassed] && stationId != travelTimes.length) {
            int timePassedLeftToRight = timePassed + travelTimes[stationId];
            int nextTimeLeftLeftToRight = timeLeft - travelTimes[stationId];
            waitingTimeLeftToRight =
                    computeLeastWaitingTime(travelTimes, hasTrainLeftToRight, hasTrainRightToLeft, dp,
                            timePassedLeftToRight, stationId + 1, nextTimeLeftLeftToRight);
        }
        if (hasTrainRightToLeft[stationId][timePassed] && stationId > 0) {
            int timePassedRightToLeft = timePassed + travelTimes[stationId - 1];
            int nextTimeLeftRightToLeft = timeLeft - travelTimes[stationId - 1];
            waitingTimeRightToLeft =
                    computeLeastWaitingTime(travelTimes, hasTrainLeftToRight, hasTrainRightToLeft, dp,
                            timePassedRightToLeft, stationId - 1, nextTimeLeftRightToLeft);
        }
        waitingTimeNotMoving = 1 + computeLeastWaitingTime(travelTimes, hasTrainLeftToRight, hasTrainRightToLeft, dp,
                timePassed + 1, stationId, timeLeft - 1);

        int leastWaitingTime = Math.min(waitingTimeLeftToRight, waitingTimeRightToLeft);
        leastWaitingTime = Math.min(leastWaitingTime, waitingTimeNotMoving);
        dp[stationId][timeLeft] = leastWaitingTime;
        return dp[stationId][timeLeft];
    }

    private static boolean[][] computeHasTrain(int[] travelTimes, int[] stationTrainTimes, boolean leftToRight,
                                               int maxTime) {
        boolean[][] hasTrain = new boolean[travelTimes.length + 1][maxTime + 1];

        int startStationId = leftToRight ? 0 : travelTimes.length;
        int endStationId = leftToRight ? travelTimes.length : 0;
        int iterator = leftToRight ? 1 : -1;

        for (int trainTime : stationTrainTimes) {
            for (int currentStationId = startStationId; currentStationId != endStationId
                    && trainTime < hasTrain[0].length; currentStationId += iterator) {
                hasTrain[currentStationId][trainTime] = true;
                int adjustedStationId = leftToRight ? currentStationId : currentStationId - 1;
                trainTime += travelTimes[adjustedStationId];
            }
        }
        return hasTrain;
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
