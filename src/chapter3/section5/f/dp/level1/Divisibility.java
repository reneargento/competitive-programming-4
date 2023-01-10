package chapter3.section5.f.dp.level1;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 07/01/23.
 */
public class Divisibility {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int[] sequence = new int[FastReader.nextInt()];
            int k = FastReader.nextInt();

            for (int i = 0; i < sequence.length; i++) {
                sequence[i] = FastReader.nextInt();
            }

            if (isDivisible(sequence, k)) {
                outputWriter.printLine("Divisible");
            } else {
                outputWriter.printLine("Not divisible");
            }
        }
        outputWriter.flush();
    }

    private static boolean isDivisible(int[] sequence, int k) {
        int[][] dp = new int[2][k];
        int firstMod = Math.abs(sequence[0]) % k;
        dp[0][firstMod] = 1;
        int currentIndex = 1;

        for (int i = 1; i < sequence.length; i++) {
            int previousIndex = currentIndex ^ 1;
            for (int mod = 0; mod < dp[0].length; mod++) {
                if (dp[previousIndex][mod] == 1) {
                    int resultAdding = Math.abs(mod + sequence[i]) % k;
                    int resultSubtracting = Math.abs(mod - sequence[i]) % k;
                    dp[currentIndex][resultAdding] = 1;
                    dp[currentIndex][resultSubtracting] = 1;
                }
            }
            currentIndex = previousIndex;
            Arrays.fill(dp[currentIndex], 0);
        }
        return dp[currentIndex ^ 1][0] == 1;
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
