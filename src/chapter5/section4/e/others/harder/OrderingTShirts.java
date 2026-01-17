package chapter5.section4.e.others.harder;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 12/01/26.
 */
public class OrderingTShirts {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        long[] dp = new long[12];
        Arrays.fill(dp, -1);

        for (int t = 0; t < tests; t++) {
            int objects = FastReader.nextInt();
            long arrangements = computeArrangements(dp, objects);
            outputWriter.printLine(arrangements);
        }
        outputWriter.flush();
    }

    // https://oeis.org/A000670
    private static long computeArrangements(long[] dp, int objects) {
        if (objects == 0) {
            return 1;
        }
        if (dp[objects] != -1) {
            return dp[objects];
        }

        long ways = 0;
        for (int i = 1; i <= objects; i++) {
            ways += binomialCoefficient(objects, i) * computeArrangements(dp, objects - i);
        }
        dp[objects] = ways;
        return ways;
    }

    private static long binomialCoefficient(int totalNumbers, int numbersToChoose) {
        long result = 1;

        for (int i = 0; i < numbersToChoose; i++) {
            result = result * (totalNumbers - i) / (i + 1);
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

        public void flush() {
            writer.flush();
        }
    }
}
