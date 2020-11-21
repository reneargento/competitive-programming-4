package chapter1.section6.a.game.card;

import java.util.*;

/**
 * Created by Rene Argento on 08/10/20.
 */
public class MemoryMatch {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int cards = scanner.nextInt();
        int turns = scanner.nextInt();

        Map<String, Set<Integer>> locations = new HashMap<>();
        Set<String> alreadyMatched = new HashSet<>();
        Set<Integer> locationsVisited = new HashSet<>();

        for (int i = 0; i < turns; i++) {
            int location1 = scanner.nextInt();
            int location2 = scanner.nextInt();
            String card1 = scanner.next();
            String card2 = scanner.next();

            Set<Integer> cardLocations1 = locations.getOrDefault(card1, new HashSet<>());
            cardLocations1.add(location1);
            locations.put(card1, cardLocations1);

            Set<Integer> cardLocations2 = locations.getOrDefault(card2, new HashSet<>());
            cardLocations2.add(location2);
            locations.put(card2, cardLocations2);

            if (card1.equals(card2)) {
                alreadyMatched.add(card1);
            }
            locationsVisited.add(location1);
            locationsVisited.add(location2);
        }

        int matchingPairs = 0;
        int unmatchedPairs = 0;
        for (String card : locations.keySet()) {
            if (alreadyMatched.contains(card)) {
                continue;
            }

            Set<Integer> cardLocations = locations.get(card);
            if (cardLocations.size() == 2) {
                matchingPairs++;
            } else if (cardLocations.size() == 1) {
                unmatchedPairs++;
            }
        }

        int locationsNotVisited = cards - locationsVisited.size();
        if (locationsNotVisited == 2 && unmatchedPairs != 2) {
            matchingPairs++;
        } else if (locationsNotVisited == unmatchedPairs) {
            matchingPairs += unmatchedPairs;
        }

        System.out.println(matchingPairs);
    }

}
