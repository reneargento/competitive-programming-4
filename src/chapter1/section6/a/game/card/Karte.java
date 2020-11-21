package chapter1.section6.a.game.card;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by Rene Argento on 12/10/20.
 */
public class Karte {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String cards = scanner.next();

        Set<String> pCards = new HashSet<>();
        Set<String> kCards = new HashSet<>();
        Set<String> hCards = new HashSet<>();
        Set<String> tCards = new HashSet<>();

        boolean error = false;

        for (int i = 0; i < cards.length(); i+= 3) {
            char suit = cards.charAt(i);
            char[] cardValues = { suit, cards.charAt(i + 1), cards.charAt(i + 2) };
            String card = new String(cardValues);

            Set<String> cardSetToCheck;

            switch (suit) {
                case 'P': cardSetToCheck = pCards; break;
                case 'K': cardSetToCheck = kCards; break;
                case 'H': cardSetToCheck = hCards; break;
                default: cardSetToCheck = tCards;
            }

            if (cardSetToCheck.contains(card)) {
                error = true;
                break;
            }
            cardSetToCheck.add(card);
        }

        if (error) {
            System.out.println("GRESKA");
        } else {
            int pMissing = 13 - pCards.size();
            int kMissing = 13 - kCards.size();
            int hMissing = 13 - hCards.size();
            int tMissing = 13 - tCards.size();
            System.out.printf("%d %d %d %d\n", pMissing, kMissing, hMissing, tMissing);
        }
    }

}
