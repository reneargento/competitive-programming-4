package chapter5.section3.e.modified.sieve;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 25/07/25.
 */
public class BigData {

    private static final int INVALID = -1000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int[] properties = new int[FastReader.nextInt()];
        for (int i = 0; i < properties.length; i++) {
            properties[i] = FastReader.nextInt();
        }

        int maxRevenue = computeMaxRevenue(properties);
        outputWriter.printLine(maxRevenue);
        outputWriter.flush();
    }

    private static int computeMaxRevenue(int[] properties) {
        int[] uniquePFs = eratosthenesSieveCountUniquePFs(14001);
        int bitmask = (1 << properties.length) - 1;
        int[][] dp = new int[bitmask + 1][properties.length];
        for (int[] values : dp) {
            Arrays.fill(values, -1);
        }
        return computeRevenues(properties, uniquePFs, dp, bitmask, 0);
    }

    private static int[] eratosthenesSieveCountUniquePFs(int maxNumber) {
        int[] uniquePFs = new int[maxNumber + 1];

        for (int i = 2; i < uniquePFs.length; i++) {
            if (uniquePFs[i] == 0) {
                for (int j = i; j < uniquePFs.length; j += i) {
                    uniquePFs[j]++;
                }
            }
        }
        return uniquePFs;
    }

    private static int computeRevenues(int[] values, int[] uniquePFs, int[][] dp, int bitmask, int customerId) {
        if (customerId == values.length) {
            if (bitmask == 0) {
                return 0;
            }
            return INVALID;
        }
        if (dp[bitmask][customerId] != -1) {
            return dp[bitmask][customerId];
        }

        int[] availableIndexes = new int[values.length];
        int availableIndexesIndex = 0;
        int bitmaskCopy = bitmask;
        while (bitmaskCopy > 0) {
            int smallestPowerOf2 = getSmallestPowerOf2(bitmaskCopy);
            bitmaskCopy -= smallestPowerOf2;

            availableIndexes[availableIndexesIndex] = smallestPowerOf2;
            availableIndexesIndex++;
        }

        int maxBitmask = (1 << availableIndexesIndex) - 1;
        int maxRevenue = 0;
        for (int currentBitmask = 0; currentBitmask <= maxBitmask; currentBitmask++) {
            int revenue = 0;
            int nextBitmask = bitmask;
            int customerBitmask = currentBitmask;

            while (customerBitmask > 0) {
                int smallestPowerOf2 = getSmallestPowerOf2(customerBitmask);
                customerBitmask -= smallestPowerOf2;

                int index = Integer.numberOfTrailingZeros(smallestPowerOf2);
                int indexValuePowerOf2 = availableIndexes[index];
                int indexValue = Integer.numberOfTrailingZeros(indexValuePowerOf2);

                revenue += values[indexValue];
                nextBitmask &= ~(1 << indexValue);
            }
            int totalRevenue = uniquePFs[revenue]
                    + computeRevenues(values, uniquePFs, dp, nextBitmask, customerId + 1);
            maxRevenue = Math.max(maxRevenue, totalRevenue);
        }
        dp[bitmask][customerId] = maxRevenue;
        return maxRevenue;
    }

    private static int getSmallestPowerOf2(int number) {
        return number & (-number);
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
