package chapter1.section6.l.cipher.encode.encrypt.decode.decrypt.medium;

import java.util.Scanner;

/**
 * Created by Rene Argento on 01/12/20.
 */
public class DigitalFortress {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();
        scanner.nextLine();

        for (int t = 0; t < tests; t++) {
            String cipherText = scanner.nextLine();
            int length = cipherText.length();
            int sqrt = (int) Math.sqrt(length);
            if (sqrt * sqrt != length) {
                System.out.println("INVALID");
            } else {
                char[][] matrix = createMatrix(cipherText, sqrt);
                String decryptedString = decrypt(matrix);
                System.out.println(decryptedString);
            }
        }
    }

    private static char[][] createMatrix(String cipherText, int dimension) {
        char[][] matrix = new char[dimension][dimension];
        for (int r = 0; r < matrix.length; r++) {
            for (int c = 0; c < matrix[0].length; c++) {
                int index = r * dimension + c;
                matrix[r][c] = cipherText.charAt(index);
            }
        }
        return matrix;
    }

    private static String decrypt(char[][] matrix) {
        StringBuilder decryptedString = new StringBuilder();

        for (int c = 0; c < matrix.length; c++) {
            for (int r = 0; r < matrix.length; r++) {
                decryptedString.append(matrix[r][c]);
            }
        }
        return decryptedString.toString();
    }
}
