package chapter1.section6.k.cipher.encode.encrypt.decode.decrypt.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 27/11/20.
 */
public class DoTheUntwist {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int key = scanner.nextInt();

        while (key != 0) {
            String message = scanner.next();
            int length = message.length();
            char[] untwistedMessage = new char[length];

            for (int i = 0; i < message.length(); i++) {
                int code = getCodeFromCharacter(message.charAt(i));

                int plainCodeIndex = (key * i) % length;
                int cipherCode = (code + i) % 28;
                untwistedMessage[plainCodeIndex] = getCharacterFromCode(cipherCode);
            }
            System.out.println(untwistedMessage);

            key = scanner.nextInt();
        }
    }

    private static int getCodeFromCharacter(char character) {
        if (character == '_') {
            return 0;
        }
        if (character == '.') {
            return 27;
        }
        return character - 'a' + 1;
    }

    private static char getCharacterFromCode(int code) {
        if (code == 0) {
            return '_';
        }
        if (code == 27) {
            return '.';
        }
        return (char) ('a' + code - 1);
    }
}
