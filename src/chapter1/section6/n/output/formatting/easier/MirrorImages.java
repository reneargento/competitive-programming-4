package chapter1.section6.n.output.formatting.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 15/12/20.
 */
public class MirrorImages {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 1; t <= tests; t++) {
            int rows = scanner.nextInt();
            int columns = scanner.nextInt();
            scanner.nextLine();

            char[][] image = new char[rows][columns];
            for (int row = 0; row < rows; row++) {
                image[row] = scanner.nextLine().toCharArray();
            }

            char[][] mirrorImage = mirrorImage(image);
            System.out.printf("Test %d\n", t);
            printImage(mirrorImage);
        }
    }

    private static char[][] mirrorImage(char[][] image) {
        int rows = image.length;
        int columns = image[0].length;

        char[][] topDown = new char[rows][columns];
        char[][] rightLeft = new char[rows][columns];

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                topDown[rows - 1 - row][column] = image[row][column];
            }
        }

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                rightLeft[row][columns - 1 - column] = topDown[row][column];
            }
        }
        return rightLeft;
    }

    private static void printImage(char[][] image) {
        for (int row = 0; row < image.length; row++) {
            for (int column = 0; column < image[0].length; column++) {
                System.out.print(image[row][column]);
            }
            System.out.println();
        }
    }
}
