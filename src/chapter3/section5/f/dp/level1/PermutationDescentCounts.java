package chapter3.section5.f.dp.level1;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 14/01/23.
 */
public class PermutationDescentCounts {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int dataSet = FastReader.nextInt();
        int[][] dp = initDp();

        for (int d = 0; d < dataSet; d++) {
            int dataSetNumber = FastReader.nextInt();
            int order = FastReader.nextInt();
            int value = FastReader.nextInt();

            int pdc = computePDC(dp, order, value);
            outputWriter.printLine(String.format("%d %d", dataSetNumber, pdc));
        }
        outputWriter.flush();
    }

    private static int[][] initDp() {
        // dp[order][value] = descent count
        int[][] dp = new int[101][101];
        for (int[] values : dp) {
            Arrays.fill(values, -1);
        }
        return dp;
    }

    private static int computePDC(int[][] dp, int order, int value) {
        if (value == 0 || value == (order - 1)) {
            return 1;
        }
        if (dp[order][value] != -1) {
            return dp[order][value];
        }
        dp[order][value] = ((value + 1) * computePDC(dp, order - 1, value) +
                (order - value) * computePDC(dp, order - 1, value - 1)) % 1001113;
        return dp[order][value];
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
