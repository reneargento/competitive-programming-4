package chapter5.section5.b.probability.theory.harder;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 12/02/26.
 */
public class LetsDance {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int gentlemen = FastReader.nextInt();
        int ladies = FastReader.nextInt();

        while (gentlemen != 0 || ladies != 0) {
            int candies = FastReader.nextInt();
            double danceProbability = computeDanceProbability(gentlemen, ladies, candies);
            outputWriter.printLine(String.format("%.7f", danceProbability));

            gentlemen = FastReader.nextInt();
            ladies = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static double computeDanceProbability(int gentlemen, int ladies, int candies) {
        // dp[gentlemen that took a candy][candies left]
        double[][] dp = new double[candies + 1][candies + 1];
        for (double[] values : dp) {
            Arrays.fill(values, -1);
        }
        return computeDanceProbability(0, candies, gentlemen, ladies, dp);
    }

    private static double computeDanceProbability(int gentlemenThatTookCandy, int candiesLeft, int gentlemanTotal, int ladiesTotal, double[][] dp) {
        if (candiesLeft == 0) {
            return (gentlemenThatTookCandy % 2 == 0) ? 1 : 0;
        }
        if (dp[gentlemenThatTookCandy][candiesLeft] != -1) {
            return dp[gentlemenThatTookCandy][candiesLeft];
        }

        double candyToMenProbability = gentlemanTotal / (double) (gentlemanTotal + ladiesTotal);
        double candyToLadiesProbability = 1 - candyToMenProbability;
        dp[gentlemenThatTookCandy][candiesLeft] =
                candyToMenProbability * computeDanceProbability(gentlemenThatTookCandy + 1, candiesLeft - 1, gentlemanTotal, ladiesTotal, dp)
                        + candyToLadiesProbability * computeDanceProbability(gentlemenThatTookCandy, candiesLeft - 1, gentlemanTotal, ladiesTotal, dp);
        return dp[gentlemenThatTookCandy][candiesLeft];
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
