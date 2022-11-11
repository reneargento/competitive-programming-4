package chapter3.section5.b.longest.increasing.subsequence;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 10/11/22.
 */
public class TestingTheCATCHER {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int height = FastReader.nextInt();
        int testNumber = 1;

        while (height != -1) {
            List<Integer> heights = new ArrayList<>();
            heights.add(height);

            while (height != -1) {
                height = FastReader.nextInt();
                heights.add(height);
            }
            int lis = longestIncreasingSubsequence(heights);

            if (testNumber > 1) {
                outputWriter.printLine();
            }
            outputWriter.printLine(String.format("Test #%d:", testNumber));
            outputWriter.printLine(String.format("  maximum possible interceptions: %d", lis));

            testNumber++;
            height = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    public static int longestIncreasingSubsequence(List<Integer> heights) {
        if (heights.isEmpty()) {
            return 0;
        }
        int[] endIndexes = new int[heights.size()];
        int length = 1;

        for (int i = heights.size() - 2; i >= 0; i--) {
            // Case 1 - smallest end element
            if (heights.get(i) < heights.get(endIndexes[0])) {
                endIndexes[0] = i;
            } else if (heights.get(i) >= heights.get(endIndexes[length - 1])) {
                // Case 2 - highest end element - extends longest increasing subsequence
                endIndexes[length++] = i;
            } else {
                // Case 3 - middle end element
                int indexToReplace = ceilIndex(heights, endIndexes, 0, length - 1, heights.get(i));
                if (indexToReplace != -1) {
                    endIndexes[indexToReplace] = i;
                }
            }
        }
        return length;
    }

    private static int ceilIndex(List<Integer> heights, int[] endIndexes, int low, int high, int key) {
        while (high > low) {
            int middle = low + (high - low) / 2;

            if (heights.get(endIndexes[middle]) >= key) {
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
