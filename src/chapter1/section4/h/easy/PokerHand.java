package chapter1.section4.h.easy;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Rene Argento on 16/09/20.
 */
public class PokerHand {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<Character, Integer> rankFrequencies = new HashMap<>();

        for (int i = 0; i < 5; i++) {
            char rank = scanner.next().charAt(0);
            rankFrequencies.put(rank, rankFrequencies.getOrDefault(rank, 0) + 1);
        }

        int strength = 0;
        for (int frequency : rankFrequencies.values()) {
            strength = Math.max(strength, frequency);
        }
        System.out.println(strength);
    }

}
