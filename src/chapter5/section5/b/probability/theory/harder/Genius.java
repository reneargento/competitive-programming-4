package chapter5.section5.b.probability.theory.harder;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 12/03/26.
 */
public class Genius {

    private static class TeamsMatch {
        int teamRound1;
        int teamRound2a;
        int teamRound2b;

        public TeamsMatch(int teamRound1, int teamRound2a, int teamRound2b) {
            this.teamRound1 = teamRound1;
            this.teamRound2a = teamRound2a;
            this.teamRound2b = teamRound2b;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int winnersNeeded = FastReader.nextInt();
        int tournaments = FastReader.nextInt();
        int p = FastReader.nextInt();
        int q = FastReader.nextInt();
        int firstTerm = FastReader.nextInt();

        int[] predictedWinners = computePredictedWinners(tournaments, p, q, firstTerm);
        double[] winnersProbability = new double[tournaments];

        for (int t = 0; t < tournaments; t++) {
            int[] weights = new int[4];
            for (int i = 0; i < weights.length; i++) {
                weights[i] = FastReader.nextInt();
            }
            winnersProbability[t] = computeWinnerProbability(weights, predictedWinners[t]);
        }
        double geniusProbability = computeGeniusProbability(winnersProbability, winnersNeeded);

        outputWriter.printLine(geniusProbability);
        outputWriter.flush();
    }

    private static int[] computePredictedWinners(int tournaments, int p, int q, int firstTerm) {
        int[] predictedWinners = new int[tournaments];
        predictedWinners[0] = firstTerm;

        for (int i = 1; i < predictedWinners.length; i++) {
            predictedWinners[i] = (predictedWinners[i - 1] * p) % q;
        }
        for (int i = 0; i < predictedWinners.length; i++) {
            predictedWinners[i] %= 4;
        }
        return predictedWinners;
    }

    private static double computeWinnerProbability(int[] weights, int winnerPredicted) {
        TeamsMatch teamsMatch = getTeamsMatch(winnerPredicted);
        double firstMatchProbability = computeMatchWinProbability(weights, winnerPredicted, teamsMatch.teamRound1);
        double secondMatchProbability = computeMatchWinProbability(weights, teamsMatch.teamRound2a, teamsMatch.teamRound2b)
                * computeMatchWinProbability(weights, winnerPredicted, teamsMatch.teamRound2a)
                + computeMatchWinProbability(weights, teamsMatch.teamRound2b, teamsMatch.teamRound2a)
                * computeMatchWinProbability(weights, winnerPredicted, teamsMatch.teamRound2b);
        return firstMatchProbability * secondMatchProbability;
    }

    private static TeamsMatch getTeamsMatch(int team) {
        if (team == 0) {
            return new TeamsMatch(1, 2, 3);
        }
        if (team == 1) {
            return new TeamsMatch(0, 2, 3);
        }
        if (team == 2) {
            return new TeamsMatch(3, 0, 1);
        }
        return new TeamsMatch(2, 0, 1);
    }

    private static double computeMatchWinProbability(int[] weights, int team1, int team2) {
        return weights[team1] / (double) (weights[team1] + weights[team2]);
    }

    private static double computeGeniusProbability(double[] winnersProbability, int winnersNeeded) {
        // dp[tournament id][wins so far]
        double[][] dp = new double[winnersProbability.length][winnersProbability.length];
        for (double[] values : dp) {
            Arrays.fill(values, -1);
        }
        return computeGeniusProbability(0, 0, dp, winnersProbability, winnersNeeded);
    }

    private static double computeGeniusProbability(int tournamentId, int wins, double[][] dp,
                                                   double[] winnersProbability, int winnersNeeded) {
        if (wins == winnersNeeded) {
            return 1;
        }
        if (tournamentId == dp.length) {
            return 0;
        }
        if (dp[tournamentId][wins] != -1) {
            return dp[tournamentId][wins];
        }

        double probability = winnersProbability[tournamentId] *
                computeGeniusProbability(tournamentId + 1, wins + 1, dp, winnersProbability, winnersNeeded)
                + (1 - winnersProbability[tournamentId]) *
                computeGeniusProbability(tournamentId + 1, wins, dp, winnersProbability, winnersNeeded);
        dp[tournamentId][wins] = probability;
        return probability;
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
