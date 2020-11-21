package chapter1.section6.a.game.card;

import java.util.*;

/**
 * Created by Rene Argento on 09/10/20.
 */
public class BridgeHands {

    private static class Card implements Comparable<Card> {
        private char rank;
        private char suit;

        public Card(char rank, char suit) {
            this.rank = rank;
            this.suit = suit;
        }

        @Override
        public int compareTo(Card otherCard) {
            if (suit != otherCard.suit) {
                return getSuitValue(suit) - getSuitValue(otherCard.suit);
            }
            return getRankValue(rank) - getRankValue(otherCard.rank);
        }

        private int getSuitValue(char suit) {
            switch (suit) {
                case 'C': return 0;
                case 'D': return 1;
                case 'S': return 2;
                default: return 3;
            }
        }

        private int getRankValue(char rank) {
            if (Character.isDigit(rank)) {
                return Character.getNumericValue(rank);
            }

            switch (rank) {
                case 'T': return 10;
                case 'J': return 11;
                case 'Q': return 12;
                case 'K': return 13;
                default: return 14;
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char dealer = scanner.next().charAt(0);
        Map<Character, Integer> playerToIndex = new HashMap<>();
        playerToIndex.put('S', 0);
        playerToIndex.put('W', 1);
        playerToIndex.put('N', 2);
        playerToIndex.put('E', 3);

        while (dealer != '#') {
            List<Card> southPlayerCards = new ArrayList<>();
            List<Card> westPlayerCards = new ArrayList<>();
            List<Card> northPlayerCards = new ArrayList<>();
            List<Card> eastPlayerCards = new ArrayList<>();

            List<List<Card>> playerCards = new ArrayList<>();
            playerCards.add(southPlayerCards);
            playerCards.add(westPlayerCards);
            playerCards.add(northPlayerCards);
            playerCards.add(eastPlayerCards);

            String line1 = scanner.next();
            String line2 = scanner.next();

            int currentPlayer = playerToIndex.get(dealer);
            currentPlayer++;
            currentPlayer %= 4;

            currentPlayer = getCards(line1, playerCards, currentPlayer);
            getCards(line2, playerCards, currentPlayer);

            for (List<Card> cards : playerCards) {
                Collections.sort(cards);
            }

            printCards('S', playerCards.get(0));
            printCards('W', playerCards.get(1));
            printCards('N', playerCards.get(2));
            printCards('E', playerCards.get(3));

            dealer = scanner.next().charAt(0);
        }
    }

    private static int getCards(String line, List<List<Card>> playerCards, int currentPlayer) {
        for (int i = 0; i < line.length(); i += 2) {
            String cardInfo = line.substring(i, i + 2);
            Card card = new Card(cardInfo.charAt(1), cardInfo.charAt(0));
            playerCards.get(currentPlayer).add(card);

            currentPlayer++;
            currentPlayer %= 4;
        }
        return currentPlayer;
    }

    private static void printCards(char player, List<Card> cards) {
        System.out.print(player + ": ");

        StringJoiner stringJoiner = new StringJoiner(" ");
        for (Card card : cards) {
            char[] cardInfo = {card.suit, card.rank};
            stringJoiner.add(new String(cardInfo));
        }
        System.out.println(stringJoiner);
    }

}
