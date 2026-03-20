package chapter5.section5.b.probability.theory.harder;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 15/03/26.
 */
public class PollyGone {

    private static class Box {
        int energyRequired;
        int probabilityInside;

        public Box(int energyRequired, int probabilityInside) {
            this.energyRequired = energyRequired;
            this.probabilityInside = probabilityInside;
        }
    }

    private static final long MAX_VALUE = 10000000;
    private static final double EPSILON = 0.00000001;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        Box[] boxes = new Box[FastReader.nextInt()];
        int minimumProbability = (int) ((FastReader.nextDouble() + EPSILON) * 10000);
        for (int b = 0; b < boxes.length; b++) {
            boxes[b] = new Box(FastReader.nextInt(), (int) ((FastReader.nextDouble() + EPSILON) * 10000));
        }

        long minimumEnergy = computeMinimumEnergy(boxes, minimumProbability);
        outputWriter.printLine(minimumEnergy);
        outputWriter.flush();
    }

    private static long computeMinimumEnergy(Box[] boxes, int minimumProbability) {
        // dp[box id][current probability]
        long[][] dp = new long[boxes.length + 1][minimumProbability + 1];
        for (long[] values : dp) {
            Arrays.fill(values, -1);
        }
        return computeMinimumEnergy(0, 0, dp, boxes, minimumProbability);
    }

    private static long computeMinimumEnergy(int boxId, int currentProbability, long[][] dp, Box[] boxes,
                                             int minimumProbability) {
        if (currentProbability >= minimumProbability) {
            return 0;
        }
        if (boxId == boxes.length) {
            return MAX_VALUE;
        }
        if (dp[boxId][currentProbability] != -1) {
            return dp[boxId][currentProbability];
        }

        Box box = boxes[boxId];
        long energyOpeningBox = box.energyRequired +
                computeMinimumEnergy(boxId + 1, currentProbability + box.probabilityInside, dp, boxes, minimumProbability);
        long energyNotOpeningBox = computeMinimumEnergy(boxId + 1, currentProbability, dp, boxes, minimumProbability);
        dp[boxId][currentProbability] = Math.min(energyOpeningBox, energyNotOpeningBox);
        return dp[boxId][currentProbability];
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
