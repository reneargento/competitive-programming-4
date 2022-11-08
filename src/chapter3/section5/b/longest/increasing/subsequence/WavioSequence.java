package chapter3.section5.b.longest.increasing.subsequence;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 07/11/22.
 */
public class WavioSequence {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            if (line.isEmpty()) {
                line = FastReader.getLine();
                continue;
            }

            int sequenceLength = Integer.parseInt(line);
            List<Integer> sequence = new ArrayList<>();
            for (int i = 0; i < sequenceLength; i++) {
                sequence.add(FastReader.nextInt());
            }

            int longestWavioSequenceLength = computeLongestWavioSequenceLength(sequence);
            outputWriter.printLine(longestWavioSequenceLength);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeLongestWavioSequenceLength(List<Integer> sequence) {
        int[] lisLengthEndingAtIndex1 = longestIncreasingSubsequence(sequence);
        Collections.reverse(sequence);
        int[] lisLengthEndingAtIndex2 = longestIncreasingSubsequence(sequence);

        int longestWavioSequenceLength = 0;
        for (int i = 0; i < lisLengthEndingAtIndex1.length; i++) {
            int reverseIndex = lisLengthEndingAtIndex2.length - 1 - i;
            int sequenceLength = 2 * Math.min(lisLengthEndingAtIndex1[i], lisLengthEndingAtIndex2[reverseIndex]) - 1;
            longestWavioSequenceLength = Math.max(longestWavioSequenceLength, sequenceLength);
        }
        return longestWavioSequenceLength;
    }

    private static int[] longestIncreasingSubsequence(List<Integer> sequence) {
        int[] endIndexes = new int[sequence.size()];
        int[] lisLengthEndingAtIndex = new int[sequence.size()];
        lisLengthEndingAtIndex[0] = 1;
        int length = 1;

        for (int i = 1; i < sequence.size(); i++) {
            // Case 1 - smallest end element
            if (sequence.get(i) <= sequence.get(endIndexes[0])) {
                endIndexes[0] = i;
                lisLengthEndingAtIndex[i] = 1;
            } else if (sequence.get(i) > sequence.get(endIndexes[length - 1])) {
                // Case 2 - highest end element - extends longest increasing subsequence
                endIndexes[length++] = i;
                lisLengthEndingAtIndex[i] = length;
            } else {
                // Case 3 - middle end element
                int indexToReplace = ceilIndex(sequence, endIndexes, 0, length - 1, sequence.get(i));
                endIndexes[indexToReplace] = i;
                lisLengthEndingAtIndex[i] = indexToReplace + 1;
            }
        }
        return lisLengthEndingAtIndex;
    }

    private static int ceilIndex(List<Integer> sequence, int[] endIndexes, int low, int high, int key) {
        while (high > low) {
            int middle = low + (high - low) / 2;

            if (sequence.get(endIndexes[middle]) >= key) {
                high = middle;
            } else {
                low = middle + 1;
            }
        }
        return high;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
