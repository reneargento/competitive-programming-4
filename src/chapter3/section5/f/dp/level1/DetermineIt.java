package chapter3.section5.f.dp.level1;

import java.io.*;
import java.util.Arrays;

/**
 * Created by Rene Argento on 10/01/23.
 */
public class DetermineIt {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int n = Integer.parseInt(data[0]);
            int aN1 = Integer.parseInt(data[1]);

            long result = computeValue(n, aN1);
            outputWriter.printLine(result);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static long computeValue(int n, int aN1) {
        long[][] dp = new long[n + 1][n + 1];
        for (long[] values : dp) {
            Arrays.fill(values, -1);
        }
        dp[n][1] = aN1;
        return computeValue(dp, 1, n);
    }

    private static long computeValue(long[][] dp, int row, int column) {
        if (dp[row][column] != -1) {
            return dp[row][column];
        }

        int n = dp.length - 1;
        long value = 0;

        if (row < column) {
            for (int k = row; k < column; k++) {
                value = Math.max(value, computeValue(dp, row, k) + computeValue(dp, k + 1, column));
            }
        } else if (row < n) {
            long maxValue1 = 0;
            for (int k = row + 1; k <= n; k++) {
                maxValue1 = Math.max(maxValue1, computeValue(dp, k, 1) + computeValue(dp, k, column));
            }

            long maxValue2 = 0;
            for (int k = 1; k < column; k++) {
                maxValue2 = Math.max(maxValue2, computeValue(dp, row, k) + computeValue(dp, n, k));
            }
            value = maxValue1 + maxValue2;
        } else {
            for (int k = 1; k < column; k++) {
                value = Math.max(value, computeValue(dp, row, k) + computeValue(dp, n, k));
            }
        }

        dp[row][column] = value;
        return value;
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
        }

        private static String getLine() throws IOException {
            return reader.readLine();
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
