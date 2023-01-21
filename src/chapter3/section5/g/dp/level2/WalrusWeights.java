package chapter3.section5.g.dp.level2;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 20/01/23.
 */
public class WalrusWeights {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] weights = new int[FastReader.nextInt()];
        int maxSum = 0;
        for (int i = 0; i < weights.length; i++) {
            weights[i] = FastReader.nextInt();
            maxSum += weights[i];
        }
        int closestWeight = computeClosestWeight(weights, maxSum);
        outputWriter.printLine(closestWeight);
        outputWriter.flush();
    }

    private static int computeClosestWeight(int[] weights, int maxSum) {
        maxSum = Math.min(maxSum, 2001);
        boolean[] dp = new boolean[maxSum + 1];
        dp[0] = true;
        int bestDistance = Integer.MAX_VALUE;
        int closestWeight = 0;

        for (int plateWeight : weights) {
            for (int weight = maxSum; weight >= 0; weight--) {
                if (weight >= plateWeight) {
                    if (dp[weight - plateWeight]) {
                        dp[weight] = true;
                        int distance = Math.abs(1000 - weight);
                        if (distance < bestDistance
                                || (distance == bestDistance && weight > closestWeight)) {
                            closestWeight = weight;
                            bestDistance = distance;
                        }
                    }
                }
            }
        }
        return closestWeight;
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
