package chapter1.section6.c.games.other.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 16/10/20.
 */
public class MasterMindHelper {

    private static int guesses;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            String guess = scanner.next();
            int correctPlaces = scanner.nextInt();
            int wrongPlaces = scanner.nextInt();

            guesses = 0;
            char[] guessChars = guess.toCharArray();
            char[] code = new char[guess.length()];
            countCodes(guessChars, correctPlaces, wrongPlaces, 0, code);
            System.out.println(guesses);
        }
    }

    private static void countCodes(char[] guess, int correctPlaces, int wrongPlaces, int index, char[] code) {
        if (index == guess.length) {
            int correctInside = 0;
            int correctOutside = 0;

            char[] guessCopy = new char[guess.length];
            System.arraycopy(guess, 0, guessCopy, 0, guess.length);
            char[] codeCopy = new char[code.length];
            System.arraycopy(code, 0, codeCopy, 0, code.length);

            for (int i = 0; i < guessCopy.length; i++) {
                if (guessCopy[i] == codeCopy[i]) {
                    correctInside++;
                    guessCopy[i] = '0';
                    codeCopy[i] = '0';
                }
            }

            for (int i = 0; i < guessCopy.length; i++) {
                if (guessCopy[i] != '0') {
                    for (int j = 0; j < codeCopy.length; j++) {
                        if (guessCopy[i] == codeCopy[j]) {
                            correctOutside++;
                            guessCopy[i] = '0';
                            codeCopy[j] = '0';
                            break;
                        }
                    }
                }
            }

            if (correctInside == correctPlaces && correctOutside == wrongPlaces) {
                guesses++;
            }
        } else {
            for (int i = 1; i <= 9; i++) {
                code[index] = (char) ('0' + i);
                countCodes(guess, correctPlaces, wrongPlaces, index + 1, code);
            }
        }
    }

}
