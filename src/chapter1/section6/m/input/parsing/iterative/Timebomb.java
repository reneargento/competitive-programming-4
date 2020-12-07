package chapter1.section6.m.input.parsing.iterative;

import java.util.Scanner;

/**
 * Created by Rene Argento on 06/12/20.
 */
import java.util.Scanner;

public class Timebomb {

    private static char[][] zero = {
            {'*', '*', '*'},
            {'*', ' ', '*'},
            {'*', ' ', '*'},
            {'*', ' ', '*'},
            {'*', '*', '*'}
    };

    private static char[][] one = {
            {' ', ' ', '*'},
            {' ', ' ', '*'},
            {' ', ' ', '*'},
            {' ', ' ', '*'},
            {' ', ' ', '*'}
    };

    private static char[][] two = {
            {'*', '*', '*'},
            {' ', ' ', '*'},
            {'*', '*', '*'},
            {'*', ' ', ' '},
            {'*', '*', '*'}
    };

    private static char[][] three = {
            {'*', '*', '*'},
            {' ', ' ', '*'},
            {'*', '*', '*'},
            {' ', ' ', '*'},
            {'*', '*', '*'}
    };

    private static char[][] four = {
            {'*', ' ', '*'},
            {'*', ' ', '*'},
            {'*', '*', '*'},
            {' ', ' ', '*'},
            {' ', ' ', '*'}
    };

    private static char[][] five = {
            {'*', '*', '*'},
            {'*', ' ', ' '},
            {'*', '*', '*'},
            {' ', ' ', '*'},
            {'*', '*', '*'}
    };

    private static char[][] six = {
            {'*', '*', '*'},
            {'*', ' ', ' '},
            {'*', '*', '*'},
            {'*', ' ', '*'},
            {'*', '*', '*'}
    };

    private static char[][] seven = {
            {'*', '*', '*'},
            {' ', ' ', '*'},
            {' ', ' ', '*'},
            {' ', ' ', '*'},
            {' ', ' ', '*'}
    };

    private static char[][] eight = {
            {'*', '*', '*'},
            {'*', ' ', '*'},
            {'*', '*', '*'},
            {'*', ' ', '*'},
            {'*', '*', '*'}
    };

    private static char[][] nine = {
            {'*', '*', '*'},
            {'*', ' ', '*'},
            {'*', '*', '*'},
            {' ', ' ', '*'},
            {'*', '*', '*'}
    };

    private static final String BEER = "BEER!!";
    private static final String BOOM = "BOOM!!";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        char[][] numbers = new char[5][];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = scanner.nextLine().toCharArray();
        }

        StringBuilder number = new StringBuilder();
        boolean validNumber = true;

        for (int i = 0; i < numbers[0].length; i += 4) {
            int currentNumber = getNumber(numbers, i);
            if (currentNumber == - 1) {
                validNumber = false;
                break;
            }
            number.append(currentNumber);
        }

        if (validNumber) {
            int numberValue = Integer.parseInt(number.toString());
            if (numberValue % 6 == 0) {
                System.out.println(BEER);
            } else {
                System.out.println(BOOM);
            }
        } else {
            System.out.println(BOOM);
        }
    }

    private static int getNumber(char[][] numbers, int index) {
        if (isNumber(numbers, index, zero)) {
            return 0;
        }
        if (isNumber(numbers, index, one)) {
            return 1;
        }
        if (isNumber(numbers, index, two)) {
            return 2;
        }
        if (isNumber(numbers, index, three)) {
            return 3;
        }
        if (isNumber(numbers, index, four)) {
            return 4;
        }
        if (isNumber(numbers, index, five)) {
            return 5;
        }
        if (isNumber(numbers, index, six)) {
            return 6;
        }
        if (isNumber(numbers, index, seven)) {
            return 7;
        }
        if (isNumber(numbers, index, eight)) {
            return 8;
        }
        if (isNumber(numbers, index, nine)) {
            return 9;
        }
        return -1;
    }

    private static boolean isNumber(char[][] numbers, int index, char[][] number) {
        for (int row = 0; row < 5; row++) {
            for (int column = index; column < index + 3; column++) {
                if (numbers[row][column] != number[row][column - index]) {
                    return false;
                }
            }
        }
        return true;
    }
}
