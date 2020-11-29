package chapter1.section6.i.time.harder;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

/**
 * Created by Rene Argento on 20/11/20.
 */
@SuppressWarnings("unchecked")
public class ClockPatience {

    private static class Card {
        int rank;
        char suit;

        public Card(int rank, char suit) {
            this.rank = rank;
            this.suit = suit;
        }

        @Override
        public String toString() {
            return getCardRank(rank) + suit;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        while (!line.equals("#")) {
            Deque<Card> allCards = new ArrayDeque<>();

            for (int l = 0; l < 4; l++) {
                String[] cards = line.split(" ");

                for (int i = 0; i < cards.length; i++) {
                    int rank = getRank(cards[i].charAt(0));
                    allCards.push(new Card(rank, cards[i].charAt(1)));
                }
                line = scanner.nextLine();
            }

            Deque<Card>[] stacks = new ArrayDeque[13];
            for (int i = 0; i < stacks.length; i++) {
                stacks[i] = new ArrayDeque<>();
            }

            int stackIndex = 0;

            for (Card card : allCards) {
                stacks[stackIndex].push(card);
                stackIndex = (stackIndex + 1) % 13;
            }

            stackIndex = 12;
            int cardsExposed = 0;
            Card lastCardExposed = null;

            while (!stacks[stackIndex].isEmpty()) {
                Card nextCard = stacks[stackIndex].pop();
                cardsExposed++;
                lastCardExposed = nextCard;
                stackIndex = nextCard.rank - 1;
            }
            System.out.printf("%02d,%s\n", cardsExposed, lastCardExposed.toString());
        }
    }

    private static int getRank(char value) {
        if (Character.isDigit(value)) {
            return Character.getNumericValue(value);
        }
        switch (value) {
            case 'T': return 10;
            case 'J': return 11;
            case 'Q': return 12;
            case 'K': return 13;
            default: return 1;
        }
    }

    private static String getCardRank(int value) {
        if (2 <= value && value <= 9) {
            return String.valueOf(value);
        }

        switch (value) {
            case 10: return "T";
            case 11: return "J";
            case 12: return "Q";
            case 13: return "K";
            default: return "A";
        }
    }
}
