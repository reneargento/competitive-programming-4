package chapter2.section2.section1;

/**
 * Created by Rene Argento on 09/01/21.
 */
public class Exercise4_2_Transpose {

    public static void main(String[] args) {
        int[][] array = {
                {1,   2,  3,  4},
                {5,   6,  7,  8},
                {9,  10, 11, 12},
                {13, 14, 15, 16}
        };

        transpose(array);
        System.out.println("Transposed array:");
        printArray(array);

        System.out.println("\nExpected:");
        System.out.println("  1  5  9 13\n" +
                           "  2  6 10 14\n" +
                           "  3  7 11 15\n" +
                           "  4  8 12 16");
    }

    private static void transpose(int[][] array) {
        int[][] transposedArray = new int[array.length][array[0].length];

        for (int row = 0; row < array.length; row++) {
            for (int column = 0; column < array[0].length; column++) {
                transposedArray[column][row] = array[row][column];
            }
        }

        for (int row = 0; row < array.length; row++) {
            for (int column = 0; column < array[0].length; column++) {
                array[row][column] = transposedArray[row][column];
            }
        }
    }

    private static void printArray(int[][] array) {
        for (int row = 0; row < array.length; row++) {
            for (int column = 0; column < array[0].length; column++) {
                System.out.printf("%3d", array[row][column]);
            }
            System.out.println();
        }
    }
}
