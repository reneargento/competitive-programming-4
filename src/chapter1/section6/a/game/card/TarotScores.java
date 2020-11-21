package chapter1.section6.a.game.card;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by Rene Argento on 10/10/20.
 */
public class TarotScores {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 1; t <= tests; t++) {
            int cards = scanner.nextInt();
            scanner.nextLine();
            double points = 0;

            Set<String> oudlers = new HashSet<>();
            for (int c = 0; c < cards; c++) {
                String card = scanner.nextLine();
                if (isOudler(card)) {
                    oudlers.add(card);
                    points += 4.5;
                } else {
                    points += getPoints(card);
                }
            }

            double requiredPoints = getRequiredPoints(oudlers);
            double difference = points - requiredPoints;

            if (t > 1) {
                System.out.println();
            }
            System.out.printf("Hand #%d\n", t);

            if (difference >= 0) {
                System.out.printf("Game won by %.0f point(s).\n", difference);
            } else {
                System.out.printf("Game lost by %.0f point(s).\n", -1 * difference);
            }
        }
    }

    private static boolean isOudler(String card) {
        return card.equals("fool")
                || card.equals("one of trumps")
                || card.equals("twenty-one of trumps");

    }

    private static double getPoints(String card) {
        String value = card.split(" ")[0];
        switch (value) {
            case "king": return 4.5;
            case "queen": return 3.5;
            case "knight": return 2.5;
            case "jack": return 1.5;
            default: return 0.5;
        }
    }

    private static double getRequiredPoints(Set<String> oudlers) {
        switch (oudlers.size()) {
            case 3: return 36;
            case 2: return 41;
            case 1: return 51;
            default: return 56;
        }
    }

}
