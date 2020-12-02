package chapter1.section6.k.cipher.encode.encrypt.decode.decrypt.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 25/11/20.
 */
public class MobileSMS {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();
        char[][] keyboardMapping = createKeyboardMapping();

        for (int t = 0; t < tests; t++) {
            int messageLength = scanner.nextInt();
            int[] keys = new int[messageLength];
            int[] presses = new int[messageLength];

            for (int i = 0; i < messageLength; i++) {
                keys[i] = scanner.nextInt();
            }

            for (int i = 0; i < messageLength; i++) {
                presses[i] = scanner.nextInt() - 1;
            }

            StringBuilder message = new StringBuilder();
            for (int i = 0; i < messageLength; i++) {
                char character = keyboardMapping[keys[i]][presses[i]];
                message.append(character);
            }
            System.out.println(message);
        }
    }

    private static char[][] createKeyboardMapping() {
        return new char[][]{
                {' '},
                {'.', ',', '?', '"'},
                {'a', 'b', 'c'},
                {'d', 'e', 'f'},
                {'g', 'h', 'i'},
                {'j', 'k', 'l'},
                {'m', 'n', 'o'},
                {'p', 'q', 'r', 's'},
                {'t', 'u', 'v'},
                {'w', 'x', 'y', 'z'},
        };
    }
}
