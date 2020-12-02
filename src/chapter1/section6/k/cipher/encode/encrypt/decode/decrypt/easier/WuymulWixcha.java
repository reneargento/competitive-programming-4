package chapter1.section6.k.cipher.encode.encrypt.decode.decrypt.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 24/11/20.
 */
public class WuymulWixcha {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int lowerCaseStartIndex = 'a';
        int lowerCaseEndIndex = 'z' + 1;
        int upperCaseStartIndex = 'A';
        int upperCaseEndIndex = 'Z' + 1;

        int shift = scanner.nextInt();
        scanner.nextLine();

        while (shift != 0) {
            shift = (shift % 26 + 26) % 26;
            String text = scanner.nextLine();

            for (char character : text.toCharArray()) {
                if (!Character.isLetter(character)) {
                    System.out.print(character);
                } else {
                    int characterIndex = character + shift;

                    if (Character.isUpperCase(character) && characterIndex >= upperCaseEndIndex) {
                        int offset = characterIndex % upperCaseEndIndex;
                        characterIndex = upperCaseStartIndex + offset;
                    } else if (Character.isLowerCase(character) && characterIndex >= lowerCaseEndIndex) {
                        int offset = characterIndex % lowerCaseEndIndex;
                        characterIndex = lowerCaseStartIndex + offset;
                    }

                    char decodedCharacter = (char) (characterIndex);
                    System.out.print(decodedCharacter);
                }
            }
            System.out.println();

            shift = scanner.nextInt();
            scanner.nextLine();
        }
    }
}
