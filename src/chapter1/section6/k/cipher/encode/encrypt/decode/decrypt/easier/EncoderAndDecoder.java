package chapter1.section6.k.cipher.encode.encrypt.decode.decrypt.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 26/11/20.
 */
public class EncoderAndDecoder {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String message = scanner.nextLine();

            if (message.length() > 0 && Character.isDigit(message.charAt(0))) {
                System.out.println(decode(message));
            } else {
                System.out.println(encode(message));
            }
        }
    }

    private static String decode(String message) {
        StringBuilder reverseEncode = new StringBuilder(message).reverse();

        StringBuilder decodedMessage = new StringBuilder();
        int index = 0;

        while (index < reverseEncode.length()) {
            int endIndex = reverseEncode.charAt(index) == '1' ? 3 : 2;

            char character = (char) Integer.parseInt(reverseEncode.substring(index, index + endIndex));
            decodedMessage.append(character);
            index += endIndex;
        }
        return decodedMessage.toString();
    }

    private static String encode(String message) {
        StringBuilder encodedMessage = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            int ascIIValue = message.charAt(i);
            encodedMessage.append(ascIIValue);
        }
        return encodedMessage.reverse().toString();
    }
}
