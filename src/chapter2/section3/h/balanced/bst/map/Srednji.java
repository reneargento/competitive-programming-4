package chapter2.section3.h.balanced.bst.map;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 09/06/21.
 */
public class Srednji {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int sequenceSize = FastReader.nextInt();
        int targetMedian = FastReader.nextInt();

        int[] values = new int[sequenceSize];
        int targetIndex = -1;

        for (int i = 0; i < values.length; i++) {
            int value = FastReader.nextInt();
            values[i] = value;
            if (value == targetMedian) {
                targetIndex = i;
            }
        }
        long subsequencesWithTargetMedian = countSubsequencesWithTargetMedian(values, targetMedian, targetIndex);
        outputWriter.printLine(subsequencesWithTargetMedian);
        outputWriter.flush();
    }

    private static long countSubsequencesWithTargetMedian(int[] values, int targetMedian, int targetIndex) {
        long subsequencesWithTargetMedian = 1;
        // Delta = NumbersHigherThanTarget - NumbersLowerThanTarget
        Map<Integer, Integer> leftDelta = computeDelta(values, targetMedian, targetIndex - 1, -1, -1);
        Map<Integer, Integer> rightDelta = computeDelta(values, targetMedian, targetIndex + 1, values.length, 1);

        subsequencesWithTargetMedian += leftDelta.getOrDefault(0, 0);
        subsequencesWithTargetMedian += rightDelta.getOrDefault(0, 0);
        for (int i = 0; i < values.length; i++) {
            long leftDeltaFrequencyPositive = leftDelta.getOrDefault(i, 0);
            long rightDeltaFrequencyNegative = rightDelta.getOrDefault(-i, 0);
            long leftDeltaFrequencyNegative = leftDelta.getOrDefault(-i, 0);
            long rightDeltaFrequencyPositive = rightDelta.getOrDefault(i, 0);

            subsequencesWithTargetMedian += leftDeltaFrequencyPositive * rightDeltaFrequencyNegative;
            if (i != 0) {
                subsequencesWithTargetMedian += leftDeltaFrequencyNegative * rightDeltaFrequencyPositive;
            }
        }
        return subsequencesWithTargetMedian;
    }

    private static Map<Integer, Integer> computeDelta(int[] values, int targetMedian, int targetIndex,
                                                      int targetStop, int increment) {
        Map<Integer, Integer> deltaMap = new HashMap<>();
        int higherThanMedian = 0;
        int lowerThanMedian = 0;

        for (int i = targetIndex; i != targetStop; i += increment) {
            if (values[i] > targetMedian) {
                higherThanMedian++;
            } else {
                lowerThanMedian++;
            }
            int delta = higherThanMedian - lowerThanMedian;
            int frequency = deltaMap.getOrDefault(delta, 0);
            deltaMap.put(delta, frequency + 1);
        }
        return deltaMap;
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
