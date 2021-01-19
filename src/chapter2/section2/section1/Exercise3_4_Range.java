package chapter2.section2.section1;

import java.util.Arrays;
import java.util.StringJoiner;

/**
 * Created by Rene Argento on 08/01/21.
 */
public class Exercise3_4_Range {

    public static void main(String[] args) {
        int[] array = { 10, 39, 2, 23, 34, 16, 20, 98, 38, 23, 0};
        int start = 20;
        int end = 40;
        printNumbersInRange(array, start, end);
        System.out.println("Expected: 20, 23, 23, 34, 38, 39");
    }

    private static void printNumbersInRange(int[] array, int start, int end) {
        if (end - start <= 1000000) {
            printNumbersInRangeWithCountingSort(array, start, end);
        } else {
            printNumbersInRangeWithSort(array, start, end);
        }
    }

    private static void printNumbersInRangeWithCountingSort(int[] array, int start, int end) {
        int[] count = new int[end - start + 1];

        for (int value : array) {
            if (start <= value && value <= end) {
                count[value - start]++;
            }
        }

        StringJoiner list = new StringJoiner(", ");

        for (int i = 0; i < count.length; i++) {
            while (count[i] != 0) {
                list.add(String.valueOf(i + start));
                count[i]--;
            }
        }
        System.out.println(list);
    }

    private static void printNumbersInRangeWithSort(int[] array, int start, int end) {
        Arrays.sort(array);
        StringJoiner list = new StringJoiner(", ");

        for (int value : array) {
            if (start <= value && value <= end) {
                list.add(String.valueOf(value));
            } else if (value > end) {
                break;
            }
        }
        System.out.println(list);
    }
}
