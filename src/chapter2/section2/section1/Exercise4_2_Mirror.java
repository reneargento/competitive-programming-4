package chapter2.section2.section1;

/**
 * Created by Rene Argento on 09/01/21.
 */
public class Exercise4_2_Mirror {

    public static void main(String[] args) {
        int[][] array = {
                {1,   2,  3,  4},
                {5,   6,  7,  8},
                {9,  10, 11, 12},
                {13, 14, 15, 16}
        };

        mirror(array, true, 2);
        mirror(array, false, 0);
        System.out.println("Mirrored array (third row and first column):");
        printArray(array);

        System.out.println("\nExpected:");
        System.out.println(" 13  2  3  4\n" +
                           " 12  6  7  8\n" +
                           "  5 11 10  9\n" +
                           "  1 14 15 16");
    }

    private static void mirror(int[][] array, boolean isXAxis, int index) {
        int[] copyElements = new int[array.length];

        if (isXAxis) {
            System.arraycopy(array[index], 0, copyElements, 0, array.length);

            for (int c = 0; c < array[0].length; c++) {
                array[index][c] = copyElements[copyElements.length - 1 - c];
            }
        } else {
            for (int r = 0; r < array.length; r++) {
                copyElements[r] = array[r][index];
            }

            for (int r = 0; r < array.length; r++) {
                array[r][index] = copyElements[copyElements.length - 1 - r];
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
