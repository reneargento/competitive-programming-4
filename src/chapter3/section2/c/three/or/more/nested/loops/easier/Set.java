package chapter3.section2.c.three.or.more.nested.loops.easier;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 09/11/21.
 */
public class Set {

    private static class CompleteSet implements Comparable<CompleteSet> {
        int card1;
        int card2;
        int card3;

        public CompleteSet(int card1, int card2, int card3) {
            this.card1 = card1;
            this.card2 = card2;
            this.card3 = card3;
        }

        @Override
        public int compareTo(CompleteSet other) {
            if (card1 != other.card1) {
                return Integer.compare(card1, other.card1);
            }
            if (card2 != other.card2) {
                return Integer.compare(card2, other.card2);
            }
            return Integer.compare(card3, other.card3);
        }
    }

    private static class Card {
        String data;
        int id;

        public Card(String data, int id) {
            this.data = data;
            this.id = id;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        Card[] cards = new Card[12];

        for (int i = 0; i < cards.length; i++) {
            cards[i] = new Card(FastReader.next(), i + 1);
        }

        List<CompleteSet> completeSets = computeSets(cards);
        if (completeSets.isEmpty()) {
            outputWriter.printLine("no sets");
        } else {
            for (CompleteSet set : completeSets) {
                outputWriter.printLine(String.format("%d %d %d", set.card1, set.card2, set.card3));
            }
        }
        outputWriter.flush();
    }

    private static List<CompleteSet> computeSets(Card[] cards) {
        List<CompleteSet> sets = new ArrayList<>();

        for (int i = 0; i < cards.length; i++) {
            for (int j = i + 1; j < cards.length; j++) {
                for (int k = j + 1; k < cards.length; k++) {

                    if (isSet(cards[i], cards[j], cards[k])) {
                        sets.add(new CompleteSet(cards[i].id, cards[j].id, cards[k].id));
                    }
                }
            }
        }
        Collections.sort(sets);
        return sets;
    }

    private static boolean isSet(Card card1, Card card2, Card card3) {
        for (int i = 0; i < 4; i++) {
            if (!isAttributeOk(card1, card2, card3, i)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isAttributeOk(Card card1, Card card2, Card card3, int index) {
        return (card1.data.charAt(index) != card2.data.charAt(index)
                && card1.data.charAt(index) != card3.data.charAt(index)
                && card2.data.charAt(index) != card3.data.charAt(index)) ||
                (card1.data.charAt(index) == card2.data.charAt(index)
                        && card2.data.charAt(index) == card3.data.charAt(index));
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
