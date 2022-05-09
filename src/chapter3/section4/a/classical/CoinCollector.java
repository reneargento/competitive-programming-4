package chapter3.section4.a.classical;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 09/05/22.
 */
public class CoinCollector {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int[] coins = new int[FastReader.nextInt()];

            for (int i = 0; i < coins.length; i++) {
                coins[i] = FastReader.nextInt();
            }

            int coinTypes = computeMaximumCoinTypes(coins);
            outputWriter.printLine(coinTypes);
        }
        outputWriter.flush();
    }

    private static int computeMaximumCoinTypes(int[] coins) {
        int coinTypes = 1;
        long currentSum = 0;

        for (int i = 0; i < coins.length - 1; i++) {
            if (currentSum + coins[i] < coins[i + 1]) {
                coinTypes++;
                currentSum += coins[i];
            }
        }
        return coinTypes;
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
