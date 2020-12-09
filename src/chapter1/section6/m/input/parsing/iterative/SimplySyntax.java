package chapter1.section6.m.input.parsing.iterative;

import java.util.Scanner;

/**
 * Created by Rene Argento on 07/12/20.
 */
public class SimplySyntax {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String sentence = scanner.nextLine();
            int lastValidIndex = isValid(sentence, 0);
            if (lastValidIndex == sentence.length() - 1) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        }
    }

    private static int isValid(String sentence, int index) {
        char symbol = sentence.charAt(index);

        if (symbol >= 'p' && symbol <= 'z') {
            return index;
        }
        if (symbol == 'N') {
            if (index < sentence.length() - 1) {
                return isValid(sentence, index + 1);
            } else {
                return -1;
            }
        }

        if (index < sentence.length() - 2) {
            int result1 = isValid(sentence, index + 1);

            if (result1 == -1 || result1 == sentence.length() - 1) {
                return -1;
            }
            return isValid(sentence, result1 + 1);
        }
        return -1;
    }
}