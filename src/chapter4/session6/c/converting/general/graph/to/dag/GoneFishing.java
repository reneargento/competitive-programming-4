package chapter4.session6.c.converting.general.graph.to.dag;

import java.io.*;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by Rene Argento on 04/06/24.
 */
public class GoneFishing {

    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int lakesNumber = inputReader.nextInt();
        int caseId = 1;

        while (lakesNumber != 0) {
            int minutesAvailable = inputReader.nextInt() * 60;
            int[] initialCaughtFish = readInput(inputReader, lakesNumber, 0, 1);
            int[] fishDecreaseRate = readInput(inputReader, lakesNumber, 0, 1);
            int[] minutesToTravel = readInput(inputReader, lakesNumber, 1, 5);

            int[] bestTimeSpent = new int[initialCaughtFish.length];

            int maxFish = computeTripPlan(initialCaughtFish, fishDecreaseRate, minutesToTravel, minutesAvailable,
                    bestTimeSpent);
            if (caseId > 1) {
                outputWriter.printLine();
            }
            outputWriter.print(bestTimeSpent[0]);
            for (int i = 1; i < bestTimeSpent.length; i++) {
                outputWriter.print(String.format(", %d", bestTimeSpent[i]));
            }
            outputWriter.printLine();
            outputWriter.printLine(String.format("Number of fish expected: %d", maxFish));

            caseId++;
            lakesNumber = inputReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int[] readInput(InputReader inputReader, int size, int startIndex, int multiple) throws IOException {
        int[] values = new int[size];
        for (int i = startIndex; i < values.length; i++) {
            values[i] = inputReader.nextInt() * multiple;
        }
        return values;
    }

    private static int computeTripPlan(int[] initialCaughtFish, int[] fishDecreaseRate, int[] minutesToTravel,
                                        int minutesAvailable, int[] bestTimeSpent) {
        // dp[lake id][minutes left]
        int[][] dp = new int[initialCaughtFish.length][minutesAvailable + 1];
        for (int[] values : dp) {
            Arrays.fill(values, -1);
        }

        int[][] timePerLake = new int[initialCaughtFish.length][minutesAvailable + 1];
        int maxFish = computeTripPlan(initialCaughtFish, fishDecreaseRate, minutesToTravel, dp, timePerLake,
                0, minutesAvailable);

        for (int lakeId = 0; lakeId < initialCaughtFish.length && minutesAvailable > 0; lakeId++) {
            int timeSpent = timePerLake[lakeId][minutesAvailable];
            bestTimeSpent[lakeId] = timeSpent;
            minutesAvailable -= timeSpent;

            if (lakeId < initialCaughtFish.length - 1) {
                minutesAvailable -= minutesToTravel[lakeId + 1];
            }
        }
        return maxFish;
    }

    private static int computeTripPlan(int[] initialCaughtFish, int[] fishDecreaseRate, int[] minutesToTravel,
                                        int[][] dp, int[][] timePerLake, int lakeId, int minutesLeft) {
        int lakesNumber = initialCaughtFish.length;
        if (lakeId == dp.length || minutesLeft <= 0) {
            return 0;
        }
        if (dp[lakeId][minutesLeft] != -1) {
            return dp[lakeId][minutesLeft];
        }

        int maxFish = 0;
        int bestTimeInLake = 0;
        int travelTime = 0;

        int fishCaught = 0;
        int timeRemaining = minutesLeft;
        int fishAvailable = initialCaughtFish[lakeId];
        int timeSpentFishing = 0;
        if (lakeId < lakesNumber - 1) {
            travelTime += minutesToTravel[lakeId + 1];
        }

        while (timeRemaining >= 0) {
            int candidatePlanFish = computeTripPlan(initialCaughtFish, fishDecreaseRate, minutesToTravel, dp,
                    timePerLake, lakeId + 1, timeRemaining - travelTime);

            int candidateMaxFish = candidatePlanFish + fishCaught;
            if (candidateMaxFish > maxFish) {
                maxFish = candidateMaxFish;
                bestTimeInLake = timeSpentFishing;
            } else if (candidateMaxFish == maxFish && timeSpentFishing > bestTimeInLake) {
                bestTimeInLake = timeSpentFishing;
            }

            timeSpentFishing += 5;
            fishCaught += fishAvailable;

            fishAvailable -= fishDecreaseRate[lakeId];
            fishAvailable = Math.max(fishAvailable, 0);
            timeRemaining -= 5;
        }

        dp[lakeId][minutesLeft] = maxFish;
        timePerLake[lakeId][minutesLeft] = bestTimeInLake;
        return dp[lakeId][minutesLeft];
    }

    private static class InputReader {
        private final InputStream stream;
        private final byte[] buf = new byte[8192];
        private int curChar, snumChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public int snext() throws IOException {
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

        public int nextInt() throws IOException {
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

        public boolean isSpaceChar(int c) {
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
