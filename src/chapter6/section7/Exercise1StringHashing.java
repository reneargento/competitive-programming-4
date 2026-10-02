package chapter6.section7;

/**
 * Created by Rene Argento on 02/10/26.
 */
public class Exercise1StringHashing {

    public static void main() {
        String string1 = "forgeeksskeegfor";
        int lps1 = longestPalindromicSubstringLength(string1);
        System.out.println("Longest Palindromic Substring length: " + lps1);
        System.out.println("Expected: 10");

        String string2 = "bananas";
        int lps2 = longestPalindromicSubstringLength(string2);
        System.out.println("\nLongest Palindromic Substring length: " + lps2);
        System.out.println("Expected: 5");
    }

    public static int longestPalindromicSubstringLength(String string) {
        if (string.length() <= 1) {
            return string.length();
        }
        String reverse = new StringBuilder(string).reverse().toString();
        DoubleHash forwardHash = new DoubleHash(string);
        DoubleHash reverseHash = new DoubleHash(reverse);

        int longestLength = 1;

        // Odd-length palindromes.
        //
        // Center = i
        // radius = 0 -> length 1
        // radius = 1 -> length 3
        // radius = 2 -> length 5
        for (int i = 0; i < string.length(); i++) {
            int low = 0;
            int high = Math.min(i, string.length() - 1 - i);
            int bestRadius = 0;

            while (low <= high) {
                int radius = (low + high) >>> 1;
                int left = i - radius;
                int right = i + radius + 1;

                if (isPalindrome(string.length(), forwardHash, reverseHash, left, right)) {
                    bestRadius = radius;
                    low = radius + 1;
                } else {
                    high = radius - 1;
                }
            }
            longestLength = Math.max(longestLength, 2 * bestRadius + 1);
        }

        // Even-length palindromes.
        //
        // Center is between i-1 and i.
        // radius = 0 -> length 0
        // radius = 1 -> length 2
        // radius = 2 -> length 4
        for (int i = 0; i <= string.length(); i++) {
            int low = 0;
            int high = Math.min(i, string.length() - i);
            int bestRadius = 0;

            while (low <= high) {
                int radius = (low + high) >>> 1;
                int left = i - radius;
                int right = i + radius;

                if (isPalindrome(string.length(), forwardHash, reverseHash, left, right)) {
                    bestRadius = radius;
                    low = radius + 1;
                } else {
                    high = radius - 1;
                }
            }
            longestLength = Math.max(longestLength, 2 * bestRadius);
        }
        return longestLength;
    }

    private static boolean isPalindrome(int n, DoubleHash forwardHash, DoubleHash reverseHash, int left, int right) {
        // s[left..right)
        // Corresponding substring in reverse(s):
        // [n-right, n-left)
        //
        // If they are equal, s[left..right) is a palindrome.
        return forwardHash.equals(reverseHash, left, right, n - right, n - left);
    }

    private static final long MOD1 = 1_000_000_007L;
    private static final long MOD2 = 1_000_000_009L;
    private static final long BASE = 911382323L;

    private static class DoubleHash {
        long[] hash1;
        long[] hash2;
        long[] power1;
        long[] power2;

        DoubleHash(String string) {
            int n = string.length();
            hash1 = new long[n + 1];
            hash2 = new long[n + 1];
            power1 = new long[n + 1];
            power2 = new long[n + 1];
            power1[0] = 1;
            power2[0] = 1;

            for (int i = 0; i < n; i++) {
                int value = string.charAt(i);
                hash1[i + 1] = (hash1[i] * BASE + value) % MOD1;
                hash2[i + 1] = (hash2[i] * BASE + value) % MOD2;
                power1[i + 1] = (power1[i] * BASE) % MOD1;
                power2[i + 1] = (power2[i] * BASE) % MOD2;
            }
        }

        // Hash of s[l..r), normalized so equal strings have equal hashes.
        private long getHash1(int l, int r) {
            long result = hash1[r] - hash1[l] * power1[r - l] % MOD1;
            if (result < 0) {
                result += MOD1;
            }
            return result;
        }

        private long getHash2(int l, int r) {
            long result = hash2[r] - hash2[l] * power2[r - l] % MOD2;
            if (result < 0) {
                result += MOD2;
            }
            return result;
        }

        private boolean equals(DoubleHash other, int l1, int r1, int l2, int r2) {
            return r1 - l1 == r2 - l2
                    && getHash1(l1, r1) == other.getHash1(l2, r2)
                    && getHash2(l1, r1) == other.getHash2(l2, r2);
        }
    }
}
