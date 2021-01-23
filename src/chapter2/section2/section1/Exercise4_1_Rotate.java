package chapter2.section2.section1;

/**
 * Created by Rene Argento on 09/01/21.
 */
public class Exercise4_1_Rotate {

    public static void main(String[] args) {
        int[][] array = {
                {1,   2,  3,  4},
                {5,   6,  7,  8},
                {9,  10, 11, 12},
                {13, 14, 15, 16}
        };

        rotate(array);
        System.out.println("Rotated array:");
        printArray(array);

        System.out.println("\nExpected:");
        System.out.println("  4  8 12 16\n" +
                           "  3  7 11 15\n" +
                           "  2  6 10 14\n" +
                           "  1  5  9 13");
    }

    private static void rotate(int[][] array) {
        int startRow = 0;
        int startColumn = 0;
        int endRow = array.length - 1;
        int endColumn = array.length - 1;

        while (startRow <= endRow && startColumn <= endColumn) {
            int currentColumn = endColumn;
            int delta = endColumn - startColumn;

            while (currentColumn != startColumn) {
                // First element
                int elementRow = startRow;
                int elementColumn = currentColumn;

                int nextElementColumn = startColumn;
                int nextElementRow = startRow + (delta - (currentColumn - startColumn));

                int secondElement = array[nextElementRow][nextElementColumn];
                array[nextElementRow][nextElementColumn] = array[elementRow][elementColumn];

                // Second element
                elementRow = nextElementRow;

                nextElementColumn = startColumn + (delta - (endRow - elementRow));
                nextElementRow = endRow;

                int thirdElement = array[nextElementRow][nextElementColumn];
                array[nextElementRow][nextElementColumn] = secondElement;

                // Third element
                elementColumn = nextElementColumn;

                nextElementColumn = endColumn;
                nextElementRow = endRow - (delta - (endColumn - elementColumn));

                int fourthElement = array[nextElementRow][nextElementColumn];
                array[nextElementRow][nextElementColumn] = thirdElement;

                // Fourth element
                array[startRow][currentColumn] = fourthElement;

                currentColumn--;
            }
            startRow++;
            startColumn++;
            endRow--;
            endColumn--;
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
