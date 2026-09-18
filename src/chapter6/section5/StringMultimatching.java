package chapter6.section5;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Rene Argento on 12/09/2026.
 */
public class StringMultimatching {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] patterns = new String[Integer.parseInt(line)];
            for (int i = 0; i < patterns.length; i++) {
                patterns[i] = FastReader.getLine();
            }
            String text = FastReader.getLine();
            SuffixArrayNlgN suffixArray = new SuffixArrayNlgN(text);

            for (String pattern : patterns) {
                int[] positions = stringMatching(suffixArray, pattern);

                if (positions[0] != -1) {
                    List<Integer> occurrences = new ArrayList<>();
                    for (int i = positions[0]; i <= positions[1]; i++) {
                        occurrences.add(suffixArray.suffixArray[i]);
                    }
                    Collections.sort(occurrences);

                    outputWriter.print(occurrences.get(0));
                    for (int i = 1; i < occurrences.size(); i++) {
                        outputWriter.print(" " + occurrences.get(i));
                    }
                }
                outputWriter.printLine();
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int[] stringMatching(SuffixArrayNlgN suffixArray, String patternString) {
        char[] stringChars = suffixArray.string;
        char[] pattern = patternString.toCharArray();
        int low = 0;
        int high = suffixArray.suffixArray.length - 1;
        int middle;
        while (low < high) {             // find lower bound
            middle = (low + high) / 2;
            int compareResult = stringCompare(stringChars, suffixArray.suffixArray[middle], pattern, 0);  // try to find pattern in suffix 'middle'
            if (compareResult >= 0) {
                high = middle;          // prune upper half
            } else {
                low = middle + 1;       // prune lower half including middle
            }
        }
        if (stringCompare(stringChars, suffixArray.suffixArray[low], pattern,0) != 0) {
            return new int[]{ -1, -1 };         // if not found
        }

        int[] result = new int[]{ low, 0 } ;
        low = 0;
        high = suffixArray.suffixArray.length - 1;
        while (low < high) {             // if lower bound is found, find upper bound
            middle = (low + high) / 2;
            int compareResult = stringCompare(stringChars, suffixArray.suffixArray[middle], pattern,0);
            if (compareResult > 0) {
                high = middle;          // prune upper half
            } else {
                low = middle + 1;       // prune lower half including middle
            }
        }
        if (stringCompare(stringChars, suffixArray.suffixArray[high], pattern,0) != 0) {
            high--;                      // special case
        }
        result[1] = high;
        return result;
    } // return lower/upper bound as the first/second item of the pair, respectively

    private static int stringCompare(char[] string1, int index1, char[] string2, int index2) {
        int compares = 0;
        for (int i = 0; index1 + i < string1.length && index2 + i < string2.length; i++){
            if (string1[index1 + i] != string2[index2 + i]) {
                return string1[index1 + i] - string2[index2 + i];
            }
            compares++;
        }

        if (compares < string2.length) {
            return -1;
        }
        return 0;
    }

    private static class SuffixArrayNlgN {
        public int[] suffixArray;
        public int[] lcp;  // lcp[i] stores the LCP between previous suffix "T + SA[i-1]" and current suffix "T + SA[i]"

        private final char[] string;
        private final int stringLength;

        public SuffixArrayNlgN(String string) {
            char lastChar = (char) 0;
            string = string + lastChar;
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