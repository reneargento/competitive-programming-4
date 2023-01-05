package chapter3.section5.f.dp.level1;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 01/01/23.
 */
public class Nikola {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] costs = new int[FastReader.nextInt() + 1];

        for (int i = 1; i < costs.length; i++) {
            costs[i] = FastReader.nextInt();
        }
        long smallestCost = computeSmallestCost(costs);
        outputWriter.printLine(smallestCost);
        outputWriter.flush();
    }

    private static long computeSmallestCost(int[] costs) {
        // dp[position][previousJump] = cost
        long[][] dp = new long[costs.length][costs.length];
        for (long[] values : dp) {
            Arrays.fill(values, -1);
        }
        return costs[2] + computeSmallestCost(costs, dp, 2, 1);
    }

    private static long computeSmallestCost(int[] costs, long[][] dp, int position, int lastJump) {
        if (position == dp.length - 1) {
            return 0;
        }

        if (dp[position][lastJump] != -1) {
            return dp[position][lastJump];
        }

        long costJumpingBackward = Long.MAX_VALUE;
        int positionBackward = position - lastJump;
        if (positionBackward >= 1) {
            costJumpingBackward = costs[positionBackward] + computeSmallestCost(costs, dp, positionBackward, lastJump);
        }

        long costJumpingForward = Long.MAX_VALUE;
        int positionForward = position + lastJump + 1;
        if (positionForward < dp.length) {
            costJumpingForward = costs[positionForward] + computeSmallestCost(costs, dp, positionForward, lastJump + 1);
        }

        dp[position][lastJump] = Math.min(costJumpingBackward, costJumpingForward);
        return dp[position][lastJump];
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
