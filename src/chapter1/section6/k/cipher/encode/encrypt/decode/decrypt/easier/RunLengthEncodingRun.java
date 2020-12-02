package chapter1.section6.k.cipher.encode.encrypt.decode.decrypt.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 29/11/20.
 */
public class RunLengthEncodingRun {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String method = scanner.next();
        String message = scanner.next();
        String result;

        if (method.equals("E")) {
            result = encode(message);
        } else {
            result = decode(message);
        }
        System.out.println(result);
    }

    private static String encode(String message) {
        StringBuilder encodedMessage = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
            char character = message.charAt(i);
            int runLength = 1;

            for (int j = i + 1; j < message.length(); j++) {
                if (message.charAt(j) == character) {
                    runLength++;
                } else {
                    break;
                }
            }

            i += runLength - 1;
            encodedMessage.append(character).append(runLength);
        }
        return encodedMessage.toString();
    }

    private static String decode(String message) {
        StringBuilder decodedMessage = new StringBuilder();

        for (int i = 0; i < message.length(); i += 2) {
            char character = message.charAt(i);
            int runLength = Character.getNumericValue(message.charAt(i + 1));

            for (int r = 0; r < runLength; r++) {
                decodedMessage.append(character);
            }
        }
        return decodedMessage.toString();
    }
}
