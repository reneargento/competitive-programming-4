package chapter3.section5.b.longest.increasing.subsequence;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 10/11/22.
 */
public class StrategicDefenseInitiative {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        FastReader.getLine();

        for (int t = 0; t < tests; t++) {
            String line = FastReader.getLine();
            List<Integer> altitudes = new ArrayList<>();

            while (line != null && !line.isEmpty()) {
                altitudes.add(Integer.parseInt(line));
                line = FastReader.getLine();
            }
            List<Integer> lis = longestIncreasingSubsequence(altitudes);

            if (t > 0) {
                outputWriter.printLine();
            }
            outputWriter.printLine(String.format("Max hits: %d", lis.size()));
            for (int altitude : lis) {
                outputWriter.printLine(altitude);
            }
        }
        outputWriter.flush();
    }

    private static List<Integer> longestIncreasingSubsequence(List<Integer> altitudes) {
        if (altitudes.isEmpty()) {
            return new ArrayList<>();
        }
        int[] endIndexes = new int[altitudes.size()];
        int[] previousIndices = new int[altitudes.size()];

        Arrays.fill(previousIndices, -1);
        int length = 1;

        for (int i = 1; i < altitudes.size(); i++) {
            // Case 1 - smallest end element
            if (altitudes.get(i) <= altitudes.get(endIndexes[0])) {
                endIndexes[0] = i;
            } else if (altitudes.get(i) > altitudes.get(endIndexes[length - 1])) {
                // Case 2 - highest end element - extends longest increasing subsequence
                previousIndices[i] = endIndexes[length - 1];
                endIndexes[length++] = i;
            } else {
                // Case 3 - middle end element
                int indexToReplace = ceilIndex(altitudes, endIndexes, 0, length - 1, altitudes.get(i));
                previousIndices[i] = endIndexes[indexToReplace - 1];
                endIndexes[indexToReplace] = i;
            }
        }
        return getSequence(altitudes, endIndexes, previousIndices, length);
    }

    private static int ceilIndex(List<Integer> altitudes, int[] endIndexes, int low, int high, int key) {
        while (high > low) {
            int middle = low + (high - low) / 2;

            if (altitudes.get(endIndexes[middle]) >= key) {
                high = middle;
            } else {
                low = middle + 1;
            }
        }
        return high;
    }

    private static List<Integer> getSequence(List<Integer> altitudes, int[] endIndexes, int[] previousIndices, int length) {
        LinkedList<Integer> sequence = new LinkedList<>();

        for (int i = endIndexes[length - 1]; i >= 0; i = previousIndices[i]) {
            sequence.addFirst(altitudes.get(i));
        }
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
