package chapter3.section2.i.mathematical.simulation.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 19/01/22.
 */
public class StopCounting {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] cards = new int[FastReader.nextInt()];

        for (int i = 0; i < cards.length; i++) {
            cards[i] = FastReader.nextInt();
        }

        double bestPayout = computeBestPayout(cards);
        outputWriter.printLine(bestPayout);
        outputWriter.flush();
    }

    private static double computeBestPayout(int[] cards) {
        double bestPayoutWithPrefix = computeBestPayout(cards, 0, cards.length, 1);
        double bestPayoutWithSuffix = computeBestPayout(cards, cards.length - 1, -1, -1);
        return Math.max(bestPayoutWithPrefix, bestPayoutWithSuffix);
    }

    private static double computeBestPayout(int[] cards, int startIndex, int endIndex, int increment) {
        double bestPayout = 0;
        double currentSum = 0;
        int cardsTaken = 0;

        for (int i = startIndex; i != endIndex; i += increment) {
            currentSum += cards[i];
            cardsTaken++;
            double currentPayout = currentSum / cardsTaken;
            bestPayout = Math.max(bestPayout, currentPayout);
        }
        return bestPayout;
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
