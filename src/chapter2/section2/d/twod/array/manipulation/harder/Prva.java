package chapter2.section2.d.twod.array.manipulation.harder;

import java.util.Scanner;

/**
 * Created by Rene Argento on 13/02/21.
 */
public class Prva {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int rows = scanner.nextInt();
        int columns = scanner.nextInt();
        char[][] crossword = new char[rows][columns];

        for (int row = 0; row < crossword.length; row++) {
            crossword[row] = scanner.next().toCharArray();
        }
        String lexicographicallySmallestWord = getLexicographicallySmallestWord(crossword);
        System.out.println(lexicographicallySmallestWord);
    }

    private static String getLexicographicallySmallestWord(char[][] crossword) {
        String lexicographicallySmallestWord = null;

        for (int row = 0; row < crossword.length; row++) {
            for (int column = 0; column < crossword[0].length; column++) {
                String word = null;

                if (shouldProcessHorizontally(crossword, row, column)) {
                    word = getWord(crossword, row, column, true);
                }
                if (shouldProcessVertically(crossword, row, column)) {
                    word = getWord(crossword, row, column, false);
                }

                if (word != null) {
                    if (word.length() >= 2 &&
                            (lexicographicallySmallestWord == null || word.compareTo(lexicographicallySmallestWord) < 0)) {
                        lexicographicallySmallestWord = word;
                    }
                }
            }
        }
        return lexicographicallySmallestWord;
    }

    private static String getWord(char[][] crossword, int row, int column, boolean horizontal) {
        StringBuilder word = new StringBuilder();

        while (row < crossword.length && column < crossword[0].length && crossword[row][column] != '#') {
            char currentLetter = crossword[row][column];
            word.append(currentLetter);

            if (horizontal) {
                column++;
            } else {
                row++;
            }
        }
        return word.toString();
    }

    private static boolean shouldProcessHorizontally(char[][] crossword, int row, int column) {
        return (column == 0 && crossword[row][column] != '#')
                || (column > 0 && crossword[row][column - 1] == '#');
    }

    private static boolean shouldProcessVertically(char[][] crossword, int row, int column) {
        return (row == 0 && crossword[row][column] != '#')
                || (row > 0 && crossword[row - 1][column] == '#');
    }
}
