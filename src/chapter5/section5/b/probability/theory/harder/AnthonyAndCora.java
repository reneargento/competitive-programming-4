package chapter5.section5.b.probability.theory.harder;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 10/02/26.
 */
public class AnthonyAndCora {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int anthonyPoints = FastReader.nextInt();
        int coraPoints = FastReader.nextInt();
        int rounds = anthonyPoints + coraPoints - 1;
        double[] winProbabilities = new double[rounds];
        for (int i = 0; i < winProbabilities.length; i++) {
            winProbabilities[i] = FastReader.nextDouble();
        }

        double winProbability = computeGameWinProbability(anthonyPoints, coraPoints, winProbabilities);
        outputWriter.printLine(winProbability);
        outputWriter.flush();
    }

    private static double computeGameWinProbability(int anthonyPoints, int coraPoints, double[] winProbabilities) {
        // dp[anthony points][cora points]
        double[][] dp = new double[anthonyPoints + 1][coraPoints + 1];
        for (double[] values : dp) {
            Arrays.fill(values, -1);
        }
        return computeGameWinProbability(anthonyPoints, coraPoints, 0, winProbabilities, dp);
    }

    private static double computeGameWinProbability(int anthonyPoints, int coraPoints, int round,
                                                    double[] winProbabilities, double[][] dp) {
        if (anthonyPoints == 0) {
            return 0;
        }
        if (coraPoints == 0) {
            return 1;
        }
        if (dp[anthonyPoints][coraPoints] != -1) {
            return dp[anthonyPoints][coraPoints];
        }

        double loseProbability = 1 - winProbabilities[round];
        double winProbability =
                winProbabilities[round] * computeGameWinProbability(anthonyPoints, coraPoints - 1, round + 1,
                        winProbabilities, dp) +
                        loseProbability * computeGameWinProbability(anthonyPoints - 1, coraPoints, round + 1, winProbabilities, dp);

        dp[anthonyPoints][coraPoints] = winProbability;
        return winProbability;
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
