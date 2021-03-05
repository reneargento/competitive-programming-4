package chapter2.section2.h.bit.manipulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/03/21.
 */
public class TheMostPotentCorner {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        String dimensionString = FastReader.getLine();

        while (dimensionString != null) {
            int dimension = Integer.parseInt(dimensionString);
            int totalCorners = (int) Math.pow(2, dimension);

            int[] weights = new int[totalCorners];
            for (int i = 0; i < weights.length; i++) {
                weights[i] = FastReader.nextInt();
            }

            int[] potencies = computePotencies(weights, dimension);
            int maxPotencySum = getMaxPotencySum(weights, potencies, dimension);
            System.out.println(maxPotencySum);

            dimensionString = FastReader.getLine();
        }
    }

    private static int[] computePotencies(int[] weights, int dimension) {
        int[] potencies = new int[weights.length];

        for (int corner = 0; corner < weights.length; corner++) {
            int potency = 0;
            int mask = 1;

            for (int bitIndex = 0; bitIndex < dimension; bitIndex++) {
                int neighbor = mask ^ corner;
                potency += weights[neighbor];
                mask <<= 1;
            }
            potencies[corner] = potency;
        }
        return potencies;
    }

    private static int getMaxPotencySum(int[] weights, int[] potencies, int dimension) {
        int maxSum = 0;

        for (int corner = 0; corner < weights.length; corner++) {
            int mask = 1;

            for (int bitIndex = 0; bitIndex < dimension; bitIndex++) {
                int neighbor = mask ^ corner;
                int sum = potencies[corner] + potencies[neighbor];
                maxSum = Math.max(maxSum, sum);
                mask <<= 1;
            }
        }
        return maxSum;
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

        private static String getLine() throws IOException {
            return reader.readLine();
        }
    }
}
