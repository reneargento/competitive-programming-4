package chapter2.section2.section1;

/**
 * Created by Rene Argento on 09/01/21.
 */
public class Exercise3_5_LongestSubArray {

    public static void main(String[] args) {
        int[] array = { 10, 39, 0, 2, 23, 34, 16, 20, 98, 38, 23, 0};
        int length = getLongestIncreasingContiguousSubArrayLength(array);
        System.out.println("Longest increasing contiguous sub-array length: " + length);
        System.out.println("Expected: 4");
    }

    private static int getLongestIncreasingContiguousSubArrayLength(int[] array) {
        int maxLength = 0;
        int count = 1;

        for (int i = 0; i < array.length; i++) {
            if (i == array.length - 1 || array[i] >= array[i + 1]) {
                maxLength = Math.max(maxLength, count);
                count = 1;
            } else {
                count++;
            }
        }
        return maxLength;
    }
}
