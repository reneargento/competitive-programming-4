package chapter3.section3.b.bisection.method.and.bsta.easier;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 01/04/22.
 */
public class FinancialPlanning {

    private static class Investment implements Comparable<Investment> {
        int profit;
        int cost;
        double profitPerCost;

        public Investment(int profit, int cost) {
            this.profit = profit;
            this.cost = cost;
            profitPerCost = profit / (double) cost;
        }

        @Override
        public int compareTo(Investment other) {
            return Double.compare(other.profitPerCost, profitPerCost);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        Investment[] investments = new Investment[FastReader.nextInt()];
        int moneyToRetire = FastReader.nextInt();

        for (int i = 0; i < investments.length; i++) {
            investments[i] = new Investment(FastReader.nextInt(), FastReader.nextInt());
        }
        long daysToRetire = computeDaysToRetire(investments, moneyToRetire);
        outputWriter.printLine(daysToRetire);
        outputWriter.flush();
    }

    private static long computeDaysToRetire(Investment[] investments, int moneyToRetire) {
        Arrays.sort(investments);
        long lowDays = 1;
        long highDays = 10000000000L;
        long daysToRetire = Integer.MAX_VALUE;

        while (lowDays <= highDays) {
            long middleDays = lowDays + (highDays - lowDays) / 2;
            if (canRetire(investments, moneyToRetire, middleDays)) {
                daysToRetire = middleDays;
                highDays = middleDays - 1;
            } else {
                lowDays = middleDays + 1;
            }
        }
        return daysToRetire;
    }

    private static boolean canRetire(Investment[] investments, int moneyToRetire, long days) {
        long profit = 0;
        long cost = 0;

        for (Investment investment : investments) {
            profit += investment.profit;
            cost += investment.cost;

            if (days * profit - cost >= moneyToRetire) {
                return true;
            }
        }
        return false;
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
