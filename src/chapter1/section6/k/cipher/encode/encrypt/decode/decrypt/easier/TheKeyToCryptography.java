package chapter1.section6.k.cipher.encode.encrypt.decode.decrypt.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 29/11/20.
 */
public class TheKeyToCryptography {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String ciphertext = scanner.next();
        StringBuilder secretWord = new StringBuilder(scanner.next());

        StringBuilder originalMessage = new StringBuilder();

        for (int i = 0; i < ciphertext.length(); i++) {
            char originalChar = shiftBackwards(ciphertext.charAt(i), secretWord.charAt(i));
            secretWord.append(originalChar);
            originalMessage.append(originalChar);
        }

        System.out.println(originalMessage);
    }

    private static char shiftBackwards(char character, char baseCharacter) {
        int newPosition = getValue(character) - getValue(baseCharacter);
        if (newPosition < 0) {
            newPosition = 26 + newPosition;
        }
        return (char) ('A' + newPosition);
    }

    private static int getValue(char character) {
        return character - 'A';
    }
}
