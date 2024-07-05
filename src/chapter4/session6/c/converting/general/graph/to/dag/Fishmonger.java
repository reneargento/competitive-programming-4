package chapter4.session6.c.converting.general.graph.to.dag;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 30/06/24.
 */
public class Fishmonger {

    private static class Result {
        int totalTolls;
        int travellingTime;

        public Result(int totalTolls, int travellingTime) {
            this.totalTolls = totalTolls;
            this.travellingTime = travellingTime;
        }
    }

    private static final int INFINITE = 10000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int states = FastReader.nextInt();
        int availableTime = FastReader.nextInt();

        while (states != 0) {
            int[][] times = readTable(states);
            int[][] tolls = readTable(states);

            Result result = computeBestRoute(times, tolls, availableTime);
            outputWriter.printLine(String.format("%d %d", result.totalTolls, result.travellingTime));

            states = FastReader.nextInt();
            availableTime = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Result computeBestRoute(int[][] times, int[][] tolls, int availableTime) {
        // dp[state id][time left] = minimum tolls paid
        Result[][] dp = new Result[times.length][availableTime + 1];
        return computeBestRoute(times, tolls, dp, 0, availableTime);
    }

    private static Result computeBestRoute(int[][] times, int[][] tolls, Result[][] dp, int stateId, int timeLeft) {
        if (timeLeft < 0) {
            return new Result(INFINITE, INFINITE);
        }
        if (stateId == times.length - 1) {
            return new Result(0, 0);
        }
        if (dp[stateId][timeLeft] != null) {
            return dp[stateId][timeLeft];
        }

        Result bestResult = null;
        for (int nextStateId = 0; nextStateId < times.length; nextStateId++) {
            if (nextStateId == stateId) {
                continue;
            }
            Result resultCandidate =
                    computeBestRoute(times, tolls, dp, nextStateId, timeLeft - times[stateId][nextStateId]);
            int tollsCandidate = tolls[stateId][nextStateId] + resultCandidate.totalTolls;
            int timeCandidate = times[stateId][nextStateId] + resultCandidate.travellingTime;

            if (bestResult == null || tollsCandidate < bestResult.totalTolls) {
                bestResult = new Result(tollsCandidate, timeCandidate);
            }
        }

        dp[stateId][timeLeft] = bestResult;
        return dp[stateId][timeLeft];
    }

    private static int[][] readTable(int states) throws IOException {
        int[][] table = new int[states][states];

        for (int row = 0; row < table.length; row++) {
            for (int column = 0; column < table[0].length; column++) {
                table[row][column] = FastReader.nextInt();
            }
        }
        return table;
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
