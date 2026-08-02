package chapter6.section3.a.classic;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 31/07/2026.
 */
public class BridgingSignals {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int[] ports = new int[FastReader.nextInt()];
        for (int i = 0; i < ports.length; i++) {
            ports[i] = FastReader.nextInt();
        }
        int maximumSignals = longestIncreasingSubsequence(ports);
        outputWriter.print(maximumSignals);
        outputWriter.flush();
    }

    private static int longestIncreasingSubsequence(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        int[] endIndexes = new int[array.length];
        int length = 1;

        for (int i = 1; i < array.length; i++) {
            // Case 1 - smallest end element
            if (array[i] <= array[endIndexes[0]]) {
                endIndexes[0] = i;
            } else if (array[i] > array[endIndexes[length - 1]]) {
                // Case 2 - highest end element - extends longest increasing subsequence
                endIndexes[length++] = i;
            } else {
                // Case 3 - middle end element
                int indexToReplace = ceilIndex(array, endIndexes, 0, length - 1, array[i]);
                endIndexes[indexToReplace] = i;
            }
        }
        return length;
    }

    private static int ceilIndex(int[] array, int[] endIndexes, int low, int high, int key) {
        while (high > low) {
            int middle = low + (high - low) / 2;

            if (array[endIndexes[middle]] >= key) {
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