package chapter1.section6.d.games.other.harder;

import java.util.Scanner;

/**
 * Created by Rene Argento on 25/10/20.
 */
public class CrosswordAnswers {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int rows = scanner.nextInt();
        int puzzleNumber = 1;

        while (rows != 0) {
            int columns = scanner.nextInt();
            char[][] puzzle = new char[rows][columns];

            for (int i = 0; i < rows; i++) {
                puzzle[i] = scanner.next().toCharArray();
            }

            int[][] ids = new int[rows][columns];
            int wordId = 1;
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < columns; c++) {
                    if (puzzle[r][c] != '*' &&
                            (r == 0 || c == 0 || puzzle[r - 1][c] == '*' || puzzle[r][c - 1] == '*')) {
                        ids[r][c] = wordId;
                        wordId++;
                    }
                }
            }

            if (puzzleNumber > 1) {
                System.out.println();
            }

            System.out.printf("puzzle #%d:\n", puzzleNumber);
            System.out.println("Across");
            printAcross(puzzle, ids);
            System.out.println("Down");
            printDown(puzzle, ids);

            puzzleNumber++;
            rows = scanner.nextInt();
        }
    }

    private static void printAcross(char[][] puzzle, int[][] ids) {
        for (int r = 0; r < puzzle.length; r++) {
            for (int c = 0; c < puzzle[0].length; c++) {
                if (puzzle[r][c] != '*' &&
                        (c == 0 || puzzle[r][c - 1] == '*')) {
                    System.out.printf("%3d.", ids[r][c]);

                    StringBuilder word = new StringBuilder();
                    for (int columnToPrint = c; columnToPrint < puzzle[0].length && puzzle[r][columnToPrint] != '*';
                         columnToPrint++) {
                        word.append(puzzle[r][columnToPrint]);
                    }
                    System.out.println(word);
                }
            }
        }
    }

    private static void printDown(char[][] puzzle, int[][] ids) {
        for (int r = 0; r < puzzle.length; r++) {
            for (int c = 0; c < puzzle[0].length; c++) {
                if (puzzle[r][c] != '*' &&
                        (r == 0 || puzzle[r - 1][c] == '*')) {
                    System.out.printf("%3d.", ids[r][c]);

                    StringBuilder word = new StringBuilder();
                    for (int rowToPrint = r; rowToPrint < puzzle.length && puzzle[rowToPrint][c] != '*'; rowToPrint++) {
                        word.append(puzzle[rowToPrint][c]);
                    }
                    System.out.println(word);
                }
            }
        }
    }

}
