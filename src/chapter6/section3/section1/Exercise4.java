package chapter6.section3.section1;

// Time complexity: O(n * d), where n is the length of string1 and d is the number of maximum insertions or deletions
public class Exercise4 {

    private static int bandedStringAlignment(String string1, String string2, int maximumInsertionsDeletions) {
        if (string1 == null || string2 == null) {
            return 0;
        }

        int[][] dp = new int[string1.length() + 1][string2.length() + 1];

        // Base cases
        for (int i = 1; i < dp.length; i++) {
            dp[i][0] = i * -1;
        }
        for (int j = 1; j < dp[0].length; j++) {
            dp[0][j] = j * -1;
        }

        for (int i = 1; i < dp.length; i++) {
            int from = Math.max(1, i - maximumInsertionsDeletions);
            int to = Math.min(dp[0].length - 1, i + maximumInsertionsDeletions);

            for (int j = from; j <= to; j++) {
                // Match: 2 points, mismatch: -1 point
                dp[i][j] = dp[i - 1][j - 1] + (string1.charAt(i - 1) == string2.charAt(j - 1) ? 2 : -1);
                dp[i][j] = Math.max(dp[i][j], dp[i - 1][j] - 1);
                dp[i][j] = Math.max(dp[i][j], dp[i][j - 1] - 1);
            }
        }
        return dp[string1.length()][string2.length()];
    }

    public static void main(String[] args) {
        String string1 = "ACAATCC";
        String string2 = "AGCATGC";
        int maximumInsertionsDeletions = 2;
        int alignmentScore = bandedStringAlignment(string1, string2, maximumInsertionsDeletions);
        System.out.println("Maximum alignment score: " + alignmentScore);
        System.out.println("Expected: 7");
    }
}
