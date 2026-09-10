package chapter6.section5;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 08/09/2026.
 */
public class AutomaticTrading {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String string = FastReader.getLine();
        SuffixArrayNlgN suffixArray = new SuffixArrayNlgN(string);
        SparseTable sparseTable = new SparseTable(suffixArray.lcp);

        int queries = FastReader.nextInt();
        for (int q = 0; q < queries; q++) {
            int index1 = FastReader.nextInt();
            int index2 = FastReader.nextInt();

            int lce = computeLongestCommonExtension(suffixArray, sparseTable, index1, index2);
            outputWriter.printLine(lce);
        }
        outputWriter.flush();
    }

    private static int computeLongestCommonExtension(SuffixArrayNlgN suffixArray, SparseTable sparseTable, int index1,
                                                     int index2) {
        int position1 = suffixArray.positionInSA[index1];
        int position2 = suffixArray.positionInSA[index2];

        int minPosition = Math.min(position1, position2);
        int maxPosition = Math.max(position1, position2);

        return sparseTable.rangeMinQuery(minPosition + 1, maxPosition);
    }

    private static class SuffixArrayNlgN {
        public int[] suffixArray;
        public int[] lcp;  // lcp[i] stores the LCP between previous suffix "T + SA[i-1]" and current suffix "T + SA[i]"
        public int[] positionInSA;

        private final char[] string;
        private final int stringLength;

        public SuffixArrayNlgN(String string) {
            string = string + "$";
            this.string = string.toCharArray();
            stringLength = this.string.length;
            positionInSA = new int[stringLength];
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
                positionInSA[tempSuffixArray[i]] = i;
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
        private final int[][] sparseTable;

        SparseTable(int[] values) {
            sparseTable = buildSparseTable(values);
        }

        // O(N lg N)
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

        // O(1)
        public int rangeMinQuery(int start, int end) {
            int rangeLog = log2(end - start + 1);
            // Compute the minimum value of the first 2^rangeLog elements and the last 2^rangeLog elements in range.
            int value1 = sparseTable[start][rangeLog];
            int value2 = sparseTable[end - (1 << rangeLog) + 1][rangeLog];
            return Math.min(value1, value2);
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