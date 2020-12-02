package chapter1.section6.k.cipher.encode.encrypt.decode.decrypt.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 28/11/20.
 */
public class FunnyEncryptionMethod {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            int number = scanner.nextInt();

            long hexadecimal = getHexadecimal(number);

            int binaryBits1 = count1Bits(number);
            int hexadecimalBits1 = count1Bits(hexadecimal);

            System.out.printf("%d %d\n", binaryBits1, hexadecimalBits1);
        }
    }

    private static long getHexadecimal(int decimal) {
        long hexadecimal = 0;
        long multiplier = 1;

        while (decimal > 0) {
            int digit = decimal % 10;
            hexadecimal += digit * multiplier;
            multiplier *= 16;

            decimal /= 10;
        }

        return hexadecimal;
    }

    private static int count1Bits(long number) {
        int count = 0;

        while (number > 0) {
            count++;
            number &= (number - 1);
        }
        return count;
    }
}
