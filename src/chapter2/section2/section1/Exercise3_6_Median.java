package chapter2.section2.section1;

import java.util.Random;

/**
 * Created by Rene Argento on 09/01/21.
 */
public class Exercise3_6_Median {

    public static void main(String[] args) {
        int[] array = { 9, 1, 3, 6, 8, 2, 4, 5, 7 };
        int median = getMedian(array);
        System.out.println("Median: " + median);
        System.out.println("Expected: 5");
    }

    private static int getMedian(int[] array) {
        int medianIndex = array.length / 2;
        return partition(array, 0, array.length - 1, medianIndex);
    }

    private static int partition(int[] array, int start, int end, int medianIndex) {
        Random random = new Random();
        int rangeSize = end - start + 1;
        int pivotIndex = start + random.nextInt(rangeSize);
        int pivot = array[pivotIndex];

        swap(array, pivotIndex, end);
        int startIndex = start;
        int endIndex = end - 1;

        while (startIndex <= endIndex) {
            if (array[startIndex] >= pivot) {
                swap(array, startIndex, endIndex);
                endIndex--;
            } else {
                startIndex++;
            }
        }
        swap(array, startIndex, end);

        if (startIndex == medianIndex) {
            return pivot;
        } else if (startIndex < medianIndex) {
            return partition(array, startIndex + 1, end, medianIndex);
        } else {
            return partition(array, start, startIndex - 1, medianIndex);
        }
    }

    private static void swap(int[] array, int index1, int index2) {
        int aux = array[index1];
        array[index1] = array[index2];
        array[index2] = aux;
    }
}
