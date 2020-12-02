package chapter1.section6.k.cipher.encode.encrypt.decode.decrypt.easier;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Rene Argento on 28/11/20.
 */
public class KnownPlaintextAttack {

    private static final int START_INDEX = (int) 'a';
    private static final int END_INDEX = (int) 'z';

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();
        scanner.nextLine();

        for (int t = 0; t < tests; t++) {
            String[] sentenceWords = scanner.nextLine().split(" ");
            String decryptedWord = scanner.nextLine();

            StringBuilder possibleKeys = new StringBuilder();
            for (String word : sentenceWords) {
                if (word.length() == decryptedWord.length()) {
                    Character possibleKey = checkPossibleKey(word, decryptedWord);
                    if (possibleKey != null) {
                        possibleKeys.append(possibleKey);
                    }
                }
            }

            char[] keys = possibleKeys.toString().toCharArray();
            Arrays.sort(keys);
            System.out.println(String.valueOf(keys));
        }
    }

    private static Character checkPossibleKey(String word, String decryptedWord) {
        int key = adjustedKey(word.charAt(0), decryptedWord.charAt(0));
        boolean isPossible = true;

        for (int i = 1; i < word.length(); i++) {
            int currentKey = adjustedKey(word.charAt(i), decryptedWord.charAt(i));
            if (currentKey != key) {
                isPossible = false;
                break;
            }
        }

        if (isPossible) {
            return (char) ('a' + key);
        } else {
            return null;
        }
    }

    private static int adjustedKey(int index1, int index2) {
        if (index1 >= index2) {
            return index1 - index2;
        }
        return (END_INDEX - index2) + (index1 - START_INDEX + 1);
    }
}
