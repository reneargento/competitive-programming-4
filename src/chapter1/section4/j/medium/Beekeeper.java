package chapter1.section4.j.medium;

import java.util.Scanner;

/**
 * Created by Rene Argento on 25/09/20.
 */
public class Beekeeper {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int words = scanner.nextInt();

        while (words != 0) {
            String favoriteWord = "";
            int maxPairs = 0;

            for (int i = 0; i < words; i++) {
                int pairs = 0;
                String word = scanner.next();

                for (int c = 0; c < word.length() - 1; c++) {
                    char currentChar = word.charAt(c);
                    char nextChar = word.charAt(c + 1);

                    if (isVowel(currentChar) && currentChar == nextChar) {
                        pairs++;
                        c++;
                    }
                }

                if (pairs >= maxPairs) {
                    maxPairs = pairs;
                    favoriteWord = word;
                }
            }

            System.out.println(favoriteWord);
            words = scanner.nextInt();
        }
    }

    private static boolean isVowel(char character) {
        return character == 'a'
                || character == 'e'
                || character == 'i'
                || character == 'o'
                || character == 'u'
                || character == 'y';
    }

}
