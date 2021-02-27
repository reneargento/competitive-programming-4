package chapter2.section2.f.sorting.harder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by Rene Argento on 26/02/21.
 */
public class AddEmUp {

    private static class Card {
        int id;
        int value;

        public Card(int id, int value) {
            this.id = id;
            this.value = value;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int numberOfCards = FastReader.nextInt();
        int targetSum = FastReader.nextInt();
        List<Card> cards = new ArrayList<>();

        for (int i = 0; i < numberOfCards; i++) {
            int value = FastReader.nextInt();
            Card card = new Card(i, value);
            cards.add(card);
        }

        addRotatedCards(cards);

        boolean possible = twoSum(cards, targetSum);
        System.out.println(possible ? "YES" : "NO");
    }

    private static boolean twoSum(List<Card> cards, int targetSum) {
        Map<Integer, List<Card>> cardMap = new HashMap<>();

        for (Card card : cards) {
            int complementValue = targetSum - card.value;
            if (cardMap.containsKey(complementValue)) {
                List<Card> complementCards = cardMap.get(complementValue);

                for (Card complementCard : complementCards) {
                    if (complementCard.id != card.id) {
                        return true;
                    }
                }
            }

            if (!cardMap.containsKey(card.value)) {
                cardMap.put(card.value, new ArrayList<>());
            }
            cardMap.get(card.value).add(card);
        }
        return false;
    }

    private static void addRotatedCards(List<Card> cards) {
        List<Card> rotatedCards = new ArrayList<>();
        for (Card card : cards) {
            int value = card.value;
            if (canBeRotated(value)) {
                int rotatedValue = rotateValue(value);
                rotatedCards.add(new Card(card.id, rotatedValue));
            }
        }
        cards.addAll(rotatedCards);
    }

    private static boolean canBeRotated(int value) {
        while (value > 0) {
            int digit = value % 10;
            if (digit == 3 || digit == 4 || digit == 7) {
                return false;
            }
            value /= 10;
        }
        return true;
    }

    private static int rotateValue(int value) {
        if (value == 0) {
            return 0;
        }
        StringBuilder rotatedValue = new StringBuilder();

        while (value > 0) {
            int digit = value % 10;
            int rotatedDigit;

            switch (digit) {
                case 1: rotatedDigit = 1; break;
                case 2: rotatedDigit = 2; break;
                case 5: rotatedDigit = 5; break;
                case 6: rotatedDigit = 9; break;
                case 8: rotatedDigit = 8; break;
                case 9: rotatedDigit = 6; break;
                default: rotatedDigit = 0;
            }

            rotatedValue.append(rotatedDigit);
            value /= 10;
        }
        return Integer.parseInt(rotatedValue.toString());
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
}
