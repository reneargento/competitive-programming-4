package chapter1.section6.c.games.other.easier;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Rene Argento on 17/10/20.
 */
public class MasterMindHints {

    private static class Hint {
        int strongMatches;
        int weakMatches;

        public Hint(int strongMatches, int weakMatches) {
            this.strongMatches = strongMatches;
            this.weakMatches = weakMatches;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int length = scanner.nextInt();
        int game = 1;
        List<Hint> hints = new ArrayList<>();

        while (length != 0) {
            char[] code = new char[length];
            for (int i = 0; i < length; i++) {
                code[i] = (char) ('0' + scanner.nextInt());
            }

            while (true) {
                boolean isValidGuess = false;
                char[] guess = new char[length];

                for (int i = 0; i < length; i++) {
                    int value = scanner.nextInt();
                    if (value != 0) {
                        isValidGuess = true;
                    }
                    guess[i] = (char) ('0' + value);
                }

                if (!isValidGuess) {
                    printHints(hints, game);
                    game++;
                    hints = new ArrayList<>();
                    break;
                }

                Hint hint = getHint(code, guess);
                hints.add(hint);
            }
            length = scanner.nextInt();
        }
    }

    private static Hint getHint(char[] code, char[] guess) {
        int strongMatches = 0;
        int weakMatches = 0;
        int length = code.length;

        char[] codeCopy = new char[length];
        System.arraycopy(code, 0, codeCopy, 0, length);

        for (int i = 0; i < length; i++) {
            if (codeCopy[i] == guess[i]) {
                strongMatches++;
                codeCopy[i] = '0';
                guess[i] = '0';
            }
        }

        for (int i = 0; i < length; i++) {
            if (codeCopy[i] != '0') {
                for (int j = 0; j < length; j++) {
                    if (codeCopy[i] == guess[j]) {
                        weakMatches++;
                        codeCopy[i] = '0';
                        guess[j] = '0';
                        break;
                    }
                }
            }
        }
        return new Hint(strongMatches, weakMatches);
    }

    private static void printHints(List<Hint> hits, int game) {
        System.out.printf("Game %d:\n", game);
        for (Hint hint : hits) {
            System.out.printf("    (%d,%d)\n", hint.strongMatches, hint.weakMatches);
        }
    }

}
