package chapter5.section5.b.probability.theory.harder;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 09/03/26.
 */
public class Bond {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int bonds = FastReader.nextInt();
        double[][] probabilities = new double[bonds][bonds];
        for (int row = 0; row < probabilities.length; row++) {
            for (int column = 0; column < probabilities[0].length; column++) {
                probabilities[row][column] = FastReader.nextInt() / 100.0;
            }
        }

        double highestProbability = computeHighestProbability(probabilities);
        outputWriter.printLine(highestProbability);
        outputWriter.flush();
    }

    private static double computeHighestProbability(double[][] probabilities) {
        // dp[last bond checked][missions taken]
        double[][] dp = new double[probabilities.length][2 << probabilities.length + 1];
        for (double[] values : dp) {
            Arrays.fill(values, -1);
        }
        return computeHighestProbability(0, 0, dp, probabilities) * 100;
    }

    private static double computeHighestProbability(int bondId, int bitmask, double[][] dp, double[][] probabilities) {
        if (bondId == dp.length) {
            return 1;
        }
        if (dp[bondId][bitmask] != -1) {
            return dp[bondId][bitmask];
        }

        double highestProbability = 0;
        for (int missionId = 0; missionId < probabilities.length; missionId++) {
            if (((1 << missionId) & bitmask) == 0) {
                int nextBitmask = bitmask | (1 << missionId);
                double probability = probabilities[bondId][missionId] *
                        computeHighestProbability(bondId + 1, nextBitmask, dp, probabilities);
                highestProbability = Math.max(highestProbability, probability);
            }
        }
        dp[bondId][bitmask] = highestProbability;
        return highestProbability;
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
