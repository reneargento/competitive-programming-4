package chapter5.section4.a.fibonacci.numbers;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/11/25.
 */
public class CriticalMass {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int stackSize = FastReader.nextInt();
        while (stackSize != 0) {
            long dangerousCombinations = computeDangerousCombinations(stackSize);
            outputWriter.printLine(dangerousCombinations);
            stackSize = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long computeDangerousCombinations(int stackSize) {
        int maxSize = Math.min(stackSize, 30);
        // dp[index][number of uranium cubes suffix] = valid combinations
        long[][] dp = new long[maxSize + 1][3];
        dp[1][0] = 1;
        dp[1][1] = 1;

        for (int i = 2; i < dp.length; i++) {
            dp[i][0] = dp[i - 1][0] + dp[i - 1][1] + dp[i - 1][2];
            dp[i][1] = dp[i - 1][0];
            dp[i][2] = dp[i - 1][1];
        }

        long totalCombinations = (long) Math.pow(2, maxSize);
        long totalValidCombinations = dp[maxSize][0] + dp[maxSize][1] + dp[maxSize][2];
        return totalCombinations - totalValidCombinations;
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
