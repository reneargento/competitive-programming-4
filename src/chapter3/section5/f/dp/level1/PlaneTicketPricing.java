package chapter3.section5.f.dp.level1;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 02/01/23.
 */
public class PlaneTicketPricing {

    private static class Week {
        int price;
        int ticketsSold;
    }

    private static class Result {
        int maxRevenue;
        int priceForCurrentWeek;

        public Result(int maxRevenue, int priceForCurrentWeek) {
            this.maxRevenue = maxRevenue;
            this.priceForCurrentWeek = priceForCurrentWeek;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int seatsLeft = FastReader.nextInt();
        Week[][] weeks = new Week[FastReader.nextInt() + 1][];

        for (int w = 0; w < weeks.length; w++) {
            int values = FastReader.nextInt();
            weeks[w] = new Week[values];
            for (int i = 0; i < values; i++) {
                weeks[w][i] = new Week();
                weeks[w][i].price = FastReader.nextInt();
            }
            for (int i = 0; i < values; i++) {
                weeks[w][i].ticketsSold = FastReader.nextInt();
            }
        }

        Result result = computeResult(seatsLeft, weeks);
        outputWriter.printLine(result.maxRevenue);
        outputWriter.printLine(result.priceForCurrentWeek);
        outputWriter.flush();
    }

    private static Result computeResult(int seatsLeft, Week[][] weeks) {
        // dp[weekId][seatsLeft] = revenue
        int[][] dp = new int[weeks.length][seatsLeft + 1];
        for (int[] values : dp) {
            Arrays.fill(values, -1);
        }
        int maxRevenue = computeMaxRevenue(weeks, dp, 0, seatsLeft);
        int priceForCurrentWeek = computePriceForCurrentWeek(weeks, dp, seatsLeft);
        return new Result(maxRevenue, priceForCurrentWeek);
    }

    private static int computeMaxRevenue(Week[][] weeks, int[][] dp, int weekId, int seatsLeft) {
        if (weekId == weeks.length) {
            return 0;
        }
        if (dp[weekId][seatsLeft] != -1) {
            return dp[weekId][seatsLeft];
        }

        int maxRevenue = 0;
        for (int i = 0; i < weeks[weekId].length; i++) {
            int seats = Math.min(seatsLeft, weeks[weekId][i].ticketsSold);
            int revenue = seats * weeks[weekId][i].price
                    + computeMaxRevenue(weeks, dp, weekId + 1, seatsLeft - seats);
            maxRevenue = Math.max(maxRevenue, revenue);
        }
        dp[weekId][seatsLeft] = maxRevenue;
        return dp[weekId][seatsLeft];
    }

    private static int computePriceForCurrentWeek(Week[][] weeks, int[][] dp, int seatsLeft) {
        int priceForCurrentWeek = 0;
        int maxRevenue = -1;

        for (int i = 0; i < weeks[0].length; i++) {
            int seats = Math.min(seatsLeft, weeks[0][i].ticketsSold);
            int revenue = seats * weeks[0][i].price
                    + computeMaxRevenue(weeks, dp, 1, seatsLeft - seats);
            if (revenue > maxRevenue
                    || (revenue == maxRevenue && weeks[0][i].price < priceForCurrentWeek)) {
                maxRevenue = revenue;
                priceForCurrentWeek = weeks[0][i].price;
            }
        }
        return priceForCurrentWeek;
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
