package chapter2.section2.d.twod.array.manipulation.harder;

import java.util.Scanner;

/**
 * Created by Rene Argento on 06/02/21.
 */
public class Kattis2048 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[][] puzzle = new int[4][4];

        for (int row = 0; row < puzzle.length; row++) {
            for (int column = 0; column < puzzle[0].length; column++) {
                puzzle[row][column] = scanner.nextInt();
            }
        }
        int move = scanner.nextInt();

        makeMove(puzzle, move);
        printPuzzle(puzzle);
    }

    private static void makeMove(int[][] puzzle, int move) {
        int length = puzzle.length;

        switch (move) {
            case 0: moveHorizontal(puzzle, 0, 1, length); break;
            case 1: moveVertical(puzzle, 0, 1, length); break;
            case 2: moveHorizontal(puzzle, length - 1, -1, -1); break;
            case 3: moveVertical(puzzle, length - 1, -1, -1); break;
        }
    }

    private static void moveHorizontal(int[][] puzzle, int startIndex, int increment, int endIndex) {
        for (int row = 0; row < puzzle.length; row++) {
            for (int column = startIndex; column != endIndex; column += increment) {
                if (puzzle[row][column] == 0) {
                    for (int nextColumn = column + increment; nextColumn != endIndex; nextColumn += increment) {
                        if (puzzle[row][nextColumn] != 0) {
                            puzzle[row][column] = puzzle[row][nextColumn];
                            puzzle[row][nextColumn] = 0;
                            break;
                        }
                    }
                }
                if (puzzle[row][column] != 0) {
                    for (int nextColumn = column + increment; nextColumn != endIndex; nextColumn += increment) {
                        if (puzzle[row][nextColumn] != 0) {
                            if (puzzle[row][nextColumn] == puzzle[row][column]) {
                                puzzle[row][column] += puzzle[row][column];
                                puzzle[row][nextColumn] = 0;
                            }
                            break;
                        }
                    }
                }
            }
        }
    }

    private static void moveVertical(int[][] puzzle, int startIndex, int increment, int endIndex) {
        for (int column = 0; column < puzzle[0].length; column++) {
            for (int row = startIndex; row != endIndex; row += increment) {
                if (puzzle[row][column] == 0) {
                    for (int nextRow = row + increment; nextRow != endIndex; nextRow += increment) {
                        if (puzzle[nextRow][column] != 0) {
                            puzzle[row][column] = puzzle[nextRow][column];
                            puzzle[nextRow][column] = 0;
                            break;
                        }
                    }
                }
                if (puzzle[row][column] != 0) {
                    for (int nextRow = row + increment; nextRow != endIndex; nextRow += increment) {
                        if (puzzle[nextRow][column] != 0) {
                            if (puzzle[nextRow][column] == puzzle[row][column]) {
                                puzzle[row][column] += puzzle[row][column];
                                puzzle[nextRow][column] = 0;
                            }
                            break;
                        }
                    }
                }
            }
        }
    }

    private static void printPuzzle(int[][] puzzle) {
        for (int row = 0; row < puzzle.length; row++) {
            for (int column = 0; column < puzzle[0].length; column++) {
                System.out.print(puzzle[row][column]);

                if (column != puzzle[0].length - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}
