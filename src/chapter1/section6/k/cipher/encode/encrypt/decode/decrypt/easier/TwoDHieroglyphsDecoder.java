package chapter1.section6.k.cipher.encode.encrypt.decode.decrypt.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 25/11/20.
 */
public class TwoDHieroglyphsDecoder {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();
        scanner.nextLine();

        for (int t = 0; t < tests; t++) {
            if (t >= 1) {
                scanner.nextLine();
            }

            String line = scanner.nextLine();
            char[][] message = new char[8][line.length()];

            for (int row = 0; row < 8; row++) {
                message[row] = scanner.nextLine().toCharArray();
            }
            scanner.nextLine();

            for (int column = 1; column < message[0].length - 1; column++) {
                int characterValue = 0;

                for (int row = 0; row < message.length; row++) {
                    if (message[row][column] == '\\') {
                        characterValue += Math.pow(2, row);
                    }
                }
                char character = (char) characterValue;
                System.out.print(character);
            }
            System.out.println();
        }
    }
}
