package chapter3.section5.d.coin.change;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 30/11/22.
 */
public class CoinChange {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();
        int[] dp = computeChangeWays();

        while (line != null) {
            int targetMoney = Integer.parseInt(line);
            outputWriter.printLine(dp[targetMoney]);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int[] computeChangeWays() {
        int[] values = { 1, 5, 10, 25, 50 };
        int[] dp = new int[7590];

        // Base case
        dp[0] = 1;

        for (int value : values) {
            for (int money = value; money < dp.length; money++) {
                dp[money] += dp[money - value];
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

        private static String getLine() throws IOException {
            return reader.readLine();
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
