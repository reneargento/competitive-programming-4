package chapter3.section5.b.longest.increasing.subsequence;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 12/11/22.
 */
public class LongestIncreasingSubsequence {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            int sequenceLength = Integer.parseInt(line);
            int[] sequence = new int[sequenceLength];
            for (int i = 0; i < sequence.length; i++) {
                sequence[i] = FastReader.nextInt();
            }

            List<Integer> lis = longestIncreasingSubsequence(sequence);
            outputWriter.printLine(lis.size());
            if (!lis.isEmpty()) {
                outputWriter.print(lis.get(0));
                for (int i = 1; i < lis.size(); i++) {
                    outputWriter.print(" " + lis.get(i));
                }
            }
            outputWriter.printLine();
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static List<Integer> longestIncreasingSubsequence(int[] sequence) {
        if (sequence.length == 0) {
            return new ArrayList<>();
        }
        int[] endIndexes = new int[sequence.length];
        int[] previousIndices = new int[sequence.length];

        Arrays.fill(previousIndices, 0, sequence.length, -1);
        int length = 1;

        for (int i = 1; i < sequence.length; i++) {
            // Case 1 - smallest end element
            if (sequence[i] <= sequence[endIndexes[0]]) {
                endIndexes[0] = i;
            } else if (sequence[i] > sequence[endIndexes[length - 1]]) {
                // Case 2 - highest end element - extends longest increasing subsequence
                previousIndices[i] = endIndexes[length - 1];
                endIndexes[length++] = i;
            } else {
                // Case 3 - middle end element
                int indexToReplace = ceilIndex(sequence, endIndexes, 0, length - 1, sequence[i]);
                previousIndices[i] = endIndexes[indexToReplace - 1];
                endIndexes[indexToReplace] = i;
            }
        }
        return getSequence(endIndexes, previousIndices, length);
    }

    private static int ceilIndex(int[] sequence, int[] endIndexes, int low, int high, int key) {
        while (high > low) {
            int middle = low + (high - low) / 2;

            if (sequence[endIndexes[middle]] >= key) {
                high = middle;
            } else {
                low = middle + 1;
            }
        }
        return high;
    }

    private static List<Integer> getSequence(int[] endIndexes, int[] previousIndices, int length) {
        List<Integer> sequence = new ArrayList<>();

        for (int i = endIndexes[length - 1]; i >= 0; i = previousIndices[i]) {
            sequence.add(i);
        }
        Collections.reverse(sequence);
        return sequence;
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens() ) {
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
