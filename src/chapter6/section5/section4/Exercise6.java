package chapter6.section5.section4;

public class Exercise6 {

    public static void main() {
        SuffixArrayNlgN suffixArray = new SuffixArrayNlgN("ABABA#");
        int distinctSubstrings = suffixArray.computeDistinctSubstrings();
        System.out.println("Distinct substrings: " + distinctSubstrings);
        System.out.println("Expected: 10");
    }

    private static class SuffixArrayNlgN {
        private int[] suffixArray;
        private int[] lcp;

        private final char[] string;
        private final int stringLength;

        SuffixArrayNlgN(String string) {
            this.string = string.toCharArray();
            stringLength = this.string.length;
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

        private int computeDistinctSubstrings() {
            int distinctSubstrings = 1; // initialize with 1 for the empty substring
            distinctSubstrings += string.length - suffixArray[0] - 1;

            for (int i = 1; i < lcp.length; i++) {
                int suffixLength = string.length - suffixArray[i] - 1;
                distinctSubstrings += (suffixLength - lcp[i]);
            }
            return distinctSubstrings;
        }
    }
}
