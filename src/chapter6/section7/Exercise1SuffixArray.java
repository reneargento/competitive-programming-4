package chapter6.section7;

/**
 * Created by Rene Argento on 30/09/26.
 */
public class Exercise1SuffixArray {

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

    private static int longestPalindromicSubstringLength(String string) {
        String reverseString = new StringBuilder(string).reverse().toString();
        int maxLength = 0;

        String concatenatedString = string + "#" + reverseString;
        SuffixArrayNlgN suffixArray = new SuffixArrayNlgN(concatenatedString);
        SparseTable sparseTable = new SparseTable(suffixArray.lcp);

        // Odd-length palindromes
        for (int center = 0; center < string.length(); center++) {
            int originalSuffixIndex = center + 1;
            int reversedSuffixIndex = 2 * string.length() + 1 - center;

            if (originalSuffixIndex < concatenatedString.length()
                    && reversedSuffixIndex < concatenatedString.length()) {
                int centerIndex = Math.min(center, string.length() - center - 1);
                if (centerIndex == 0) {
                    maxLength = Math.max(maxLength, 1);
                    continue;
                }

                int startIndex = Math.min(suffixArray.rank[originalSuffixIndex], suffixArray.rank[reversedSuffixIndex]) + 1;
                int endIndex = Math.max(suffixArray.rank[originalSuffixIndex], suffixArray.rank[reversedSuffixIndex]);
                int minLcp = sparseTable.rangeMinQuery(startIndex, endIndex);

                int radius = Math.min(minLcp, centerIndex);
                int palindromicLength = 2 * radius + 1;
                maxLength = Math.max(maxLength, palindromicLength);
            }
        }

        // Even length palindromes
        for (int center = 1; center < string.length(); center++) {
            int originalSuffixIndex = center;
            int reversedSuffixIndex = 2 * string.length() + 1 - center;

            if (reversedSuffixIndex < concatenatedString.length()) {
                int startIndex = Math.min(suffixArray.rank[originalSuffixIndex], suffixArray.rank[reversedSuffixIndex]) + 1;
                int endIndex = Math.max(suffixArray.rank[originalSuffixIndex], suffixArray.rank[reversedSuffixIndex]);
                int minLcp = sparseTable.rangeMinQuery(startIndex, endIndex);

                int centerIndex = string.length() - center;
                int radius = Math.min(minLcp, centerIndex);
                int palindromicLength = 2 * radius;
                maxLength = Math.max(maxLength, palindromicLength);
            }
        }
        return maxLength;
    }

    private static class SuffixArrayNlgN {
        public int[] suffixArray;
        public int[] lcp;
        private final int[] rank;

        public final char[] string;
        private final int stringLength;

        public SuffixArrayNlgN(String string) {
            char lastChar = (char) 0;
            string = string + lastChar;
            this.string = string.toCharArray();
            stringLength = this.string.length;
            rank = new int[stringLength];
            constructSuffixArray();
            computeLcp();
        }

        private void countingSort(int[] ranks, int k) {
            int sum = 0;
            int[] tempSuffixArray = new int[stringLength];
            int maxi = Math.max(300, stringLength);
            // for counting/radix sort
            int[] count = new int[maxi];
            for (int i = 0; i < stringLength; i++) {
                count[i + k < stringLength ? ranks[i + k] : 0]++;
            }
            for (int i = 0; i < maxi; i++) {
                int aux = count[i];
                count[i] = sum;
                sum += aux;
            }
            for (int i = 0; i < stringLength; i++) {
                tempSuffixArray[count[suffixArray[i] + k < stringLength ? ranks[suffixArray[i] + k] : 0]++] = suffixArray[i];
            }
            for (int i = 0; i < stringLength; i++) {
                suffixArray[i] = tempSuffixArray[i];
                rank[suffixArray[i]] = i;
            }
        }

        private void constructSuffixArray() {
            suffixArray = new int[stringLength];
            int[] ranks = new int[stringLength];
            int[] tempRanks = new int[stringLength];

            for (int i = 0; i < stringLength; i++) {
                ranks[i] = string[i];
            }
            for (int i = 0; i < stringLength; i++) {
                suffixArray[i] = i;
            }
            for (int k = 1; k < stringLength; k <<= 1) {
                countingSort(ranks, k);
                countingSort(ranks, 0);

                int rank = 0;
                for (int i = 1; i < stringLength; i++) {
                    tempRanks[suffixArray[i]] =
                            (ranks[suffixArray[i]] == ranks[suffixArray[i - 1]] && ranks[suffixArray[i] + k] == ranks[suffixArray[i - 1] + k]) ? rank : ++rank;
                }
                for (int i = 0; i < stringLength; i++) {
                    ranks[i] = tempRanks[i];
                }
            }
        }

        private void computeLcp() {
            int length = 0;
            lcp = new int[stringLength];
            int[] plcp = new int[stringLength];
            int[] phi = new int[stringLength];
            phi[suffixArray[0]] = -1;
            for (int i = 1; i < stringLength; i++) {
                phi[suffixArray[i]] = suffixArray[i - 1];
            }
            for (int i = 0; i < stringLength; i++) {
                if (phi[i] == -1) {
                    plcp[i] = 0;
                    continue;
                }
                while (i + length < string.length
                        && phi[i] + length < string.length
                        && string[i + length] == string[phi[i] + length]) {
                    length++;
                }
                plcp[i] = length;
                length = Math.max(length - 1, 0);
            }
            for (int i = 1; i < stringLength; i++) {
                lcp[i] = plcp[suffixArray[i]];
            }
        }
    }

    private static class SparseTable {
        private final int[][] sparseTable;

        SparseTable(int[] values) {
            sparseTable = buildSparseTable(values);
        }

        private int[][] buildSparseTable(int[] values) {
            int columns = log2(values.length) + 1;
            int[][] sparseTable = new int[values.length][columns];

            for (int row = 0; row < sparseTable.length; row++) {
                sparseTable[row][0] = values[row];
            }

            for (int column = 1; column < sparseTable[0].length; column++) {
                // Compute minimum value for all intervals of size 2^column
                for (int row = 0; row + ((1 << column) - 1) < sparseTable.length; row++) {
                    int value1 = sparseTable[row][column - 1];
                    int value2 = sparseTable[row + (1 << (column - 1))][column - 1];
                    sparseTable[row][column] = Math.min(value1, value2);
                }
            }
            return sparseTable;
        }

        public int rangeMinQuery(int start, int end) {
            int rangeLog = log2(end - start + 1);
            int value1 = sparseTable[start][rangeLog];
            int value2 = sparseTable[end - (1 << rangeLog) + 1][rangeLog];
            return Math.min(value1, value2);
        }

        private int log2(int value) {
            return (int) (Math.log(value) / Math.log(2));
        }
    }
}
