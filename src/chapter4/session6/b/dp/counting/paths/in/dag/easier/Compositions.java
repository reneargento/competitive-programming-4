package chapter4.session6.b.dp.counting.paths.in.dag.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 24/05/24.
 */
// Based on http://acmgnyr.org/year2015/solutions/g.c
public class Compositions {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        int[] powersOf2 = getPowersOf2();

        for (int t = 0; t < tests; t++) {
            int dataSetNumber = FastReader.nextInt();
            int number = FastReader.nextInt();
            int m = FastReader.nextInt();
            int k = FastReader.nextInt();

            long targetCompositions = countTargetCompositions(number, m, k, powersOf2);
            outputWriter.printLine(String.format("%d %d", dataSetNumber, targetCompositions));
        }
        outputWriter.flush();
    }

    private static long countTargetCompositions(int number, int m, int k, int[] powersOf2) {
        long[] dp = new long[31];

        if (m == 0) {
            if (number < k) {
                return powersOf2[number - 1];
            } else if (number == k) {
                return powersOf2[number - 1] - 1;
            } else {
                dp[0] = 1;
                dp[1] = 1;
                for (int i = 2; i < k; i++) {
                    dp[i] = powersOf2[i - 1];
                }
                dp[k] = powersOf2[k - 1] - 1;

                for (int i = k; i <= number; i++) {
                    long sum = 0;
                    for (int j = 1; j <= i; j++) {
                        if (j % k != 0) {
                            sum += dp[i - j];
                        }
                    }
                    dp[i] = sum;
                }
                return dp[number];
            }
        } else {
            if (number < m) {
                return powersOf2[number - 1];
            } else if (number == m) {
                return powersOf2[number - 1] - 1;
            } else {
                dp[0] = 1;
                for (int i = 1; i < m; i++) {
                    dp[i] = powersOf2[i - 1];
                }
                dp[m] = powersOf2[m - 1] - 1;

                for (int i = m + 1; i <= number; i++) {
                    long sum = 0;
                    for (int j = 0; j <= i; j++) {
                        if (j % k != m) {
                            sum += dp[i - j];
                        }
                    }
                    dp[i] = sum;
                }
                return dp[number];
            }
        }
    }

    private static int[] getPowersOf2() {
        int[] powersOf2 = new int[30];
        powersOf2[0] = 1;

        for (int i = 1; i < powersOf2.length; i++) {
            powersOf2[i] = powersOf2[i - 1] * 2;
        }
        return powersOf2;
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
