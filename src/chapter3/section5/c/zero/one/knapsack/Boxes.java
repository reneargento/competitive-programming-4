package chapter3.section5.c.zero.one.knapsack;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 26/11/22.
 */
public class Boxes {

    private static class Box {
        int weight;
        int maximumLoad;

        public Box(int weight, int maximumLoad) {
            this.weight = weight;
            this.maximumLoad = maximumLoad;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int boxesNumber = FastReader.nextInt();

        while (boxesNumber != 0) {
            Box[] boxes = new Box[boxesNumber];
            for (int i = 0; i < boxes.length; i++) {
                boxes[i] = new Box(FastReader.nextInt(), FastReader.nextInt());
            }
            int maximumBoxesStacked = computeMaximumBoxesStacked(boxes);
            outputWriter.printLine(maximumBoxesStacked);

            boxesNumber = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeMaximumBoxesStacked(Box[] boxes) {
        // dp[boxes from i..N][length] = minimum_weight
        int[][] dp = new int[boxes.length + 1][boxes.length + 1];

        // Base cases
        for (int length = 1; length < dp[0].length; length++) {
            dp[boxes.length][length] = Integer.MAX_VALUE;
        }

        for (int boxId = dp.length - 2; boxId >= 0; boxId--) {
            for (int length = 1; length < dp[0].length; length++) {
                int weightWithoutBox = dp[boxId + 1][length];
                int weightWithBox = Integer.MAX_VALUE;
                if (dp[boxId + 1][length - 1] <= boxes[boxId].maximumLoad) {
                    weightWithBox = dp[boxId + 1][length - 1] + boxes[boxId].weight;
                }
                dp[boxId][length] = Math.min(weightWithoutBox, weightWithBox);
            }
        }

        for (int length = boxes.length; length >= 0; length--) {
            if (dp[0][length] != Integer.MAX_VALUE) {
                return length;
            }
        }
        return 0;
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
