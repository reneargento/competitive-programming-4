package chapter2.section2.section1;

/**
 * Created by Rene Argento on 08/01/21.
 */
public class Exercise3_3_TwoSumSorted {

    private static class Pair {
        int value1;
        int value2;

        public Pair(int value1, int value2) {
            this.value1 = value1;
            this.value2 = value2;
        }
    }

    public static void main(String[] args) {
        int[] array = { 1, 2, 3, 4, 5 };
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
        int leftIndex = 0;
        int rightIndex = array.length - 1;

        while (leftIndex < rightIndex) {
            int sum = array[leftIndex] + array[rightIndex];

            if (sum == targetValue) {
                return new Pair(array[leftIndex], array[rightIndex]);
            } else if (sum < targetValue) {
                leftIndex++;
            } else {
                rightIndex++;
            }
        }
        return null;
    }
}
