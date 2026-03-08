package chapter5.section5.b.probability.theory.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/02/26.
 */
public class Tribles {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            double[] probabilities = new double[FastReader.nextInt()];
            int tribles = FastReader.nextInt();
            int generations = FastReader.nextInt();

            for (int i = 0; i < probabilities.length; i++) {
                probabilities[i] = FastReader.nextDouble();
            }

            double deathProbability = computeDeathProbability(tribles, probabilities, generations);
            outputWriter.printLine(String.format("Case #%d: %.7f", t, deathProbability));
        }
        outputWriter.flush();
    }

    private static double computeDeathProbability(int tribles, double[] probabilities, int generations) {
        int upperBound = Math.max(probabilities.length, generations + 1);
        // dp[generation] = death probability
        double[] dp = new double[upperBound];
        for (int g = 1; g <= generations; g++) {
            for (int i = 0; i < probabilities.length; i++) {
                dp[g] += probabilities[i] * Math.pow(dp[g - 1], i);
            }
        }
        return Math.pow(dp[generations], tribles);
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