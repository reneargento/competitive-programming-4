package chapter6.section5;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 11/09/2026.
 */
public class StammeringAliens {

    private static class SubstringData {
        int maximumLength;
        int startingIndex;

        public SubstringData(int maximumLength, int startingIndex) {
            this.maximumLength = maximumLength;
            this.startingIndex = startingIndex;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int minimumFrequency = FastReader.nextInt();
        while (minimumFrequency != 0) {
            String message = FastReader.getLine();
            SubstringData result = computeFrequencyLCS(message, minimumFrequency - 1);
            if (result == null) {
                outputWriter.printLine("none");
            } else {
                outputWriter.printLine(result.maximumLength + " " + result.startingIndex);
            }
            minimumFrequency = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static SubstringData computeFrequencyLCS(String message, int minimumFrequency) {
        if (minimumFrequency == 0) {
            return new SubstringData(message.length(), 0);
        }
        SuffixArrayNlgN suffixArray = new SuffixArrayNlgN(message);
        SubstringData result = null;
        SparseTable sparseTable = new SparseTable(suffixArray.lcp, suffixArray.suffixArray);

        for (int right = 1; right < suffixArray.lcp.length; right++) {
            int lastValidIndex = right - minimumFrequency + 1;
            if (lastValidIndex > 0 && lastValidIndex <= right) {
                SubstringData currentResult = sparseTable.rangeMinQuery(lastValidIndex, right);
                if (currentResult.maximumLength > 0
                        && (result == null
                            || currentResult.maximumLength > result.maximumLength
                            || (currentResult.maximumLength == result.maximumLength
                                && currentResult.startingIndex > result.startingIndex))) {
                    result = currentResult;
                }
            }
        }
        return result;
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

    private static class SparseTable {
        private final SubstringData[][] sparseTable;

        SparseTable(int[] values, int[] suffixIndexes) {
            sparseTable = buildSparseTable(values, suffixIndexes);
        }

        // O(N lg N)
        private SubstringData[][] buildSparseTable(int[] values, int[] suffixIndexes) {
            int columns = log2(values.length) + 1;
            SubstringData[][] sparseTable = new SubstringData[values.length][columns];

            for (int row = 1; row < sparseTable.length; row++) {
                int highestIndex = Math.max(suffixIndexes[row - 1], suffixIndexes[row]);
                sparseTable[row][0] = new SubstringData(values[row], highestIndex);
            }

            for (int column = 1; column < sparseTable[0].length; column++) {
                // Compute minimum value for all intervals of size 2^column
                for (int row = 1; row + ((1 << column) - 1) < sparseTable.length; row++) {
                    SubstringData value1 = sparseTable[row][column - 1];
                    SubstringData value2 = sparseTable[row + (1 << (column - 1))][column - 1];
                    sparseTable[row][column] = getMinResult(value1, value2);
                }
            }
            return sparseTable;
        }

        // O(1)
        public SubstringData rangeMinQuery(int start, int end) {
            int rangeLog = log2(end - start + 1);
            // Compute the minimum value of the first 2^rangeLog elements and the last 2^rangeLog elements in range.
            SubstringData value1 = sparseTable[start][rangeLog];
            SubstringData value2 = sparseTable[end - (1 << rangeLog) + 1][rangeLog];
            return getMinResult(value1, value2);
        }

        private static SubstringData getMinResult(SubstringData result1, SubstringData result2) {
            return new SubstringData(
                    Math.min(result1.maximumLength, result2.maximumLength),
                    Math.max(result1.startingIndex, result2.startingIndex)
            );
        }

        private int log2(int value) {
            return (int) (Math.log(value) / Math.log(2));
        }
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
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