package chapter1.section6.l.cipher.encode.encrypt.decode.decrypt.medium;

import java.util.Scanner;

/**
 * Created by Rene Argento on 01/12/20.
 */
public class Tajna {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String message = scanner.next();
        int length = message.length();

        int rows = (int) Math.sqrt(length);
        int columns = 1;
        boolean foundSize = false;

        for (int r = rows; r >= 1; r--) {
            columns = length / r;
            if (r * columns == length) {
                rows = r;
                foundSize = true;
                break;
            }
        }

        if (!foundSize) {
            rows = 1;
            columns = length;
        }

        char[][] matrix = createMatrix(message, rows, columns);
        System.out.println(getMessageFromMatrix(matrix));
    }

    private static char[][] createMatrix(String message, int rows, int columns) {
        int messageIndex = 0;

        char[][] matrix = new char[rows][columns];
        for (int c = 0; c < columns; c++) {
            for (int r = 0; r < rows; r++) {
                matrix[r][c] = message.charAt(messageIndex++);
            }
        }
        return matrix;
    }

    private static String getMessageFromMatrix(char[][] matrix) {
        StringBuilder message = new StringBuilder();

        for (int r = 0; r < matrix.length; r++) {
            for (int c = 0; c < matrix[0].length; c++) {
                message.append(matrix[r][c]);
            }
        }
        return message.toString();
    }
}
