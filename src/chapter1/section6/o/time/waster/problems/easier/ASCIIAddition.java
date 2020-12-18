package chapter1.section6.o.time.waster.problems.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 15/12/20.
 */
public class ASCIIAddition {

    private static final char[][] DIGIT_0 = {
            "xxxxx".toCharArray(),
            "x...x".toCharArray(),
            "x...x".toCharArray(),
            "x...x".toCharArray(),
            "x...x".toCharArray(),
            "x...x".toCharArray(),
            "xxxxx".toCharArray()
    };

    private static final char[][] DIGIT_1 = {
            "....x".toCharArray(),
            "....x".toCharArray(),
            "....x".toCharArray(),
            "....x".toCharArray(),
            "....x".toCharArray(),
            "....x".toCharArray(),
            "....x".toCharArray()
    };

    private static final char[][] DIGIT_2 = {
            "xxxxx".toCharArray(),
            "....x".toCharArray(),
            "....x".toCharArray(),
            "xxxxx".toCharArray(),
            "x....".toCharArray(),
            "x....".toCharArray(),
            "xxxxx".toCharArray()
    };

    private static final char[][] DIGIT_3 = {
            "xxxxx".toCharArray(),
            "....x".toCharArray(),
            "....x".toCharArray(),
            "xxxxx".toCharArray(),
            "....x".toCharArray(),
            "....x".toCharArray(),
            "xxxxx".toCharArray()
    };

    private static final char[][] DIGIT_4 = {
            "x...x".toCharArray(),
            "x...x".toCharArray(),
            "x...x".toCharArray(),
            "xxxxx".toCharArray(),
            "....x".toCharArray(),
            "....x".toCharArray(),
            "....x".toCharArray()
    };

    private static final char[][] DIGIT_5 = {
            "xxxxx".toCharArray(),
            "x....".toCharArray(),
            "x....".toCharArray(),
            "xxxxx".toCharArray(),
            "....x".toCharArray(),
            "....x".toCharArray(),
            "xxxxx".toCharArray()
    };

    private static final char[][] DIGIT_6 = {
            "xxxxx".toCharArray(),
            "x....".toCharArray(),
            "x....".toCharArray(),
            "xxxxx".toCharArray(),
            "x...x".toCharArray(),
            "x...x".toCharArray(),
            "xxxxx".toCharArray()
    };

    private static final char[][] DIGIT_7 = {
            "xxxxx".toCharArray(),
            "....x".toCharArray(),
            "....x".toCharArray(),
            "....x".toCharArray(),
            "....x".toCharArray(),
            "....x".toCharArray(),
            "....x".toCharArray()
    };

    private static final char[][] DIGIT_8 = {
            "xxxxx".toCharArray(),
            "x...x".toCharArray(),
            "x...x".toCharArray(),
            "xxxxx".toCharArray(),
            "x...x".toCharArray(),
            "x...x".toCharArray(),
            "xxxxx".toCharArray()
    };

    private static final char[][] DIGIT_9 = {
            "xxxxx".toCharArray(),
            "x...x".toCharArray(),
            "x...x".toCharArray(),
            "xxxxx".toCharArray(),
            "....x".toCharArray(),
            "....x".toCharArray(),
            "xxxxx".toCharArray()
    };

    private static class NumberParseResult {
        int number;
        int nextNumberColumn;

        public NumberParseResult(int number, int nextNumberColumn) {
            this.number = number;
            this.nextNumberColumn = nextNumberColumn;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        char[][] ascIINumbers = new char[7][line.length()];

        ascIINumbers[0] = line.toCharArray();

        for (int i = 1; i < 7; i++) {
            ascIINumbers[i] = scanner.nextLine().toCharArray();
        }

        NumberParseResult numberParseResult1 = getNumber(ascIINumbers, 0);
        NumberParseResult numberParseResult2 = getNumber(ascIINumbers, numberParseResult1.nextNumberColumn);
        int sum = numberParseResult1.number + numberParseResult2.number;
        char[][] resultAscII = getResultAscII(sum);
        printAscII(resultAscII);
    }

