package chapter3.section3.a.binary.search;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 11/03/22.
 */
public class AngryBids {

    private static class Solution {
        int price;
        long angryPeople;

        public Solution(int price, long angryPeople) {
            this.price = price;
            this.angryPeople = angryPeople;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int[] producers = new int[FastReader.nextInt()];
            int[] consumers = new int[FastReader.nextInt() + 1];

            for (int i = 0; i < producers.length; i++) {
                producers[i] = FastReader.nextInt();
            }
            consumers[0] = 0;
            for (int i = 1; i < consumers.length; i++) {
                consumers[i] = FastReader.nextInt();
            }
            Solution solution = computeBestPrice(producers, consumers);
            outputWriter.printLine(solution.price + " " + solution.angryPeople);
        }
        outputWriter.flush();
    }

    private static Solution computeBestPrice(int[] producers, int[] consumers) {
        if (producers.length == 0 && consumers.length == 0) {
            return new Solution(0, 0);
        }
        Arrays.sort(producers);
        Arrays.sort(consumers);

        Solution solution1 = getBestPriceFromProducers(producers, consumers);
        Solution solution2 = getBestPriceFromConsumers(producers, consumers);

        if (solution1.angryPeople < solution2.angryPeople
                || (solution1.angryPeople == solution2.angryPeople
                     && solution1.price < solution2.price)) {
            return solution1;
        } else {
            return solution2;
        }
    }

    private static Solution getBestPriceFromProducers(int[] producers, int[] consumers) {
        int leastAngryPeople = Integer.MAX_VALUE;
        int bestPrice = Integer.MAX_VALUE;

        for (int i = 0; i < producers.length; i++) {
            int price = producers[i];
            int angryPeople = 0;

            if (i < producers.length - 1) {
                angryPeople += producers.length - 1 - i;
            }
            if (consumers.length > 0) {
                int upperBound = binarySearch(consumers, price, false);
                if (upperBound != -1) {
                    angryPeople += upperBound;
                }
            }

            if (angryPeople < leastAngryPeople) {
                leastAngryPeople = angryPeople;
                bestPrice = price;
            }
        }
        return new Solution(bestPrice, leastAngryPeople);
    }

    private static Solution getBestPriceFromConsumers(int[] producers, int[] consumers) {
        int leastAngryPeople = Integer.MAX_VALUE;
        int bestPrice = Integer.MAX_VALUE;

        for (int i = 0; i < consumers.length; i++) {
            int price = consumers[i];
            int angryPeople = i;

            if (producers.length > 0) {
                int lowerBound = binarySearch(producers, price, true);
                if (lowerBound != -1) {
                    angryPeople += (producers.length - lowerBound);
                }
            }

            if (angryPeople < leastAngryPeople) {
                leastAngryPeople = angryPeople;
                bestPrice = price;
            }
        }
        return new Solution(bestPrice, leastAngryPeople);
    }

    private static int binarySearch(int[] values, int target, boolean isLowerBound) {
        int low = 0;
        int high = values.length - 1;
        int result = -1;

        while (low <= high) {
            int middle = low + (high - low) / 2;

            if (isLowerBound) {
                if (values[middle] <= target) {
                    low = middle + 1;
                } else {
                    result = middle;
                    high = middle - 1;
                }
            } else {
                if (values[middle] >= target) {
                    high = middle - 1;
                } else {
                    result = middle;
                    low = middle + 1;
                }
            }
        }
        return result;
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
