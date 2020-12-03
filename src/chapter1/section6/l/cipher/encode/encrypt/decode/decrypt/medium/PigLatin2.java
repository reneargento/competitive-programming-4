package chapter1.section6.l.cipher.encode.encrypt.decode.decrypt.medium;

import java.util.Scanner;

/**
 * Created by Rene Argento on 30/11/20.
 */
// Kattis - piglatin
public class PigLatin2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String[] words = scanner.nextLine().split(" ");

            for (int i = 0; i < words.length; i++) {
                System.out.print(convertWordsToPigLatin(words[i]));

                if (i != words.length - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    private static String convertWordsToPigLatin(String word) {
        if (isVowel(word.charAt(0))) {
            return word + "yay";
        } else {
            int firstVowelIndex = 1;
            for (int i = 1; i < word.length(); i++) {
                if (isVowel(word.charAt(i))) {
                    firstVowelIndex = i;
                    break;
                }
            }

            return word.substring(firstVowelIndex) + word.substring(0, firstVowelIndex) + "ay";
        }

    }

    private static boolean isVowel(char character) {
        return character == 'a' ||
                character == 'e' ||
                character == 'i' ||
                character == 'o' ||
                character == 'u' ||
                character == 'y';
    }
}
