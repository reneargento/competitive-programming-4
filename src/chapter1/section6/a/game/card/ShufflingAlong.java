package chapter1.section6.a.game.card;

import java.util.*;

/**
 * Created by Rene Argento on 08/10/20.
 */
public class ShufflingAlong {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int deckSize = scanner.nextInt();
        String type = scanner.next();
        int middle = deckSize / 2;
        Set<List<Integer>> orders = new HashSet<>();
        int shuffles = 0;

        List<Integer> cards = new ArrayList<>();
        for (int i = 0; i < deckSize; i++) {
            cards.add(i);
        }
        orders.add(cards);

        while (true) {
            shuffles++;

            if (type.equals("out")) {
                int secondHalfStart = deckSize % 2 == 0 ? middle : middle + 1;
                cards = shuffle(cards, secondHalfStart, true);

                if (orders.contains(cards)) {
                    break;
                }
            } else {
                cards = shuffle(cards, middle, false);

                if (orders.contains(cards)) {
                    break;
                }
            }
            orders.add(cards);
        }

        System.out.println(shuffles);
    }

    private static List<Integer> shuffle(List<Integer> cards, int secondHalfStart, boolean outShuffle) {
        List<Integer> newOrder = new ArrayList<>();
        boolean firstHalf = outShuffle;
        int firstHalfIndex = 0;
        int secondHalfIndex = secondHalfStart;

        while (firstHalfIndex < secondHalfStart || secondHalfIndex < cards.size()) {
            if (firstHalf) {
                newOrder.add(cards.get(firstHalfIndex));
                firstHalfIndex++;
            } else {
                newOrder.add(cards.get(secondHalfIndex));
                secondHalfIndex++;
            }
            firstHalf = !firstHalf;
        }

        return newOrder;
    }

}
