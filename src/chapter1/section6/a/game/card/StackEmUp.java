package chapter1.section6.a.game.card;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Rene Argento on 10/10/20.
 */
@SuppressWarnings("unchecked")
public class StackEmUp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            String[] deck = createDeck();

            int shufflesNumber = scanner.nextInt();
            List<Integer>[] shuffles = new ArrayList[shufflesNumber];

            for (int i = 0; i < shufflesNumber; i++) {
                shuffles[i] = new ArrayList<>();

                for (int c = 0; c < 52; c++) {
                    shuffles[i].add(scanner.nextInt());
                }
            }
            scanner.nextLine();

            while (true) {
                if (!scanner.hasNext()) {
                    if (t > 0) {
                        System.out.println();
                    }
                    printDeck(deck);
                    break;
                }

                String nextLine = scanner.nextLine();
                if (nextLine.length() == 0) {
                    if (t > 0) {
                        System.out.println();
                    }
                    printDeck(deck);
                    break;
                }

                int shuffleId = Integer.parseInt(nextLine) - 1;
                deck = shuffle(deck, shuffles[shuffleId]);
            }
        }
    }

    private static String[] createDeck() {
        String[] suits = {"Clubs", "Diamonds", "Hearts", "Spades"};
        String[] values = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};

        String[] deck = new String[52];
        int deckIndex = 0;

        for (String suit : suits) {
            for (String value : values) {
                deck[deckIndex++] = value + " of " + suit;
            }
        }
        return deck;
    }

    private static String[] shuffle(String[] deck, List<Integer> shuffle) {
        String[] shuffledDeck = new String[52];

        for (int i = 0; i < shuffle.size(); i++) {
            int cardToMove = shuffle.get(i) - 1;
            shuffledDeck[i] = deck[cardToMove];
        }

        return shuffledDeck;
    }

    private static void printDeck(String[] deck) {
        for (String card : deck) {
            System.out.println(card);
        }
    }

}
