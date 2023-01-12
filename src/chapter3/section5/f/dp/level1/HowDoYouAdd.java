package chapter3.section5.f.dp.level1;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 11/01/23.
 */
public class HowDoYouAdd {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int target = FastReader.nextInt();
        int numbers = FastReader.nextInt();
        long[][] dp = createDpArray();

        while (target != 0 || numbers != 0) {
            outputWriter.printLine(countWays(dp, target, numbers));
            target = FastReader.nextInt();
            numbers = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long[][] createDpArray() {
        long[][] dp = new long[101][101];
        for (long[] values : dp) {
            Arrays.fill(values, -1);
        }
        return dp;
    }

    private static long countWays(long[][] dp, int sum, int numbersRemaining) {
        if (numbersRemaining == 0) {
            if (sum == 0) {
                return 1;
            }
            return 0;
        }
        if (dp[sum][numbersRemaining] != -1) {
            return dp[sum][numbersRemaining];
        }

        long ways = 0;
        for (int number = 0; number <= sum; number++) {
            ways += countWays(dp, sum - number, numbersRemaining - 1);
            ways %= 1000000;
        }
        dp[sum][numbersRemaining] = ways;
        return ways;
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
