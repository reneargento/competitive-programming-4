package chapter1.section6.n.output.formatting.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 15/12/20.
 */
public class Okviri {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String letters = scanner.next();
        int columns = 5 + (letters.length() - 1) * 4;
        char[][] frames = new char[5][columns];

        initFrames(frames);
        int column = 2;

        for (int i = 1; i <= letters.length(); i++) {
            char letter = letters.charAt(i - 1);
            char frame = (i % 3 == 0) ? '*' : '#';
            addFrame(frames, letter, column, frame);
            column += 4;
        }
        printFrames(frames);
    }

    private static void addFrame(char[][] frames, char letter, int column, char frame) {
        if (frames[2][column - 2] != '*') {
            frames[2][column - 2] = frame;
        }
        frames[2][column] = letter;
        frames[1][column - 1] = frame;
        frames[3][column - 1] = frame;
        frames[0][column] = frame;
        frames[4][column] = frame;
        frames[1][column + 1] = frame;
        frames[3][column + 1] = frame;
        frames[2][column + 2] = frame;
    }

    private static void initFrames(char[][] frames) {
        for (int row = 0; row < frames.length; row++) {
            for (int column = 0; column < frames[0].length; column++) {
                frames[row][column] = '.';
            }
        }
    }

    private static void printFrames(char[][] frames) {
        for (int row = 0; row < frames.length; row++) {
            for (int column = 0; column < frames[0].length; column++) {
                System.out.print(frames[row][column]);
            }
            System.out.println();
        }
    }
}
