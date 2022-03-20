package chapter3.section3.a.binary.search;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 19/03/22.
 */
public class NNODN {

    private static final int MAX_VALUE = 1000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        List<Long> sequence = computeSequence();
        long[] prefixSum = computePrefixSum(sequence);

        for (int t = 1; t <= tests; t++) {
            int start = FastReader.nextInt();
            int end = FastReader.nextInt();
            long valuesInRange = prefixSum[end] - prefixSum[start - 1];
//            long valuesInRange = countValuesInRangeWithBinarySearch(sequence, start, end);
            outputWriter.printLine(String.format("Case %d: %d", t, valuesInRange));
        }
        outputWriter.flush();
    }

    private static List<Long> computeSequence() {
        List<Long> sequence = new ArrayList<>();
        long number = 1;

        while (number <= MAX_VALUE) {
            sequence.add(number);
            number += countDivisors(number);
        }
        return sequence;
    }

    private static int countDivisors(long number) {
        int divisors = 0;
        int upperLimit = (int) Math.sqrt(number);

        for(int i = 1; i <= upperLimit; i++) {
            if (number % i == 0) {
                divisors++;

                if (i != number / i) {
                    divisors++;
                }
            }
        }
        return divisors;
    }

    private static long[] computePrefixSum(List<Long> sequence) {
        int[] values = new int[MAX_VALUE + 1];
        for (long value : sequence) {
            values[(int) value] = 1;
        }

        long[] prefixSum = new long[values.length];
        for (int i = 1; i < prefixSum.length; i++) {
            prefixSum[i] = prefixSum[i - 1] + values[i];
        }
        return prefixSum;
    }

    // *************************************************************************************
    // Alternative solution with binary search

    private static long countValuesInRangeWithBinarySearch(List<Long> sequence, int start, int end) {
        int lowerBound = binarySearch(sequence, start, true, 0, sequence.size() - 1);
        if (lowerBound == -1) {
            return 0;
        }
        int upperBound = binarySearch(sequence, end, false, 0, sequence.size() - 1);
        return upperBound - lowerBound + 1;
    }

    private static int binarySearch(List<Long> values, int target, boolean isLowerBound, int low, int high) {
        int result = -1;

        while (low <= high) {
            int middle = low + (high - low) / 2;

            if (isLowerBound) {
                if (values.get(middle) < target) {
                    low = middle + 1;
                } else {
                    result = middle;
                    int candidate = binarySearch(values, target, true, low, middle - 1);
                    if (candidate != -1) {
                        result = candidate;
                    }
                    break;
                }
            } else {
                if (values.get(middle) > target) {
                    high = middle - 1;
                } else {
                    result = middle;
                    int candidate = binarySearch(values, target, false, middle + 1, high);
                    if (candidate != -1) {
                        result = candidate;
                    }
                    break;
                }
            }
        }
        return result;
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
