package chapter3.section5.section3;

import java.util.Arrays;

/**
 * Created by Rene Argento on 02/10/22.
 */
public class Exercise1LIS {

    public static void main(String[] args) {
        int[] array = { 10, 22, 9, 33, 21, 50, 41, 60 };
        int longestIncreasingSubsequenceLength = longestIncreasingSubsequenceLength(array);
        System.out.println("LIS length: " + longestIncreasingSubsequenceLength + " Expected: 5");
    }

    private static int longestIncreasingSubsequenceLength(int[] array) {
        if (array.length == 0) {
            return 0;
        }
        int[] dp = new int[array.length];
        Arrays.fill(dp, 1);
        int longestIncreasingSubsequenceLength = 1;

        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] > array[i]) {
                    dp[j] = Math.max(dp[j], dp[i] + 1);
                    longestIncreasingSubsequenceLength = Math.max(longestIncreasingSubsequenceLength, dp[j]);
                }
            }
        }
        return longestIncreasingSubsequenceLength;
    }
}
