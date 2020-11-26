package chapter1.section6.g.interesting.real.life.problems.harder;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Rene Argento on 07/11/20.
 */
public class LCDisplay {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();
        String number = scanner.next();

        while (size != 0 || !number.equals("0")) {
            int rows = 2 * size + 3;
            int columns = (size + 3) * number.length() - 1;
            char[][] display = new char[rows][columns];

            for (int i = 0; i < rows; i++) {
                Arrays.fill(display[i], ' ');
            }

            for (int i = 0; i < number.length(); i++) {
                int startColumn = (size + 3) * i;

                switch (number.charAt(i)) {
                    case '1': print1(display, size, startColumn); break;
                    case '2': print2(display, size, startColumn); break;
                    case '3': print3(display, size, startColumn); break;
                    case '4': print4(display, size, startColumn); break;
                    case '5': print5(display, size, startColumn); break;
                    case '6': print6(display, size, startColumn); break;
                    case '7': print7(display, size, startColumn); break;
                    case '8': print8(display, size, startColumn); break;
                    case '9': print9(display, size, startColumn); break;
                    case '0': print0(display, size, startColumn); break;
                }
            }
            printDisplay(display);

            size = scanner.nextInt();
            number = scanner.next();
        }
    }

    private static void printDisplay(char[][] display) {
        for (int r = 0; r < display.length; r++) {
            for (int c = 0; c < display[0].length; c++) {
                System.out.print(display[r][c]);
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void print(char[][] display, int startRow, int endRow, int startColumn, int endColumn,
                              boolean isVertical) {
        for (int row = startRow; row <= endRow; row++) {
            for (int column = startColumn; column <= endColumn; column++) {
                if (isVertical) {
                    display[row][column] = '|';
                } else {
                    display[row][column] = '-';
                }
            }
        }
    }

    private static void printTopAndBottom(char[][] display, int size, int column) {
        print(display, 0, 0, column + 1, column + size, false);
        print(display, 2 * size + 2, 2 * size + 2, column + 1, column + size, false);
    }

    private static void printLeft(char[][] display, int size, int column) {
        print(display, 1, 1 + size - 1, column, column, true);
        print(display, 1 + size + 1, 1 + size + size, column, column, true);
    }

    private static void printRight(char[][] display, int size, int column) {
        print(display, 1, 1 + size - 1, column + size + 1, column + size + 1, true);
        print(display, 1 + size + 1, 1 + size + size, column + size + 1, column + size + 1, true);
    }

    private static void printCenter(char[][] display, int size, int column) {
        print(display, size + 1, size + 1, column + 1, column + size, false);
    }

    private static void print1(char[][] display, int size, int column) {
        printRight(display, size, column);
    }

    private static void printBase2And3(char[][] display, int size, int column) {
        printTopAndBottom(display, size, column);
        printCenter(display, size, column);
        print(display, 1, 1 + size - 1, column + size + 1, column + size + 1, true);
    }

    private static void print2(char[][] display, int size, int column) {
        printBase2And3(display, size, column);
        print(display, 1 + size + 1, 1 + size + size, column, column, true);
    }

    private static void print3(char[][] display, int size, int column) {
        printBase2And3(display, size, column);
        print(display, 1 + size + 1, 1 + size + size, column + size + 1, column + size + 1, true);
    }

    private static void printBase4And5And6(char[][] display, int size, int column) {
        print(display, 1, 1 + size - 1, column, column, true);
        printCenter(display, size, column);
        print(display, 1 + size + 1, 1 + size + size, column + size + 1, column + size + 1, true);
    }

    private static void print4(char[][] display, int size, int column) {
        printBase4And5And6(display, size, column);
        print(display, 1, 1 + size - 1, column + size + 1, column + size + 1, true);
    }

    private static void print5(char[][] display, int size, int column) {
        printBase4And5And6(display, size, column);
        printTopAndBottom(display, size, column);
    }

    private static void print6(char[][] display, int size, int column) {
        printBase4And5And6(display, size, column);
        printTopAndBottom(display, size, column);
        printLeft(display, size, column);
    }

    private static void print7(char[][] display, int size, int column) {
        print(display, 0, 0, column + 1, column + size, false);
        printRight(display, size, column);
    }

    private static void print8(char[][] display, int size, int column) {
        printTopAndBottom(display, size, column);
        printCenter(display, size, column);
        printLeft(display, size, column);
        printRight(display, size, column);
    }

    private static void print9(char[][] display, int size, int column) {
        printTopAndBottom(display, size, column);
        printCenter(display, size, column);
        printRight(display, size, column);
        print(display, 1, 1 + size - 1, column, column, true);
    }

    private static void print0(char[][] display, int size, int column) {
        printTopAndBottom(display, size, column);
        printLeft(display, size, column);
        printRight(display, size, column);
    }
}
