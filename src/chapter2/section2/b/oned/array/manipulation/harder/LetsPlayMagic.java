package chapter2.section2.b.oned.array.manipulation.harder;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;

/**
 * Created by Rene Argento on 25/01/21.
 */
public class LetsPlayMagic {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int cardsNumber = scanner.nextInt();

        while (cardsNumber != 0) {
            String[] cards = new String[cardsNumber];
            List<Integer> indexes = new ArrayList<>();
            initIndexes(indexes, cardsNumber);
            int index = -1;

            for (int i = 0; i < cardsNumber; i++) {
                String card = scanner.next();
                String cardName = scanner.next();

                index = (index + cardName.length()) % indexes.size();
                int arrayIndex = indexes.get(index);
                cards[arrayIndex] = card;

                indexes.remove(index);
                index--;
                if (index < 0) {
                    index = indexes.size() - 1;
                }
            }

            StringJoiner cardsDescription = new StringJoiner(" ");
            for (String card : cards) {
                cardsDescription.add(card);
            }
            System.out.println(cardsDescription);

            cardsNumber = scanner.nextInt();
        }
    }

    private static void initIndexes(List<Integer> indexes, int cardsNumber) {
        for (int i = 0; i < cardsNumber; i++) {
            indexes.add(i);
        }
    }
}