    private static NumberParseResult getNumber(char[][] ascIINumbers, int column) {
        StringBuilder numberString = new StringBuilder();
        int digit = getDigit(ascIINumbers, column);

        while (digit != -1) {
            numberString.append(digit);

            if (column + 6 >= ascIINumbers[0].length) {
                break;
            }
            column += 6;
            digit = getDigit(ascIINumbers, column);
        }
        int number = Integer.parseInt(numberString.toString());
        return new NumberParseResult(number, column + 6);
    }

    private static int getDigit(char[][] ascIINumbers, int column) {
        if (isNumber(ascIINumbers, column, DIGIT_0)) {
            return 0;
        }
        if (isNumber(ascIINumbers, column, DIGIT_1)) {
            return 1;
        }
        if (isNumber(ascIINumbers, column, DIGIT_2)) {
            return 2;
        }
        if (isNumber(ascIINumbers, column, DIGIT_3)) {
            return 3;
        }
        if (isNumber(ascIINumbers, column, DIGIT_4)) {
            return 4;
        }
        if (isNumber(ascIINumbers, column, DIGIT_5)) {
            return 5;
        }
        if (isNumber(ascIINumbers, column, DIGIT_6)) {
            return 6;
        }
        if (isNumber(ascIINumbers, column, DIGIT_7)) {
            return 7;
        }
        if (isNumber(ascIINumbers, column, DIGIT_8)) {
            return 8;
        }
        if (isNumber(ascIINumbers, column, DIGIT_9)) {
            return 9;
        }
        return -1;
    }

    private static boolean isNumber(char[][] ascIINumbers, int gridColumn, char[][] number) {
        for (int row = 0; row < number.length; row++) {
            for (int column = 0; column < number[0].length; column++) {
                if (ascIINumbers[row][gridColumn + column] != number[row][column]) {
                    return false;
                }
            }
        }
        return true;
    }

    private static char[][] getResultAscII(int number) {
        String numberString = String.valueOf(number);
        int columns = numberString.length() * 5 + (numberString.length() - 1);

        char[][] ascII = new char[7][columns];
        int column = 0;

        for (int i = 0; i < numberString.length(); i++) {
            char digit = numberString.charAt(i);
            addDigitToAscII(ascII, digit, column);
            column += 5;
            if (i != numberString.length() - 1) {
                addSeparator(ascII, column);
            }
            column++;
        }
        return ascII;
    }

    private static void addDigitToAscII(char[][] ascII, char digit, int column) {
        switch (digit) {
            case '0': addDigitToAscII(ascII, column, DIGIT_0); break;
            case '1': addDigitToAscII(ascII, column, DIGIT_1); break;
            case '2': addDigitToAscII(ascII, column, DIGIT_2); break;
            case '3': addDigitToAscII(ascII, column, DIGIT_3); break;
            case '4': addDigitToAscII(ascII, column, DIGIT_4); break;
            case '5': addDigitToAscII(ascII, column, DIGIT_5); break;
            case '6': addDigitToAscII(ascII, column, DIGIT_6); break;
            case '7': addDigitToAscII(ascII, column, DIGIT_7); break;
            case '8': addDigitToAscII(ascII, column, DIGIT_8); break;
            case '9': addDigitToAscII(ascII, column, DIGIT_9); break;
        }
    }

    private static void addDigitToAscII(char[][] ascII, int gridColumn, char[][] digitAscII) {
        for (int row = 0; row < digitAscII.length; row++) {
            for (int column = 0; column < digitAscII[0].length; column++) {
                ascII[row][gridColumn + column] = digitAscII[row][column];
            }
        }
    }

    private static void addSeparator(char[][] ascII, int gridColumn) {
        for (int row = 0; row < ascII.length; row++) {
            ascII[row][gridColumn] = '.';
        }
    }

    private static void printAscII(char[][] ascII) {
        for (int row = 0; row < ascII.length; row++) {
            for (int column = 0; column < ascII[0].length; column++) {
                System.out.print(ascII[row][column]);
            }
            System.out.println();
        }
    }
}
