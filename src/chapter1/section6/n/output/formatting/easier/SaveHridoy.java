package chapter1.section6.n.output.formatting.easier;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Rene Argento on 13/12/20.
 */
public class SaveHridoy {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int fontSize = scanner.nextInt();

        while (fontSize != 0) {
            if (fontSize < 0) {
                fontSize = Math.abs(fontSize);
                printVertical(fontSize, 5 * fontSize * 10 + 11 * fontSize, 5 * fontSize, outputWriter);
            } else {
                printHorizontal(fontSize, 5 * fontSize, 5 * fontSize * 10 + 11 * fontSize, outputWriter);
            }
            outputWriter.printLine();
            outputWriter.printLine();
            fontSize = scanner.nextInt();
        }
        outputWriter.flush();
        outputWriter.close();
    }

    private static void printVertical(int fontSize, int rows, int columns, OutputWriter outputWriter) {
        char[][] banner = new char[rows][columns];
        int spaceBetweenLetters = fontSize;
        int spaceBetweenWords = fontSize * 3;

        initBanner(banner);
        addSorE(banner, fontSize, 0, 0, true);
        addA(banner, fontSize, fontSize * 5 + spaceBetweenLetters, 0);
        addV(banner, fontSize, fontSize * 5 * 2 + spaceBetweenLetters * 2, 0);
        addSorE(banner, fontSize, fontSize * 5 * 3 + spaceBetweenLetters * 3, 0, false);
        addH(banner, fontSize, fontSize * 5 * 4 + spaceBetweenLetters * 3 + spaceBetweenWords, 0);
        addR(banner, fontSize, fontSize * 5 * 5 + spaceBetweenLetters * 4 + spaceBetweenWords, 0);
        addI(banner, fontSize, fontSize * 5 * 6 + spaceBetweenLetters * 5 + spaceBetweenWords, 0);
        addD(banner, fontSize, fontSize * 5 * 7 + spaceBetweenLetters * 6 + spaceBetweenWords, 0);
        addO(banner, fontSize, fontSize * 5 * 8 + spaceBetweenLetters * 7 + spaceBetweenWords, 0);
        addY(banner, fontSize, fontSize * 5 * 9 + spaceBetweenLetters * 8 + spaceBetweenWords, 0);

        printBanner(banner, outputWriter);
    }

    private static void printHorizontal(int fontSize, int rows, int columns, OutputWriter outputWriter) {
        char[][] banner = new char[rows][columns];
        int spaceBetweenLetters = fontSize;
        int spaceBetweenWords = fontSize * 3;

        initBanner(banner);
        addSorE(banner, fontSize, 0, 0, true);
        addA(banner, fontSize, 0, fontSize * 5 + spaceBetweenLetters);
        addV(banner, fontSize, 0, fontSize * 5 * 2 + spaceBetweenLetters * 2);
        addSorE(banner, fontSize, 0, fontSize * 5 * 3 + spaceBetweenLetters * 3, false);
        addH(banner, fontSize, 0, fontSize * 5 * 4 + spaceBetweenLetters * 3 + spaceBetweenWords);
        addR(banner, fontSize, 0, fontSize * 5 * 5 + spaceBetweenLetters * 4 + spaceBetweenWords);
        addI(banner, fontSize, 0, fontSize * 5 * 6 + spaceBetweenLetters * 5 + spaceBetweenWords);
        addD(banner, fontSize, 0, fontSize * 5 * 7 + spaceBetweenLetters * 6 + spaceBetweenWords);
        addO(banner, fontSize, 0, fontSize * 5 * 8 + spaceBetweenLetters * 7 + spaceBetweenWords);
        addY(banner, fontSize, 0, fontSize * 5 * 9 + spaceBetweenLetters * 8 + spaceBetweenWords);

        printBanner(banner, outputWriter);
    }

    private static void addSorE(char[][] banner, int fontSize, int row, int column, boolean isS) {
        addPointsLeftToRight(banner, fontSize, 5, row, column);
        addPointsLeftToRight(banner, fontSize, 1, row + fontSize, column);
        if (isS) {
            addPointsLeftToRight(banner, fontSize, 5, row + fontSize * 2, column);
            addPointsLeftToRight(banner, fontSize, 1, row + fontSize * 3, column + fontSize * 4);
        } else {
            addPointsLeftToRight(banner, fontSize, 3, row + fontSize * 2, column);
            addPointsLeftToRight(banner, fontSize, 1, row + fontSize * 3, column);
        }
        addPointsLeftToRight(banner, fontSize, 5, row + fontSize * 4, column);
    }

    private static void addA(char[][] banner, int fontSize, int row, int column) {
        addPointsLeftToRight(banner, fontSize, 3, row, column + fontSize);
        addPointsTopToDown(banner, fontSize, 4, row + fontSize, column);
        addPointsTopToDown(banner, fontSize, 4, row + fontSize, column + fontSize * 4);
        addPointsLeftToRight(banner, fontSize, 5, row + fontSize * 2, column);
    }

    private static void addV(char[][] banner, int fontSize, int row, int column) {
        addPointsTopToDown(banner, fontSize, 3, row, column);
        addPointsTopToDown(banner, fontSize, 3, row, column + fontSize * 4);
        addPointsLeftToRight(banner, fontSize, 1, row + fontSize * 3, column + fontSize);
        addPointsLeftToRight(banner, fontSize, 1, row + fontSize * 3, column + fontSize * 3);
        addPointsLeftToRight(banner, fontSize, 1, row + fontSize * 4, column + fontSize * 2);
    }

    private static void addH(char[][] banner, int fontSize, int row, int column) {
        addPointsTopToDown(banner, fontSize, 5, row, column);
        addPointsLeftToRight(banner, fontSize, 5, row + fontSize * 2, column);
        addPointsTopToDown(banner, fontSize, 5, row, column + fontSize * 4);
    }

    private static void addR(char[][] banner, int fontSize, int row, int column) {
        addPointsLeftToRight(banner, fontSize, 5, row, column);
        addPointsTopToDown(banner, fontSize, 5, row, column);
        addPointsLeftToRight(banner, fontSize, 1, row + fontSize, column + fontSize * 4);
        addPointsLeftToRight(banner, fontSize, 5, row + fontSize * 2, column);
        addPointsLeftToRight(banner, fontSize, 1, row + fontSize * 3, column + fontSize * 2);
        addPointsLeftToRight(banner, fontSize, 2, row + fontSize * 4, column + fontSize * 3);
    }

    private static void addI(char[][] banner, int fontSize, int row, int column) {
        addPointsLeftToRight(banner, fontSize, 5, row, column);
        addPointsTopToDown(banner, fontSize, 5, row, column + fontSize * 2);
        addPointsLeftToRight(banner, fontSize, 5, row + fontSize * 4, column);
    }

    private static void addD(char[][] banner, int fontSize, int row, int column) {
        addPointsTopToDown(banner, fontSize, 5, row, column);
        addPointsLeftToRight(banner, fontSize, 3, row, column);
        addPointsLeftToRight(banner, fontSize, 1, row + fontSize, column + fontSize * 3);
        addPointsLeftToRight(banner, fontSize, 1, row + fontSize * 2, column + fontSize * 4);
        addPointsLeftToRight(banner, fontSize, 1, row + fontSize * 3, column + fontSize * 3);
        addPointsLeftToRight(banner, fontSize, 3, row + fontSize * 4, column);
    }

    private static void addO(char[][] banner, int fontSize, int row, int column) {
        addPointsLeftToRight(banner, fontSize, 5, row, column);
        addPointsTopToDown(banner, fontSize, 5, row, column);
        addPointsTopToDown(banner, fontSize, 5, row, column + fontSize * 4);
        addPointsLeftToRight(banner, fontSize, 5, row + fontSize * 4, column);
    }

    private static void addY(char[][] banner, int fontSize, int row, int column) {
        addPointsLeftToRight(banner, fontSize, 1, row, column);
        addPointsLeftToRight(banner, fontSize, 1, row, column + fontSize * 4);
        addPointsLeftToRight(banner, fontSize, 1, row + fontSize, column + fontSize);
        addPointsLeftToRight(banner, fontSize, 1, row + fontSize, column + fontSize * 3);
        addPointsTopToDown(banner, fontSize, 3, row + fontSize * 2, column + fontSize * 2);
    }

    private static void addPointsLeftToRight(char[][] banner, int fontSize, int quantity, int startRow, int startColumn) {
        int endRow = startRow + fontSize;
        int endColumn = startColumn + fontSize * quantity;

        for (int row = startRow; row < endRow; row++) {
            for (int column = startColumn; column < endColumn; column++) {
                banner[row][column] = '*';
            }
        }
    }

    private static void addPointsTopToDown(char[][] banner, int fontSize, int quantity, int startRow, int startColumn) {
        for (int column = startColumn; column < startColumn + fontSize; column++) {
            for (int row = startRow; row < startRow + fontSize * quantity; row++) {
                banner[row][column] = '*';
            }
        }
    }

    private static void initBanner(char[][] banner) {
        for (int row = 0; row < banner.length; row++) {
            for (int column = 0; column < banner[0].length; column++) {
                banner[row][column] = '.';
            }
        }
    }

    private static void printBanner(char[][] banner, OutputWriter outputWriter) {
        for (int row = 0; row < banner.length; row++) {
            for (int column = 0; column < banner[0].length; column++) {
                outputWriter.print(banner[row][column]);
            }
            outputWriter.printLine();
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
