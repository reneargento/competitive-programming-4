package chapter3.section5.d.coin.change;

import java.io.*;

/**
 * Created by Rene Argento on 05/12/22.
 */
public class IngenuousCubrency {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int[] coins = computeCoins();
        long[] waysToPay = computeWaysToPay(coins);

        String line = FastReader.getLine();
        while (line != null) {
            int amount = Integer.parseInt(line.trim());
            outputWriter.printLine(waysToPay[amount]);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int[] computeCoins() {
        int[] coins = new int[21];
        for (int value = 1; value <= 21; value++) {
            int cube = value * value * value;
            coins[value - 1] = cube;
        }
        return coins;
    }

    private static long[] computeWaysToPay(int[] coins) {
        long[] waysToPay = new long[10000];
        waysToPay[0] = 1;

        for (int coin : coins) {
            for (int value = coin; value < waysToPay.length; value++) {
                waysToPay[value] += waysToPay[value - coin];
            }
        }
        return waysToPay;
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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

        public void flush() {
            writer.flush();
        }
    }
}
