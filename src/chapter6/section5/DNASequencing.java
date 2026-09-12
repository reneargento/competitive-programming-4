package chapter6.section5;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 09/09/2026.
 */
public class DNASequencing {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int testCaseId = 1;
        String line = FastReader.getLine();
        while (line != null) {
            if (line.isEmpty()) {
                line = FastReader.getLine();
            }
            String string2 = FastReader.getLine();

            if (testCaseId > 1) {
                outputWriter.printLine();
            }
            List<String> lcsList = computeLCS(line, string2);
            if (lcsList.isEmpty()) {
                outputWriter.printLine("No common sequence.");
            } else {
                for (String lcs : lcsList) {
                    outputWriter.printLine(lcs);
                }
            }
            testCaseId++;
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static List<String> computeLCS(String string1, String string2) {
        Set<String> lcsSet = new HashSet<>();
        SuffixArrayNlgN suffixArray = new SuffixArrayNlgN(string1 + "#" + string2);
        int lcsLength = getLCSLength(suffixArray, string1.length());
        if (lcsLength == 0) {
            return new ArrayList<>();
        }

        for (int i = 1; i < suffixArray.lcp.length; i++) {
            int suffixArrayIndex1 = suffixArray.suffixArray[i - 1];
            int suffixArrayIndex2 = suffixArray.suffixArray[i];
            if (suffixArray.lcp[i] == lcsLength
                    && areDifferentStrings(suffixArrayIndex1, suffixArrayIndex2,
                    string1.length() + 1)) {
                String lcs = new String(suffixArray.string, suffixArray.suffixArray[i], lcsLength);
                lcsSet.add(lcs);
            }
        }

        List<String> lcsList = new ArrayList<>(lcsSet);
        Collections.sort(lcsList);
        return lcsList;
    }

    private static int getLCSLength(SuffixArrayNlgN suffixArray, int string1Length) {
        int longestLCSLength = 0;
        for (int i = 1; i < suffixArray.lcp.length; i++) {
            int suffixArrayIndex1 = suffixArray.suffixArray[i - 1];
            int suffixArrayIndex2 = suffixArray.suffixArray[i];

            if (areDifferentStrings(suffixArrayIndex1, suffixArrayIndex2,
                    string1Length + 1)) {
                longestLCSLength = Math.max(longestLCSLength, suffixArray.lcp[i]);
            }
        }
        return longestLCSLength;
    }

    private static boolean areDifferentStrings(int suffixArrayIndex1, int suffixArrayIndex2, int string1Length) {
        return (suffixArrayIndex1 < string1Length && suffixArrayIndex2 >= string1Length) ||
                (suffixArrayIndex2 < string1Length && suffixArrayIndex1 >= string1Length);
    }

    private static class SuffixArrayNlgN {
        public int[] suffixArray;
        public int[] lcp;  // lcp[i] stores the LCP between previous suffix "T + SA[i-1]" and current suffix "T + SA[i]"

        private final char[] string;
        private final int stringLength;

        public SuffixArrayNlgN(String string) {
            string = string + "$";
            this.string = string.toCharArray();
            stringLength = this.string.length;
            constructSuffixArray(); // O(N lg N)
            computeLcp();           // O(N)
        }

        private void countingSort(int[] ranks, int k) {
            int sum = 0;
            int[] tempSuffixArray = new int[stringLength];
            int maxi = Math.max(300, stringLength);                // up to 255 ASCII chars or length of n
            // for counting/radix sort
            int[] count = new int[maxi];
            for (int i = 0; i < stringLength; i++) {               // count the frequency of each rank
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

        private void constructSuffixArray() {            // this version can go up to 100000 characters
            suffixArray = new int[stringLength];
            int[] ranks = new int[stringLength];
            int[] tempRanks = new int[stringLength];

            for (int i = 0; i < stringLength; i++) {     // initial rankings
                ranks[i] = string[i];
            }
            for (int i = 0; i < stringLength; i++) {     // initial SA: { 0, 1, 2, ..., n-1 }
                suffixArray[i] = i;
            }
            for (int k = 1; k < stringLength; k <<= 1) {     // repeat sorting process log n times
                countingSort(ranks, k);                      // actually radix sort: sort based on the second item
                countingSort(ranks, 0);                   // then (stable) sort based on the first item

                int rank = 0;
                for (int i = 1; i < stringLength; i++) {     // compare adjacent suffixes
                    tempRanks[suffixArray[i]] =       // if same pair => same rank; otherwise, increase rank
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
            phi[suffixArray[0]] = -1;                      // default value
            for (int i = 1; i < stringLength; i++) {
                phi[suffixArray[i]] = suffixArray[i - 1];  // remember which suffix is previous to this suffix
            }
            for (int i = 0; i < stringLength; i++) {       // compute permuted lcp in O(n)
                if (phi[i] == -1) {                        // special case
                    plcp[i] = 0;
                    continue;
                }
                while (i + length < string.length
                        && phi[i] + length < string.length
                        && string[i + length] == string[phi[i] + length]) {
                    length++;                               // length will be increased at maximum n times
                }
                plcp[i] = length;
                length = Math.max(length - 1, 0);           // length will be decreased at maximum n times
            }
            for (int i = 1; i < stringLength; i++) {
                lcp[i] = plcp[suffixArray[i]];              // put the permuted LCP back to the correct position
            }
        }
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
        }

        private static String getLine() throws IOException {
            return reader.readLine();
        }
    }

    private static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++) {
                if (i != 0) {
                    writer.print(' ');
                }
                writer.print(objects[i]);
            }
        }

        public void printLine(Object... objects) {
            print(objects);
            writer.println();
        }

        public void flush() {
            writer.flush();
        }
    }
}