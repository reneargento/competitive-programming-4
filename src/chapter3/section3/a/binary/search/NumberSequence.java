package chapter3.section3.a.binary.search;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 17/03/22.
 */
public class NumberSequence {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        List<Long> sequenceSums = computeSequenceSums();

        for (int t = 0; t < tests; t++) {
            int number = FastReader.nextInt();
            int digit = getDigit(sequenceSums, number);
            outputWriter.printLine(digit);
        }
        outputWriter.flush();
    }

    private static List<Long> computeSequenceSums() {
        List<Long> sequenceSums = new ArrayList<>();
        sequenceSums.add(0L);
        long sum = 0;
        int totalLength = 0;

        for (int number = 1; sum < Integer.MAX_VALUE; number++) {
            int length = String.valueOf(number).length();
            totalLength += length;
            sum = sequenceSums.get(number - 1) + totalLength;
            sequenceSums.add(sum);
        }
        return sequenceSums;
    }

    private static int getDigit(List<Long> sequenceSums, int number) {
        int previousSequenceIndex = binarySearchLowerBound(sequenceSums, number, 0, sequenceSums.size() - 1);
        int nextSequenceDigits = (int) (number - sequenceSums.get(previousSequenceIndex));
        StringBuilder digits = new StringBuilder();
        int nextNumber = 0;

        while (digits.length() < nextSequenceDigits) {
            nextNumber++;
            digits.append(nextNumber);
        }
        return Character.getNumericValue(digits.charAt(nextSequenceDigits - 1));
    }

    private static int binarySearchLowerBound(List<Long> sequenceSums, int number, int low, int high) {
        while (low <= high) {
            int middle = low + (high - low) / 2;
            if (sequenceSums.get(middle) >= number) {
                high = middle - 1;
            } else {
                int result = middle;
                int candidate = binarySearchLowerBound(sequenceSums, number, middle + 1, high);
                if (candidate != -1) {
                    result = candidate;
                }
                return result;
            }
        }
        return -1;
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
