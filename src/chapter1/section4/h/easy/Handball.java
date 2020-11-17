package chapter1.section4.h.easy;

import java.util.Scanner;

/**
 * Created by Rene Argento on 16/09/20.
 */
public class Handball {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            int players = scanner.nextInt();
            int matches = scanner.nextInt();

            int playersWithAllMatchesScore = 0;

            for (int p = 0; p < players; p++) {
                int matchesWithScore = 0;

                for (int m = 0; m < matches; m++) {
                    if (scanner.nextInt() > 0) {
                        matchesWithScore++;
                    }
                }

                if (matchesWithScore == matches) {
                    playersWithAllMatchesScore++;
                }
            }

            System.out.println(playersWithAllMatchesScore);
        }
    }

}
