package chapter3.section3.c.ternary.search.and.others;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 19/04/22.
 */
public class Sretan {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int numberIndex = FastReader.nextInt();
        String luckyInteger = findLuckyInteger(numberIndex);

        outputWriter.printLine(luckyInteger);
        outputWriter.flush();
    }

    private static String findLuckyInteger(int numberIndex) {
        StringBuilder luckyInteger = new StringBuilder();
        long[] prefixSumRangesSize = computePrefixSumRangesSize();
        int numberOfDigits = getNumberOfDigits(prefixSumRangesSize, numberIndex);

        long rangeSize = (long) Math.pow(2, numberOfDigits);
        long adjustedNumberIndex = numberIndex;
        if (numberOfDigits >= 2) {
            adjustedNumberIndex -= prefixSumRangesSize[numberOfDigits - 2];
        }

        for (int i = 0; i < numberOfDigits; i++) {
            long valueToSubtract = 0;

            if (adjustedNumberIndex <= rangeSize / 2) {
                luckyInteger.append("4");
            } else {
                luckyInteger.append("7");
                valueToSubtract = rangeSize / 2;
            }
            rangeSize /= 2;
            adjustedNumberIndex -= valueToSubtract;
        }
        return luckyInteger.toString();
    }

    private static long[] computePrefixSumRangesSize() {
        long[] rangesSize = new long[30];
        rangesSize[0] = 2;
        long size = 2;

        for (int i = 1; i < rangesSize.length; i++) {
            size *= 2;
            rangesSize[i] = rangesSize[i - 1] + size;
        }
        return rangesSize;
    }

    private static int getNumberOfDigits(long[] prefixSumRangesSize, int numberIndex) {
        int low = 0;
        int high = prefixSumRangesSize.length - 1;
        int result = 0;

        while (low <= high) {
            int middle = low + (high - low) / 2;
            if (prefixSumRangesSize[middle] < numberIndex) {
                low = middle + 1;
            } else {
                result = middle;
                high = middle - 1;
            }
        }
        return result + 1;
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
