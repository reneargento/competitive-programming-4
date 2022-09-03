package chapter3.section4.f.non.classical.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 12/08/22.
 */
public class CardTrading {

    private static class Card implements Comparable<Card> {
        int id;
        int cost;

        public Card(int id, int cost) {
            this.id = id;
            this.cost = cost;
        }

        @Override
        public int compareTo(Card other) {
            return Integer.compare(cost, other.cost);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int cardsNumber = FastReader.nextInt();
        int types = FastReader.nextInt();
        int combosTarget = FastReader.nextInt();

        int[] cardsOwned = new int[types];
        int[] buyPrices = new int[types];
        int[] sellPrices = new int[types];

        for (int i = 0; i < cardsNumber; i++) {
            int card = FastReader.nextInt() - 1;
            cardsOwned[card]++;
        }

        for (int i = 0; i < types; i++) {
            buyPrices[i] = FastReader.nextInt();
            sellPrices[i] = FastReader.nextInt();
        }

        long maxProfit = computeMaxProfit(cardsOwned, combosTarget, buyPrices, sellPrices);
        outputWriter.printLine(maxProfit);
        outputWriter.flush();
    }

    private static long computeMaxProfit(int[] cardsOwned, int combosTarget, int[] buyPrices, int[] sellPrices) {
        long maxProfit = 0;
        Card[] cardCosts = new Card[buyPrices.length];
        for (int i = 0; i < cardCosts.length; i++) {
            int quantityNeeded = 2 - cardsOwned[i];
            int cost = (cardsOwned[i] * sellPrices[i]) + (buyPrices[i] * quantityNeeded);
            cardCosts[i] = new Card(i, cost);
        }
        Arrays.sort(cardCosts);

        for (int i = 0; i < combosTarget; i++) {
            int cardId = cardCosts[i].id;
            long quantityNeeded = 2 - cardsOwned[cardId];
            maxProfit -= (buyPrices[cardId] * quantityNeeded);
        }
        for (int i = combosTarget; i < cardCosts.length; i++) {
            int cardId = cardCosts[i].id;
            maxProfit += (cardsOwned[cardId] * sellPrices[cardId]);
        }
        return maxProfit;
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