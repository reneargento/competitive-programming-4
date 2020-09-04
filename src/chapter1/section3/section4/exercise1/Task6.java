package chapter1.section3.section4.exercise1;

import java.util.Arrays;

/**
 * Created by Rene Argento on 28/08/20.
 */
public class Task6 {

    public static void main(String[] args) {
        int[] numbers = {10, 7, 5, 20, 8};
        Arrays.sort(numbers);
        int index = Arrays.binarySearch(numbers, 7);

        System.out.println((index >= 0) ? "Exists" : "Does not exist");
    }
}
