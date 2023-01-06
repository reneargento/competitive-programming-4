package chapter3.section5.f.dp.level1;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 01/01/23.
 */
public class SpidermansWorkout {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int[] distances = new int[FastReader.nextInt()];
            for (int i = 0; i < distances.length; i++) {
                distances[i] = FastReader.nextInt();
            }

            String bestDirections = computeBestDirections(distances);
            outputWriter.printLine(bestDirections);
        }
        outputWriter.flush();
    }

    private static String computeBestDirections(int[] distances) {
        // dp[index][height] = maxHeight
        int[][] dp = new int[distances.length + 1][1001];
        for (int[] values : dp) {
            Arrays.fill(values, -1);
        }
        int maxHeight = computeBestDirections(distances, dp, 0, 0);
        if (maxHeight == Integer.MAX_VALUE) {
            return "IMPOSSIBLE";
        }

        StringBuilder path = new StringBuilder();
        constructPath(dp, distances, path, 0, 0);
        return path.toString();
    }

    private static int computeBestDirections(int[] distances, int[][] dp, int index, int height) {
        if (height < 0) {
            return Integer.MAX_VALUE;
        }

        if (index == distances.length) {
            if (height == 0) {
                dp[index][height] = 0;
                return 0;
            }
            return Integer.MAX_VALUE;
        }

        if (dp[index][height] != -1) {
            return dp[index][height];
        }

        int maxHeightClimbingUp = computeBestDirections(distances, dp, index + 1, height + distances[index]);
        int maxHeightClimbingDown = computeBestDirections(distances, dp, index + 1, height - distances[index]);

        dp[index][height] = Math.min(maxHeightClimbingUp, maxHeightClimbingDown);
        dp[index][height] = Math.max(dp[index][height], height);
        return dp[index][height];
    }

    private static void constructPath(int[][] dp, int[] distances, StringBuilder path, int index, int height) {
        if (index == distances.length) {
            return;
        }

        if (computeBestDirections(distances, dp, index + 1, height + distances[index]) <
                computeBestDirections(distances, dp, index + 1, height - distances[index])) {
            path.append("U");
            constructPath(dp, distances, path, index + 1, height + distances[index]);
        } else {
            path.append("D");
            constructPath(dp, distances, path, index + 1, height - distances[index]);
        }
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
