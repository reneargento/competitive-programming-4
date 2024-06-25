package chapter4.session6.c.converting.general.graph.to.dag;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/06/24.
 */
public class TravellingSalesman {

    private static final int NEGATIVE_INFINITE = -10000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int cities = FastReader.nextInt();
        int startingCity = FastReader.nextInt() - 1;
        int numberOfEndCities = FastReader.nextInt();
        int interCityTravels = FastReader.nextInt();

        while (cities != 0) {
            int[][] profit = new int[cities][cities];

            for (int row = 0; row < profit.length; row++) {
                for (int column = 0; column < profit[row].length; column++) {
                    profit[row][column] = FastReader.nextInt();
                }
            }

            boolean[] endCities = new boolean[cities];
            for (int i = 0; i < numberOfEndCities; i++) {
                int cityId = FastReader.nextInt() - 1;
                endCities[cityId] = true;
            }

            int maxProfit = computeMaxProfit(profit, endCities, startingCity, interCityTravels);
            maxProfit = Math.max(maxProfit, 0);
            outputWriter.printLine(maxProfit);

            cities = FastReader.nextInt();
            startingCity = FastReader.nextInt() - 1;
            numberOfEndCities = FastReader.nextInt();
            interCityTravels = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeMaxProfit(int[][] profit, boolean[] endCities, int startingCity,
                                        int interCityTravels) {
        // dp[city id][inter-city travels remaining]
        int[][] dp = new int[profit.length][interCityTravels + 1];
        for (int[] values : dp) {
            Arrays.fill(values, -1);
        }
        return computeMaxProfit(profit, endCities, dp, startingCity, interCityTravels);
    }

    private static int computeMaxProfit(int[][] profit, boolean[] endCities, int[][] dp, int currentCity,
                                        int interCityTravelsLeft) {
        if (interCityTravelsLeft == 0) {
            if (endCities[currentCity]) {
                return 0;
            } else {
                return NEGATIVE_INFINITE;
            }
        }
        if (dp[currentCity][interCityTravelsLeft] != -1) {
            return dp[currentCity][interCityTravelsLeft];
        }

        int maxProfit = 0;
        for (int nextCityId = 0; nextCityId < profit.length; nextCityId++) {
            if (nextCityId == currentCity) {
                continue;
            }
            int profitWithCity = profit[currentCity][nextCityId] +
                    computeMaxProfit(profit, endCities, dp, nextCityId, interCityTravelsLeft - 1);
            maxProfit = Math.max(maxProfit, profitWithCity);
        }

        dp[currentCity][interCityTravelsLeft] = maxProfit;
        return dp[currentCity][interCityTravelsLeft];
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
