package chapter2.section2.c.twod.array.manipulation.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 03/02/21.
 */
public class ImageProcessing {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int imageRows = scanner.nextInt();
        int imageColumns = scanner.nextInt();
        int kernelRows = scanner.nextInt();
        int kernelColumns = scanner.nextInt();
        int[][] image = new int[imageRows][imageColumns];
        int[][] kernel = new int[kernelRows][kernelColumns];

        for (int row = 0; row < image.length; row++) {
            for (int column = 0; column < image[0].length; column++) {
                image[row][column] = scanner.nextInt();
            }
        }

        for (int row = 0; row < kernel.length; row++) {
            for (int column = 0; column < kernel[0].length; column++) {
                kernel[row][column] = scanner.nextInt();
            }
        }

        int[][] resultingImage = applyConvolution(image, kernel);
        for (int row = 0; row < resultingImage.length; row++) {
            for (int column = 0; column < resultingImage[0].length; column++) {
                System.out.print(resultingImage[row][column]);
                if (column != resultingImage[0].length - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    private static int[][] applyConvolution(int[][] image, int[][] kernel) {
        int endRow = image.length - kernel.length;
        int endColumn = image[0].length - kernel[0].length;
        int[][] convolutedImage = new int[endRow + 1][endColumn + 1];
        int[][] flippedKernel = flipRowsAndColumns(kernel);

        for (int row = 0; row <= endRow; row++) {
            for (int column = 0; column <= endColumn; column++) {
                int value = 0;

                for (int kernelRow = 0; kernelRow < flippedKernel.length; kernelRow++) {
                    for (int kernelColumn = 0; kernelColumn < flippedKernel[0].length; kernelColumn++) {
                        value += image[row + kernelRow][column + kernelColumn] * flippedKernel[kernelRow][kernelColumn];
                    }
                }
                convolutedImage[row][column] = value;
            }
        }
        return convolutedImage;
    }

    private static int[][] flipRowsAndColumns(int[][] kernel) {
        int[][] flippedRows = new int[kernel.length][kernel[0].length];

        for (int row = 0; row < kernel.length; row++) {
            for (int column = 0; column < kernel[0].length; column++) {
                flippedRows[row][column] = kernel[kernel.length - 1 - row][column];
            }
        }

        int[][] flippedRowsAndColumns = new int[kernel.length][kernel[0].length];
        for (int row = 0; row < kernel.length; row++) {
            for (int column = 0; column < kernel[0].length; column++) {
                flippedRowsAndColumns[row][column] = flippedRows[row][kernel[0].length - 1 - column];
            }
        }
        return flippedRowsAndColumns;
    }
}
