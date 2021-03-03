package chapter2.section2.g.special.sorting.problems;

import java.util.Scanner;

/**
 * Created by Rene Argento on 02/03/21.
 */
public class FlipSort {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            int numbersLength = scanner.nextInt();
            long[] numbers = new long[numbersLength];

            for (int i = 0; i < numbers.length; i++) {
                numbers[i] = scanner.nextInt();
            }

            long inversions = countInversions(numbers);
            System.out.printf("Minimum exchange operations : %d\n", inversions);
        }
    }

    private static long countInversions(long[] array) {
        long[] helper = new long[array.length];
        return countInversions(array, helper, 0, array.length - 1);
    }

    private static long countInversions(long[] array, long[] helper, int low, int high) {
        if (low >= high) {
            return 0;
        }
        int middle = low + (high - low) / 2;

        long inversions = 0;

        inversions += countInversions(array, helper, low, middle);
        inversions += countInversions(array, helper, middle + 1, high);
        inversions += countSplitInversions(array, helper, low, middle, high);
        return inversions;
    }

    private static long countSplitInversions(long[] array, long[] helper, int low, int middle, int high) {
        long inversions = 0;

        for (int i = low; i <= high; i++) {
            helper[i] = array[i];
        }

        int i = low;
        int j = middle + 1;
        int k = low;

        while (i <= middle && j <= high) {
            if (helper[i] <= helper[j]) {
                array[k] = helper[i];
                i++;
            } else {
                array[k] = helper[j];
                j++;
                inversions += middle - i + 1;
            }
            k++;
        }

        while (i <= middle) {
            array[k] = helper[i];
            k++;
            i++;
        }
        return inversions;
    }
}
