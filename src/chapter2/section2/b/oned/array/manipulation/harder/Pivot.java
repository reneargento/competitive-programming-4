package chapter2.section2.b.oned.array.manipulation.harder;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by Rene Argento on 26/01/21.
 */
public class Pivot {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int integers = scanner.nextInt();
        int[] array = new int[integers];
        Set<Integer> correctLeftToRight = new HashSet<>();
        Set<Integer> correctRightToLeft = new HashSet<>();
        int maxValueSoFar = Integer.MIN_VALUE;
        int minValueSoFar = Integer.MAX_VALUE;

        for (int i = 0; i < array.length; i++) {
            array[i] = scanner.nextInt();
        }

        // Left to right
        for (int i = 0; i < array.length; i++) {
            if (array[i] > maxValueSoFar) {
                correctLeftToRight.add(array[i]);
            }
            maxValueSoFar = Math.max(maxValueSoFar, array[i]);
        }
        // Right to left
        for (int i = array.length - 1; i >= 0; i--) {
            if (array[i] < minValueSoFar) {
                correctRightToLeft.add(array[i]);
            }
            minValueSoFar = Math.min(minValueSoFar, array[i]);
        }

        int possiblePivots = 0;
        for (int integerLeftToRight : correctLeftToRight) {
            if (correctRightToLeft.contains(integerLeftToRight)) {
                possiblePivots++;
            }
        }
        System.out.println(possiblePivots);
    }
}
