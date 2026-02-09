package chapter5.section5.a.probability.theory.easier;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 07/02/26.
 */
public class TennisContest {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int winsNeeded = FastReader.nextInt();
            double winProbability = FastReader.nextDouble();
            double winTitleProbability = computeWinTitleProbability(winsNeeded, winProbability);
            outputWriter.printLine(String.format("%.2f", winTitleProbability));
        }
        outputWriter.flush();
    }

    private static double computeWinTitleProbability(int winsNeeded, double winProbability) {
        int totalMatches = 2 * winsNeeded - 1;
        double loseProbability = 1 - winProbability;

        // dp[match id][current wins]
        double[][] dp = new double[totalMatches + 1][totalMatches + 1];
        for (double[] values : dp) {
            Arrays.fill(values, -1);
        }
        return computeWinTitleProbability(0, 0, winsNeeded, totalMatches, winProbability, loseProbability, dp);
    }

    private static double computeWinTitleProbability(int matchId, int currentWins, int winsNeeded, int totalMatches,
                                                     double winProbability, double loseProbability, double[][] dp) {
        if (currentWins >= winsNeeded) {
            return 1;
        }
        if (matchId == totalMatches) {
            return 0;
        }
        if (dp[matchId][currentWins] != -1) {
            return dp[matchId][currentWins];
        }

        dp[matchId][currentWins] = winProbability * computeWinTitleProbability(matchId + 1, currentWins + 1,
                winsNeeded, totalMatches, winProbability, loseProbability, dp) +
                loseProbability * computeWinTitleProbability(matchId + 1, currentWins,
                        winsNeeded, totalMatches, winProbability, loseProbability, dp);
        return dp[matchId][currentWins];
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
