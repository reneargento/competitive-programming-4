package chapter1.section6.m.input.parsing.iterative;

import java.util.Scanner;

/**
 * Created by Rene Argento on 09/12/20.
 */
public class LongestWord {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int maxLength = 0;
        String longestWord = "";
        boolean endOfInput = false;

        while (true) {
            String line = scanner.nextLine();
            StringBuilder currentWord = new StringBuilder();

            for (int i = 0; i < line.length(); i++) {
                char symbol = line.charAt(i);

                if (!isLetterOrDash(symbol) || i == line.length() - 1) {
                    if (i == line.length() - 1 && isLetterOrDash(symbol)) {
                        currentWord.append(symbol);
                    }

                    if (currentWord.length() > 0) {
                        if (currentWord.toString().equals("E-N-D")) {
                            endOfInput = true;
                            break;
                        }

                        if (currentWord.length() > maxLength) {
                            maxLength = currentWord.length();
                            longestWord = currentWord.toString();
                        }
                        currentWord = new StringBuilder();
                    }
                } else {
                    currentWord.append(symbol);
                }
            }

            if (endOfInput) {
                break;
            }
        }
        System.out.println(longestWord.toLowerCase());
    }

    private static boolean isLetterOrDash(char symbol) {
        return Character.isLetter(symbol) || symbol == '-';
    }
}
