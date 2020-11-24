package chapter1.section6.e.interesting.real.life.problems.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 01/11/20.
 */
public class FBIUniversalControlNumbers {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();
        int[] multiplier = { 2, 4, 5, 7, 8, 10, 11, 13 };

        for (int t = 0; t < tests; t++) {
            int dataSetNumber = scanner.nextInt();
            String ucn = scanner.next();
            StringBuilder validUcn = new StringBuilder();
            long checkDigit = 0;

            for (int i = 0; i < ucn.length() - 1; i++) {
                int digit = getDigit(ucn.charAt(i));
                validUcn.append(Character.forDigit(digit, 27));

                checkDigit += digit * multiplier[i];
            }
            checkDigit %= 27;

            int currentCheckDigit = getDigit(ucn.charAt(ucn.length() - 1));
            if (currentCheckDigit == checkDigit) {
                long valueBase10 = Long.parseLong(validUcn.toString(), 27);
                System.out.printf("%d %d\n", dataSetNumber, valueBase10);
            } else {
                System.out.printf("%d Invalid\n", dataSetNumber);
            }
        }
    }

    private static int getDigit(char digit) {
        if (digit == 'B') {
            return 8;
        } else if (digit == 'I') {
            return 1;
        } else if (digit == 'O' || digit == 'Q') {
            return 0;
        } else if (digit == 'S') {
            return 5;
        } else if (digit == 'Z') {
            return 2;
        } else if (digit == 'A') {
            return 10;
        } else if (digit == 'G' || digit == 'C') {
            return 11;
        } else if (digit == 'D') {
            return 12;
        } else if (digit == 'E') {
            return 13;
        } else if (digit == 'F') {
            return 14;
        } else if (digit == 'H') {
            return 15;
        } else if (digit == 'J') {
            return 16;
        } else if (digit == 'K') {
            return 17;
        } else if (digit == 'L') {
            return 18;
        } else if (digit == 'M') {
            return 19;
        } else if (digit == 'N') {
            return 20;
        } else if (digit == 'P') {
            return 21;
        } else if (digit == 'R') {
            return 22;
        } else if (digit == 'T') {
            return 23;
        } else if (digit == 'V' || digit == 'U' || digit == 'Y') {
            return 24;
        } else if (digit == 'W') {
            return 25;
        } else if (digit == 'X') {
            return 26;
        }
        return Character.getNumericValue(digit);
    }
}
