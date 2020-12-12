package chapter1.section6.m.input.parsing.iterative;

import java.util.Scanner;

/**
 * Created by Rene Argento on 10/12/20.
 */
public class TripleTexting {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String tripleWord = scanner.next();
        int wordLength = tripleWord.length() / 3;

        int startIndex2 = wordLength;
        int startIndex3 = wordLength * 2;

        StringBuilder originalWord = new StringBuilder();

        for (int i = 0; i < wordLength; i++) {
            char character1 = tripleWord.charAt(i);
            char character2 = tripleWord.charAt(startIndex2 + i);
            char character3 = tripleWord.charAt(startIndex3 + i);

            if (character1 == character2 || character1 == character3) {
                originalWord.append(character1);
            } else {
                originalWord.append(character2);
            }
        }
        System.out.println(originalWord);
    }
}
