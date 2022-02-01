package chapter3.section2.k.recursive.backtracking.easy;

import java.io.*;

/**
 * Created by Rene Argento on 29/01/22.
 */
public class Y2KAccountingBug {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            String[] values = line.split(" ");
            int surplus = Integer.parseInt(values[0]);
            int deficit = Integer.parseInt(values[1]);

            long maxSurplus = computeMaxSurplus(surplus, deficit);
            if (maxSurplus < 0) {
                outputWriter.printLine("Deficit");
            } else {
                outputWriter.printLine(maxSurplus);
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static long computeMaxSurplus(int surplus, int deficit) {
        long[] consecutiveMonthsProfit = new long[5];
        return computeMaxSurplus(surplus, deficit, 0, 1, consecutiveMonthsProfit, 0);
    }

    private static long computeMaxSurplus(int surplus, int deficit, long currentValue, int month,
                                          long[] consecutiveMonthsProfit, int consecutiveMonthIndex) {
        if (month >= 6
                && computeConsecutiveMonthsProfit(consecutiveMonthsProfit) >= 0) {
            return -1;
        }
        if (month > 12) {
            return currentValue;
        }

        consecutiveMonthsProfit[consecutiveMonthIndex % 5] = surplus;
        long resultWithSurplus = computeMaxSurplus(surplus, deficit, currentValue + surplus, month + 1,
                consecutiveMonthsProfit, consecutiveMonthIndex + 1);

        consecutiveMonthsProfit[consecutiveMonthIndex % 5] = -deficit;
        long resultWithDeficit = computeMaxSurplus(surplus, deficit, currentValue - deficit, month + 1,
                consecutiveMonthsProfit, consecutiveMonthIndex + 1);

        return Math.max(resultWithSurplus, resultWithDeficit);
    }

    private static long computeConsecutiveMonthsProfit(long[] consecutiveMonthsProfit) {
        long totalProfit = 0;
        for (long profit : consecutiveMonthsProfit) {
            totalProfit += profit;
        }
        return totalProfit;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
