package chapter3.section5.c.zero.one.knapsack;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 13/11/22.
 */
public class SumOfDifferentPrimes {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int number = FastReader.nextInt();
        int primesToUse = FastReader.nextInt();
        List<Integer> primes = erathostenesSieve();
        int[][] dp = computeWays(primes);

        while (number != 0 && primesToUse != 0) {
            int possibleWays = dp[primesToUse][number];
            outputWriter.printLine(possibleWays);

            number = FastReader.nextInt();
            primesToUse = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static List<Integer> erathostenesSieve() {
        boolean[] composedNumbers = new boolean[1121];
        List<Integer> primes = new ArrayList<>();

        for (int i = 2; i < composedNumbers.length; i++) {
            if (!composedNumbers[i]) {
                for (int nextMultiple = i * i; nextMultiple < composedNumbers.length; nextMultiple += i) {
                    composedNumbers[nextMultiple] = true;
                }
                primes.add(i);
            }
        }
        return primes;
    }

    private static int[][] computeWays(List<Integer> primes) {
        int[][] dp = new int[15][1121];
        dp[0][0] = 1;

        for (int prime : primes) {
            for (int primesUsed = dp.length - 1; primesUsed >= 1; primesUsed--) {
                for (int value = dp[0].length - 1; value - prime >= 0; value--) {
                    dp[primesUsed][value] += dp[primesUsed - 1][value - prime];
                }
            }
        }
        return dp;
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
