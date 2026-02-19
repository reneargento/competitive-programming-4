package chapter5.section5.b.probability.theory.harder;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 10/02/26.
 */
public class WinningStreak {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int games = FastReader.nextInt();

        while (games != 0) {
            double winProbability = FastReader.nextDouble();
            double expectedValue = computeExpectedValue(games, winProbability);
            outputWriter.printLine(String.format("%.6f", expectedValue));
            games = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static double computeExpectedValue(int games, double winProbability) {
        // dp[games left][highest win streak]
        double[][] dp = new double[games + 1][games + 1];
        for (double[] values : dp) {
            Arrays.fill(values, -1.0);
        }
        double[] probabilityPower = computeProbabilityPower(winProbability);
        return computeExpectedValue(games, 0, winProbability, dp, probabilityPower);
    }

    private static double computeExpectedValue(int gamesLeft, int highestWinStreak, double winProbability,
                                               double[][] dp, double[] probabilityPower) {
        if (gamesLeft == 0) {
            return highestWinStreak;
        }
        if (dp[gamesLeft][highestWinStreak] != -1) {
            return dp[gamesLeft][highestWinStreak];
        }

        double loseProbability = 1 - winProbability;
        // Lose next game
        double expectedValue = loseProbability * computeExpectedValue(gamesLeft - 1, highestWinStreak,
                winProbability, dp, probabilityPower);
        // Win next games and then lose next
        for (int wins = 1; wins < gamesLeft; wins++) {
            double aggregateWinProbability = probabilityPower[wins];
            int nextHighestWinStreak = Math.max(highestWinStreak, wins);
            expectedValue += aggregateWinProbability * loseProbability *
                    computeExpectedValue(gamesLeft - wins - 1, nextHighestWinStreak, winProbability, dp, probabilityPower);
        }
        // Win all next games
        double aggregateWinAllProbability = probabilityPower[gamesLeft];
        int nextHighestWinStreak = Math.max(highestWinStreak, gamesLeft);
        expectedValue += aggregateWinAllProbability * computeExpectedValue(0, nextHighestWinStreak,
                winProbability, dp, probabilityPower);

        dp[gamesLeft][highestWinStreak] = expectedValue;
        return expectedValue;
    }

    private static double[] computeProbabilityPower(double probability) {
        double[] probabilityPower = new double[501];
        probabilityPower[0] = 1;
        probabilityPower[1] = probability;
        for (int i = 2; i < probabilityPower.length; i++) {
            probabilityPower[i] = probabilityPower[i - 1] * probability;
        }
        return probabilityPower;
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

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
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
