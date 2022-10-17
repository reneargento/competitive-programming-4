package chapter3.section5.a.max.oned.twod.range.sum;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 11/09/22.
 */
public class SellingSpatulas {

    private static class Result {
        int maximumProfit;
        int beginningMinute;
        int endingMinute;

        public Result(int maximumProfit, int beginningMinute, int endingMinute) {
            this.maximumProfit = maximumProfit;
            this.beginningMinute = beginningMinute;
            this.endingMinute = endingMinute;
        }
    }

    private static final int MAX_MINUTES = 1440;
    private static final int MINUTE_COST = 8;
    private static final double EPSILON = 0.000000000001;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int records = FastReader.nextInt();
        while (records != 0) {
            int[] profits = new int[MAX_MINUTES];
            Arrays.fill(profits, -MINUTE_COST);

            for (int i = 0; i < records; i++) {
                int minutes = FastReader.nextInt();
                int profit = (int) Math.round(FastReader.nextDouble() * 100);
                profits[minutes] += profit;
            }

            Result result = computeMaxProfit(profits);
            if (result.maximumProfit <= 0) {
                outputWriter.printLine("no profit");
            } else {
                double maximumProfitDouble = ((double) result.maximumProfit) / 100;
                outputWriter.printLine(String.format("%.2f %d %d", maximumProfitDouble, result.beginningMinute,
                        result.endingMinute));
            }
            records = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Result computeMaxProfit(int[] profits) {
        int maximumProfit = 0;
        int beginningMinute = 0;
        int endingMinute = 0;
        int bestPeriodLength = 0;

        int currentProfit = 0;
        int currentStartMinute = 0;

        for (int i = 0; i < profits.length; i++) {
            currentProfit += profits[i];

            if (currentProfit > maximumProfit
                    || (currentProfit == maximumProfit && ((i - currentStartMinute + 1) < bestPeriodLength))) {
                maximumProfit = currentProfit;
                beginningMinute = currentStartMinute;
                endingMinute = i;
                bestPeriodLength = i - beginningMinute + 1;
            }

            // Comparing an integer with EPSILON may not seem necessary,
            // but it is actually needed to get AC in this problem!
            if (currentProfit < EPSILON) {
                currentProfit = 0;
                currentStartMinute = i + 1;
            }
        }
        return new Result(maximumProfit, beginningMinute, endingMinute);
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