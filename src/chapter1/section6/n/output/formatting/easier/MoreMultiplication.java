package chapter1.section6.n.output.formatting.easier;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Rene Argento on 14/12/20.
 */
public class MoreMultiplication {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);

        int number1 = scanner.nextInt();
        int number2 = scanner.nextInt();

        while (number1 != 0 || number2 != 0) {
            printMultiplicationGrid(number1, number2);
            number1 = scanner.nextInt();
            number2 = scanner.nextInt();
        }
        outputWriter.flush();
        outputWriter.close();
    }

    private static void printMultiplicationGrid(int number1, int number2) {
        String number1String = String.valueOf(number1);
        String number2String = String.valueOf(number2);

        int columns = number1String.length();
        int rows = number2String.length();

        int frameLength = 2 + 5 + (4 * (columns - 1));
        int frameHeight = 2 + 5 + (4 * (rows - 1));

        char[][] grid = new char[frameHeight + 2][frameLength + 2];
        initGrid(grid);
        addNumber1ToGrid(grid, number1String);
        addNumber2ToGrid(grid, number2String);

        addHorizontalBorders(grid);
        addVerticalBorders(grid);
        addInternalBorders(grid, number1String, number2String);

        addInternalNumbers(grid, number1String, number2String);
        addFinalResult(grid, number1String, number2String);

        printGrid(grid);
    }

    private static void addNumber1ToGrid(char[][] grid, String number1String) {
        int column = 4;
        for (char digit : number1String.toCharArray()) {
            grid[1][column] = digit;
            column += 4;
        }
    }

    private static void addNumber2ToGrid(char[][] grid, String number2String) {
        int row = 4;
        for (char digit : number2String.toCharArray()) {
            grid[row][grid[0].length - 2] = digit;
            row += 4;
        }
    }

    private static void addHorizontalBorders(char[][] grid) {
        int lastRow = grid.length - 1;
        int lastColumn = grid[0].length - 1;
        grid[0][0] = '+';
        grid[0][lastColumn] = '+';
        grid[lastRow][0] = '+';
        grid[lastRow][lastColumn] = '+';

        for (int column = 1; column < grid[0].length - 1; column++) {
            grid[0][column] = '-';
            grid[lastRow][column] = '-';
        }
    }

    private static void addVerticalBorders(char[][] grid) {
        for (int row = 1; row < grid.length - 1; row++) {
            grid[row][0] = '|';
            grid[row][grid[0].length - 1] = '|';
        }
    }

    private static void addInternalBorders(char[][] grid, String number1String, String number2String) {
        int row = 2;

        for (int i = 0; i <= number2String.length(); i++) {
            int column = 2;
            grid[row][column++] = '+';

            for (int j = 0; j < number1String.length(); j++) {
                for (int c = 0; c < 3; c++) {
                    grid[row][column++] = '-';
                }
                grid[row][column++] = '+';
            }
            row += 4;
        }

        int column = 2;

        for (int i = 0; i <= number1String.length(); i++) {
            row = 3;

            for (int j = 0; j < number2String.length(); j++) {
                for (int r = 0; r < 3; r++) {
                    grid[row][column] = '|';

                    if (i != number1String.length()) {
                        grid[row][column + 3 - r] = '/';
                    }
                    row++;
                }
                row++;
            }
            column += 4;
        }
    }

    private static void addInternalNumbers(char[][] grid, String number1String, String number2String) {
        for (int i = 0; i < number2String.length(); i++) {
            char digitNumber2 = number2String.charAt(i);
            int digitNumber2Value = Character.getNumericValue(digitNumber2);
            int topRow = 3 + (4 * i);
            int bottomRow = topRow + 2;
            int topColumn = grid[0].length - 6;
            int bottomColumn = grid[0].length - 4;

            for (int j = number1String.length() - 1; j >= 0; j--) {
                char digitNumber1 = number1String.charAt(j);
                int digitNumber1Value = Character.getNumericValue(digitNumber1);

                String result = String.valueOf(digitNumber1Value * digitNumber2Value);
                char bottomDigit = result.charAt(result.length() - 1);
                char topDigit = result.length() > 1 ? result.charAt(0) : '0';

                grid[topRow][topColumn] = topDigit;
                grid[bottomRow][bottomColumn] = bottomDigit;

                topColumn -= 4;
                bottomColumn -= 4;
            }
        }
    }

    private static void addFinalResult(char[][] grid, String number1String, String number2String) {
        int row = grid.length - 4;
        int column = grid[0].length - 4;
        int carryOver = 0;
        int iterations = number1String.length() + number2String.length();

        for (int i = 0; i < iterations; i++) {

            boolean isTopNumber = i >= number2String.length();
            if (isTopNumber) {
                row = 3;
                if (i == number2String.length()) {
                    column = grid[0].length - 6;
                } else {
                    column -= 4;
                }
            } else {
                if (i == 0) {
                    row = grid.length - 4;
                } else {
                    row -= 4;
                }
                column = grid[0].length - 4;
            }

            int sum = 0;

            for (int r = row, c = column; r < grid.length; r += 2, c -= 2) {
                if (r != grid.length - 2 && c != 1) {
                    int digitValue = Character.getNumericValue(grid[r][c]);
                    sum += digitValue;
                } else {
                    if (carryOver != 0) {
                        sum += carryOver;
                    }
                    carryOver = 0;

                    if (sum == 0 && i == iterations - 1) {
                        break;
                    }

                    String result = String.valueOf(sum);
                    grid[r][c] = result.charAt(result.length() - 1);

                    if (i > 0) {
                        if (r == grid.length - 2) {
                            grid[r][c + 2] = '/';
                        } else {
                            grid[r + 2][c] = '/';
                        }
                    }

                    if (sum > 9) {
                        carryOver = sum / 10;
                    }
                    break;
                }
            }
        }
    }

    private static void initGrid(char[][] grid) {
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[0].length; column++) {
                grid[row][column] = ' ';
            }
        }
    }

    private static void printGrid(char[][] grid) {
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[0].length; column++) {
                System.out.print(grid[row][column]);
            }
            System.out.println();
        }
    }

    private static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++) {
                if (i != 0)
                    writer.print(' ');
                writer.print(objects[i]);
            }
        }

        public void printLine(Object... objects) {
            print(objects);
            writer.println();
        }

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
