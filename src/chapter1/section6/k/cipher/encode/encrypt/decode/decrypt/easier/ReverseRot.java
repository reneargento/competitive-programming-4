package chapter1.section6.k.cipher.encode.encrypt.decode.decrypt.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 29/11/20.
 */
public class ReverseRot {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int forwardRotation = scanner.nextInt();

        while (forwardRotation != 0) {
            StringBuilder message = new StringBuilder(scanner.next());
            message.reverse();
            StringBuilder encryptedMessage = new StringBuilder();

            for (char character : message.toString().toCharArray()) {
                char rotatedCharacter = rotateCharacter(character, forwardRotation);
                encryptedMessage.append(rotatedCharacter);
            }

            System.out.println(encryptedMessage);
            forwardRotation = scanner.nextInt();
        }
    }

    private static char rotateCharacter(char character, int forwardRotation) {
        int position = (getValue(character) + forwardRotation) % 28;
        if (position == 26) {
            return '_';
        }
        if (position == 27) {
            return '.';
        }
        return (char) ('A' + position);
    }

    private static int getValue(char character) {
        if (character == '_') {
            return 26;
        }
        if (character == '.') {
            return 27;
        }
        return character - 'A';
    }
}
