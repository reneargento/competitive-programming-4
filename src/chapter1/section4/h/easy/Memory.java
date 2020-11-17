package chapter1.section4.h.easy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Rene Argento on 18/09/20.
 */
public class Memory {

    public static void main(String[] args) {
        play();
    }

    public static void play() {
        Map<Character, Integer> cards = new HashMap<>();
        Set<Character> cardsFound = new HashSet<>();
        int card = 1;
        int candies = 0;

        while (candies != 25) {
            char character = faceup(card);

            if (cards.containsKey(character)) {
                int otherCard = cards.get(character);
                char otherCardValue = faceup(otherCard);
                if (otherCardValue == character && !cardsFound.contains(character)) {
                    cardsFound.add(character);
                    candies++;
                }
            } else {
                cards.put(character, card);
            }

            card++;
        }
    }

    // Mock implementation of grader method
    private static char faceup(int card) {
        return 'A';
    }

}
