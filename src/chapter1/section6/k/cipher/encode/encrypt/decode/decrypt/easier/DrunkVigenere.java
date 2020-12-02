package chapter1.section6.k.cipher.encode.encrypt.decode.decrypt.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 28/11/20.
 */
public class DrunkVigenere {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String message = scanner.next();
        String key = scanner.next();

        StringBuilder decryptedMessage = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            char messageChar = message.charAt(i);
            char keyChar = key.charAt(i);

            int positionsToShift = getPosition(keyChar);
            char shiftedChar = (i % 2 == 0) ? shiftBackward(messageChar, positionsToShift) :
                    shiftForward(messageChar, positionsToShift);
            decryptedMessage.append(shiftedChar);
        }
        System.out.println(decryptedMessage);
    }

    private static char shiftForward(char character, int positions) {
        int newPosition = (getPosition(character) + positions) % 26;
        return (char) ('A' + newPosition);
    }

    private static char shiftBackward(char character, int positions) {
        int newPosition = getPosition(character) - positions;
        if (newPosition < 0) {
            newPosition = 26 + newPosition;
        }
        return (char) ('A' + newPosition);
    }

    private static int getPosition(char character) {
        return character - 'A';
    }
}
