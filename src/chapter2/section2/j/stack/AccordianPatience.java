package chapter2.section2.j.stack;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 20/03/21.
 */
public class AccordianPatience {

    private static class Card {
        char faceValue;
        char suit;

        public Card(char faceValue, char suit) {
            this.faceValue = faceValue;
            this.suit = suit;
        }

        boolean matches(Card other) {
            return other.faceValue == faceValue || other.suit == suit;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String cardString = FastReader.next();

        while (cardString.charAt(0) != '#') {
            List<Deque<Card>> piles = new ArrayList<>();
            piles.add(new ArrayDeque<>());
            piles.get(0).push(new Card(cardString.charAt(0), cardString.charAt(1)));

            for (int i = 1; i < 52; i++) {
                cardString = FastReader.next();
                piles.add(new ArrayDeque<>());
                piles.get(piles.size() - 1).push(new Card(cardString.charAt(0), cardString.charAt(1)));
                matchAllCards(piles);
            }
            printPiles(piles, outputWriter);

            cardString = FastReader.next();
        }
        outputWriter.flush();
    }

    private static void matchAllCards(List<Deque<Card>> piles) {
        for (int i = 1; i < piles.size(); i++) {
            int lastMatchedIndex = matchCardsByIndex(piles, i);
            if (lastMatchedIndex != -1) {
                i = lastMatchedIndex - 1;
            }
        }
    }

    private static int matchCardsByIndex(List<Deque<Card>> piles, int currentIndex) {
        int lastMatchedIndex = -1;
        int index = currentIndex;

        index = matchCards(piles, index);

        while (index != -1) {
            lastMatchedIndex = index;
            index = matchCards(piles, index);
        }

        if (lastMatchedIndex != -1) {
            updatePiles(piles, currentIndex, lastMatchedIndex);
        }
        return lastMatchedIndex;
    }

    private static int matchCards(List<Deque<Card>> piles, int currentIndex) {
        if (currentIndex >= 3) {
            int targetPileIndex = currentIndex - 3;
            if (matchPiles(piles, currentIndex, targetPileIndex)) {
                return targetPileIndex;
            }
        }

        if (currentIndex >= 1) {
            int targetPileIndex = currentIndex - 1;
            if (matchPiles(piles,currentIndex, targetPileIndex)) {
                return targetPileIndex;
            }
        }
        return -1;
    }

    private static boolean matchPiles(List<Deque<Card>> piles, int currentIndex, int targetPileIndex) {
        Card card = piles.get(currentIndex).peek();
        Deque<Card> targetPile = piles.get(targetPileIndex);
        return targetPile.peek().matches(card);
    }

    private static void updatePiles(List<Deque<Card>> piles, int currentIndex, int targetPileIndex) {
        Card card = piles.get(currentIndex).pop();

        if (piles.get(currentIndex).isEmpty()) {
            piles.remove(currentIndex);
        }
        piles.get(targetPileIndex).push(card);
    }

    private static void printPiles(List<Deque<Card>> piles, OutputWriter outputWriter) {
        outputWriter.print(piles.size());
        if (piles.size() == 1) {
            outputWriter.print(" pile ");
        } else {
            outputWriter.print(" piles ");
        }
        outputWriter.print("remaining: ");

        for (int i = 0; i < piles.size(); i++) {
            outputWriter.print(piles.get(i).size());

            if (i < piles.size() - 1) {
                outputWriter.print(" ");
            }
        }
        outputWriter.printLine();
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
