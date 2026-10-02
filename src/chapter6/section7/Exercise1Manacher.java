package chapter6.section7;

public class Exercise1Manacher {

    public static void main() {
        String string1 = "forgeeksskeegfor";
        Manacher manacher1 = new Manacher(string1);
        int lps1 = manacher1.longestPalindromicSubstringLength();
        System.out.println("Longest Palindromic Substring length: " + lps1);
        System.out.println("Expected: 10");

        String string2 = "bananas";
        Manacher manacher2 = new Manacher(string2);
        int lps2 = manacher2.longestPalindromicSubstringLength();
        System.out.println("\nLongest Palindromic Substring length: " + lps2);
        System.out.println("Expected: 5");
    }

    private static class Manacher {
        private final int[] lengths; // lengths[i] = length of the longest palindromic substring of transformedString, centered at i
        private final String string; // original string
        private char[] transformedString;

        public Manacher(String string) {
            this.string = string;
            preProcess();
            lengths = new int[transformedString.length];

            int center = 0;
            int right = 0;

            for (int index = 1; index < transformedString.length - 1; index++) {
                int mirrorIndex = 2 * center - index; // Same as mirrorIndex = center - (index - center)

                if (right > index) {
                    lengths[index] = Math.min(lengths[mirrorIndex], right - index);
                }

                // Attempt to expand palindrome centered at index
                while (transformedString[index - (1 + lengths[index])] == transformedString[index + (1 + lengths[index])]) {
                    lengths[index]++;
                }

                // If palindrome centered at index expands past right, adjust center based on expanded palindrome.
                if (index + lengths[index] > right) {
                    center = index;
                    right = index + lengths[index];
                }
            }
        }

        // Transform string into transformedString.
        // For example, if string = "abba", then transformedString = "$#a#b#b#a#@"
        // The # are interleaved to handle even/odd-length palindromes uniformly.
        // $ and @ are prepended and appended to each end to avoid bounds checking.
        private void preProcess() {
            transformedString = new char[string.length() * 2 + 3];
            transformedString[0] = '$';
            transformedString[string.length() * 2 + 2] = '@';

            for (int index = 0; index < string.length(); index++) {
                transformedString[index * 2 + 1] = '#';
                transformedString[index * 2 + 2] = string.charAt(index);
            }
            transformedString[string.length() * 2 + 1] = '#';
        }

        public int longestPalindromicSubstringLength() {
            int maxLength = 0;
            for (int index = 1; index < lengths.length - 1; index++) {
                maxLength = Math.max(maxLength, lengths[index]);
            }
            return maxLength;
        }
    }
}
