package chapter3.section2.l.recursive.backtracking.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 13/02/22.
 */
public class FruitBaskets {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] weights = new int[FastReader.nextInt()];
        for (int i = 0; i < weights.length; i++) {
            weights[i] = FastReader.nextInt();
        }

        long totalWeightLessThan200 = computeTotalWeightLessThan200(weights, 0, 0);
        long totalWeight = computeTotalWeight(weights);
        long targetWeight = totalWeight - totalWeightLessThan200;

        outputWriter.printLine(targetWeight);
        outputWriter.flush();
    }

    private static long computeTotalWeightLessThan200(int[] weights, long currentWeight, int index) {
        if (currentWeight >= 200) {
            return 0;
        }
        if (index == weights.length) {
            return currentWeight;
        }

        long weightWithItem = computeTotalWeightLessThan200(weights, currentWeight + weights[index], index + 1);
        long weightWithoutItem = computeTotalWeightLessThan200(weights, currentWeight, index + 1);
        return weightWithItem + weightWithoutItem;
    }

    private static long computeTotalWeight(int[] weights) {
        long totalWeight = 0;
        long totalSubsets = (long) Math.pow(2, weights.length);
        long timesThatItemAppears = totalSubsets / 2;

        for (int weight : weights) {
            totalWeight += weight * timesThatItemAppears;
        }
        return totalWeight;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
