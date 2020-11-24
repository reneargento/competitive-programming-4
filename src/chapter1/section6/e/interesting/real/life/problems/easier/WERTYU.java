package chapter1.section6.e.interesting.real.life.problems.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 29/10/20.
 */
public class WERTYU {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char[] characters = getDecodeChars();

        while (scanner.hasNext()) {
            System.out.println(decode(scanner.nextLine(), characters));
        }
    }

    private static char[] getDecodeChars() {
        return new char[] {'`', '1', '9', '0', '-', '=', 'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', '[', ']',
        '\\', 'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', ';', '\'', 'Z', 'X', 'C', 'V', 'B', 'N', 'M', ',', '.', '/'};
    }

    private static String decode(String original, char[] characters) {
        StringBuilder decodedString = new StringBuilder();

        for (int i = 0; i < original.length(); i++) {
            char character = original.charAt(i);
            if (character == ' ') {
                decodedString.append(character);
            } else {
                if (Character.isDigit(character) && Character.getNumericValue(character) > 1) {
                    decodedString.append(Character.getNumericValue(character) - 1);
                } else {
                    int index = -1;
                    for (int c = 1; c < characters.length; c++) {
                        if (characters[c] == character) {
                            index = c;
                            break;
                        }
                    }
                    if (index != -1) {
                        decodedString.append(characters[index - 1]);
                    }
                }
            }
        }
        return decodedString.toString();
    }
}
