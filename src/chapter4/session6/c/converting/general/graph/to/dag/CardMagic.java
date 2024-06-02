package chapter4.session6.c.converting.general.graph.to.dag;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 30/05/24.
 */
public class CardMagic {

    private static final int MOD = 1000000009;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int decks = FastReader.nextInt();
        int cards = FastReader.nextInt();
        int targetNumber = FastReader.nextInt();

        long waysToPickCards = countWaysToPickCards(decks, cards, targetNumber);
        outputWriter.printLine(waysToPickCards);
        outputWriter.flush();
    }

    private static long countWaysToPickCards(int decks, int cards, int targetNumber) {
        int maxSum = decks * cards + 1;
        // dp[deckId][currentSum]
        long[][] dp = new long[decks][maxSum];
        for (long[] values : dp) {
            Arrays.fill(values, -1);
        }
        return countWaysToPickCards(decks, cards, targetNumber, dp, 0, 0);
    }

    private static long countWaysToPickCards(int decks, int cards, int targetNumber, long[][] dp,
                                             int deckId, int currentSum) {
        if (deckId == decks) {
            if (currentSum == targetNumber) {
                return 1;
            }
            return 0;
        }
        if (currentSum > dp[0].length) {
            dp[deckId][currentSum] = 0;
            return 0;
        }

        if (dp[deckId][currentSum] != -1) {
            return dp[deckId][currentSum];
        }

        long ways = 0;
        for (int cardValue = 1; cardValue <= cards; cardValue++) {
            int nextSum = currentSum + cardValue;
            ways += countWaysToPickCards(decks, cards, targetNumber, dp, deckId + 1, nextSum);
            ways %= MOD;
        }
        dp[deckId][currentSum] = ways;
        return dp[deckId][currentSum];
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
