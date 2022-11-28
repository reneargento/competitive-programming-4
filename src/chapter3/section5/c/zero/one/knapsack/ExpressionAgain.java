package chapter3.section5.c.zero.one.knapsack;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 25/11/22.
 */
public class ExpressionAgain {

    private static class Result {
        int maxValue;
        int minValue;

        public Result(int maxValue, int minValue) {
            this.maxValue = maxValue;
            this.minValue = minValue;
        }
    }

    private static final int OFFSET = 50;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            if (!line.isEmpty()) {
                String[] data = line.split(" ");
                int set1Size = Integer.parseInt(data[0]);
                int set2Size = Integer.parseInt(data[1]);
                int sum = 0;
                int[] values = new int[set1Size + set2Size];
                for (int i = 0; i < values.length; i++) {
                    int value = FastReader.nextInt();
                    values[i] = value + OFFSET;
                    sum += value;
                }

                Result result = computeExpressions(values, set1Size, sum);
                outputWriter.printLine(String.format("%d %d", result.maxValue, result.minValue));
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static Result computeExpressions(int[] values, int setSize, int sum) {
        boolean[][] dp = new boolean[setSize + 1][10001];
        dp[0][0] = true;

        for (int value : values) {
            for (int numbersToChoose = dp.length - 1; numbersToChoose > 0; numbersToChoose--) {
                for (int currentSum = 0; currentSum < dp[0].length; currentSum++) {
                    if (dp[numbersToChoose - 1][currentSum]) {
                        dp[numbersToChoose][currentSum + value] = true;
                    }
                }
            }
        }

        int maxValue = Integer.MIN_VALUE;
        int minValue = Integer.MAX_VALUE;
        for (int currentSum = 0; currentSum < dp[0].length; currentSum++) {
            if (dp[setSize][currentSum]) {
                int adjustedSum = currentSum - (setSize * OFFSET);
                int total = adjustedSum * (sum - adjustedSum);
                maxValue = Math.max(maxValue, total);
                minValue = Math.min(minValue, total);
            }
        }
        return new Result(maxValue, minValue);
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
