package chapter3.section5.c.zero.one.knapsack;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 13/11/22.
 */
public class LetsYumCha {

    private static class DimSum {
        int price;
        double favourValue;

        public DimSum(int price, double favourValue) {
            this.price = price;
            this.favourValue = favourValue;
        }
    }

    private static final double EPSILON = .000000001;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int friends = FastReader.nextInt();
        int maxPayPerPerson = FastReader.nextInt();
        int teaCharge = FastReader.nextInt();
        int dimSumTypes = FastReader.nextInt();

        while (friends != 0 || maxPayPerPerson != 0 || teaCharge != 0 || dimSumTypes != 0) {
            int people = friends + 1;
            DimSum[] dimSums = new DimSum[dimSumTypes * 2];
            for (int i = 0; i < dimSums.length; i += 2) {
                int price = FastReader.nextInt();
                double totalFavourIndex = 0;
                for (int f = 0; f < people; f++) {
                    totalFavourIndex += FastReader.nextInt();
                }
                double favourValue = totalFavourIndex / people;
                dimSums[i] = new DimSum(price, favourValue);
                dimSums[i + 1] = new DimSum(price, favourValue);
            }

            int totalTeaCharge = teaCharge * people;
            int numberOfDishes = people * 2;
            int maxPrice = (int) Math.floor((maxPayPerPerson * people) / 1.1 + EPSILON) - totalTeaCharge;
            double optimalMeanFavour = computeOptimalMeanFavour(maxPrice, dimSums, numberOfDishes);
            outputWriter.printLine(String.format("%.2f", optimalMeanFavour));

            friends = FastReader.nextInt();
            maxPayPerPerson = FastReader.nextInt();
            teaCharge = FastReader.nextInt();
            dimSumTypes = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static double computeOptimalMeanFavour(int maxPrice, DimSum[] dimSums, int numberOfDishes) {
        double[][] dp = new double[numberOfDishes + 1][maxPrice + 1];

        for (DimSum dimSum : dimSums) {
            for (int dish = numberOfDishes; dish > 0; dish--) {
                for (int priceRemaining = maxPrice; priceRemaining - dimSum.price >= 0; priceRemaining--) {
                    int indexWithDimSum = priceRemaining - dimSum.price;
                    double favourWithoutDimSum = dp[dish][priceRemaining];
                    double favourWithDimSum = dp[dish - 1][indexWithDimSum] + dimSum.favourValue;
                    dp[dish][priceRemaining] = Math.max(favourWithoutDimSum, favourWithDimSum);
                }
            }
        }
        return dp[numberOfDishes][maxPrice];
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
