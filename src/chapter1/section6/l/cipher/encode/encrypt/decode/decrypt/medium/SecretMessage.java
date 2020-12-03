package chapter1.section6.l.cipher.encode.encrypt.decode.decrypt.medium;

import java.util.Scanner;

/**
 * Created by Rene Argento on 29/11/20.
 */
public class SecretMessage {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            String message = scanner.next();
            System.out.println(encrypt(message));
        }
    }

    private static String encrypt(String message) {
        int originalLength = message.length();
        int length = (int) Math.sqrt(originalLength);
        if (length * length != originalLength) {
            length++;
        }

        char[][] rotatedTable = createRotatedTable(message, length);
        return getSecretMessage(rotatedTable);
    }

    private static char[][] createRotatedTable(String message, int length) {
        char[][] table = new char[length][length];

        for (int row = 0; row < length; row++) {
            for (int column = 0; column < length; column++) {
                int index = row * length + column;
                int rotatedRow = column;
                int rotatedColumn = length - row - 1;

                if (index < message.length()) {
                    table[rotatedRow][rotatedColumn] = message.charAt(index);
                } else {
                    table[rotatedRow][rotatedColumn] = '*';
                }
            }
        }
        return table;
    }

    private static String getSecretMessage(char[][] table) {
        StringBuilder secretMessage = new StringBuilder();
        int length = table.length;

        for (int row = 0; row < length; row++) {
            for (int column = 0; column < length; column++) {
                if (table[row][column] != '*') {
                    secretMessage.append(table[row][column]);
                }
            }
        }
        return secretMessage.toString();
    }
}
