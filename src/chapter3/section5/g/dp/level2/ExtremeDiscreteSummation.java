package chapter3.section5.g.dp.level2;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 25/01/23.
 */
public class ExtremeDiscreteSummation {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int numbersInSet = FastReader.nextInt();

        while (numbersInSet != 0) {
            int[] decimalValues = new int[numbersInSet];
            for (int i = 0; i < decimalValues.length; i++) {
                decimalValues[i] = (int) ((FastReader.nextDouble() * 10) % 10);
            }
            long sum = computeSum(decimalValues);
            outputWriter.printLine(sum);
            numbersInSet = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long computeSum(int[] decimalValues) {
        // dp[sum][numbersSelected]
        long[][] dp = new long[73][9];
        for (long[] dpValues : dp) {
            Arrays.fill(dpValues, -1);
        }
        return computeSum(decimalValues, dp, 0, 0);
    }

    private static long computeSum(int[] decimalValues, long[][] dp, int sum, int numbersSelected) {
        if (numbersSelected == 8) {
            return sum / 10;
        }
        if (dp[sum][numbersSelected] != -1) {
            return dp[sum][numbersSelected];
        }

        long totalSum = 0;
        for (int decimalValue : decimalValues) {
            totalSum += computeSum(decimalValues, dp, sum + decimalValue, numbersSelected + 1);
        }
        dp[sum][numbersSelected] = totalSum;
        return totalSum;
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
