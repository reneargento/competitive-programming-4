package chapter3.section2.a.precalculateable;

import java.io.*;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.Queue;

/**
 * Created by Rene Argento on 04/09/21.
 */
public class CardTrick {

    private static class Card {
        int value;
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            Card[] cards = computeCards(FastReader.nextInt());
            for (int i = 0; i < cards.length; i++) {
                outputWriter.print(cards[i].value);

                if (i != cards.length - 1) {
                    outputWriter.print(" ");
                }
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
    }

    private static Card[] computeCards(int cardsNumber) {
        Queue<Card> cardsQueue = new LinkedList<>();
        Card[] cards = new Card[cardsNumber];

        for (int i = 0; i < cardsNumber; i++) {
            cards[i] = new Card();
            cardsQueue.offer(cards[i]);
        }

        int nextCard = 1;
        while (!cardsQueue.isEmpty()) {
            for (int i = 0; i < nextCard; i++) {
                Card cardToMove = cardsQueue.poll();
                cardsQueue.offer(cardToMove);
            }

            Card card = cardsQueue.poll();
            card.value = nextCard;
            nextCard++;
        }
        return cards;
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
