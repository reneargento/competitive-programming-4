package chapter2.section2.section1;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Rene Argento on 08/01/21.
 */
public class Exercise3_2_TwoSum {

    private static class Pair {
        int value1;
        int value2;

        public Pair(int value1, int value2) {
            this.value1 = value1;
            this.value2 = value2;
        }
    }

    public static void main(String[] args) {
        int[] array = { 5, 2, 1, 4, 3 };
        int targetValue = 8;
        Pair pair = find2Sum(array, targetValue);

        if (pair != null) {
            System.out.println(pair.value1 + ", " + pair.value2);
        } else {
            System.out.println("There are no two integers that sum to " + targetValue);
        }
        System.out.println("Expected: 3, 5");
    }

    private static Pair find2Sum(int[] array, int targetValue) {
        Set<Integer> values = new HashSet<>();
        for (int value : array) {
            int complement = targetValue - value;
            if (values.contains(complement)) {
                return new Pair(value, complement);
            }
            values.add(value);
        }
        return null;
    }
}
