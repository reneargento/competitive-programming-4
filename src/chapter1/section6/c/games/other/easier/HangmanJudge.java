package chapter1.section6.c.games.other.easier;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by Rene Argento on 16/10/20.
 */
public class HangmanJudge {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int round = scanner.nextInt();

        while (round != -1) {
            String answer = scanner.next();

            Set<Character> letters = new HashSet<>();
            for (int i = 0; i < answer.length(); i++) {
                letters.add(answer.charAt(i));
            }

            String attempt = scanner.next();
            int wrongGuesses = 0;

            for (int i = 0; i < attempt.length(); i++) {
                char letter = attempt.charAt(i);

                if (!letters.contains(letter)) {
                    wrongGuesses++;

                    if (wrongGuesses == 7) {
                        break;
                    }
                }
                letters.remove(letter);
            }

            System.out.printf("Round %d\n", round);
            if (letters.isEmpty()) {
                System.out.println("You win.");
            } else if (wrongGuesses == 7) {
                System.out.println("You lose.");
            } else {
                System.out.println("You chickened out.");
            }
            round = scanner.nextInt();
        }
    }

}
