package chapter1.section6.k.cipher.encode.encrypt.decode.decrypt.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 26/11/20.
 */
public class EncodedMessage {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            String encodedMessage = scanner.next();

            int size = encodedMessage.length();
            int gridSize = (int) Math.sqrt(size);

            char[] decodedMessage = new char[size];
            int encodedMessageIndex = 0;

            for (int position1 = gridSize - 1; position1 >= 0; position1--) {
                int decodedMessageIndex = position1;

                for (int i = 0; i < gridSize; i++) {
                    char character = encodedMessage.charAt(decodedMessageIndex);
                    decodedMessage[encodedMessageIndex++] = character;
                    decodedMessageIndex += gridSize;
                }
            }
            System.out.println(decodedMessage);
        }
    }
}
