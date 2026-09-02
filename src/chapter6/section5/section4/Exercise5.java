package chapter6.section5.section4;

public class Exercise5 {

    public static void main() {
        SuffixArrayNlgN suffixArray = new SuffixArrayNlgN("GATAGACA#");

        String lcp1 = suffixArray.computeLcpRange(1, 4);
        System.out.println("LCP 1: " + lcp1 + ", length " + lcp1.length());
        System.out.println("Expected: A, length 1\n");

        String lcp2 = suffixArray.computeLcpRange(6, 7);
        System.out.println("LCP 2: " + lcp2 + ", length " + lcp2.length());
        System.out.println("Expected: GA, length 2\n");

        String lcp3 = suffixArray.computeLcpRange(0, 8);
        System.out.println("LCP 3: " + lcp3 + ", length " + lcp3.length());
        System.out.println("Expected: , length 0");
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

        private String computeLcpRange(int start, int end) {
            String lcpRange = "";
            int minLcp = Integer.MAX_VALUE;
            for (int i = start + 1; i <= end && i < stringLength; i++) {
                if (lcp[i] >= 0
                        && lcp[i] < minLcp) {
                    lcpRange = new String(string).substring(suffixArray[i],
                            suffixArray[i] + lcp[i]);
                    minLcp = lcp[i];
                }
            }
            return lcpRange;
        }
    }
}
