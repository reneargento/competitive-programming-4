package chapter3.section5.f.dp.level1;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 30/12/22.
 */
public class ChestOfDrawers {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int drawers = FastReader.nextInt();
        int secured = FastReader.nextInt();
        long[][][] dp = createDp();

        while (drawers >= 0 && secured >= 0) {
            long numberOfWays = dp[drawers][secured][0] + dp[drawers][secured][1];
            outputWriter.printLine(numberOfWays);

            drawers = FastReader.nextInt();
            secured = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long[][][] createDp() {
        // dp[drawers][secured][upper drawer locked or not]
        long[][][] dp = new long[66][66][2];
        // Base cases
        dp[1][0][0] = 1;
        dp[1][1][1] = 1;

        for (int drawer = 1; drawer < dp.length - 1; drawer++) {
            for (int secured = 0; secured <= drawer; secured++) {
                dp[drawer + 1][secured + 1][1] += dp[drawer][secured][1]; // LL
                dp[drawer + 1][secured][0] = dp[drawer][secured][0] + dp[drawer][secured][1]; // UU + UL
                dp[drawer + 1][secured][1] += dp[drawer][secured][0]; // LU
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
