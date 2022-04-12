package chapter3.section3.c.ternary.search.and.others;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 12/04/22.
 */
public class KuPellaKeSBST {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int[] values = new int[FastReader.nextInt()];
            for (int i = 0; i < values.length; i++) {
                values[i] = FastReader.nextInt();
            }

            Arrays.sort(values);
            long[] prefixSum = computePrefixSum(values);
            outputWriter.print(String.format("Case #%d: ", t));
            printKuPellaKeSBST(values, outputWriter, prefixSum, 0, values.length - 1);
            outputWriter.printLine();
        }
        outputWriter.flush();
    }

    private static long[] computePrefixSum(int[] values) {
        long[] prefixSum = new long[values.length + 1];
        for (int i = 1; i <= values.length; i++) {
            prefixSum[i] = prefixSum[i - 1] + values[i - 1];
        }
        return prefixSum;
    }

    private static void printKuPellaKeSBST(int[] values, OutputWriter outputWriter, long[] prefixSum,
                                           int low, int high) {
        if (low > high) {
            return;
        }

        int index = -1;
        long minimalDifference = Long.MAX_VALUE;
        long highestLeftSum = Long.MIN_VALUE;
        for (int i = low; i <= high; i++) {
            if (i < high && values[i] == values[i + 1]) {
                continue;
            }
            long leftSum = prefixSum[i] - prefixSum[low];
            long rightSum = prefixSum[high + 1] - prefixSum[i + 1];
            long difference = Math.abs(rightSum - leftSum);

            if (difference < minimalDifference
                    || (difference == minimalDifference && leftSum > highestLeftSum)) {
                minimalDifference = difference;
                highestLeftSum = leftSum;
                index = i;
            }
        }

        outputWriter.print(values[index]);
        boolean openedParenthesis = false;
        boolean hasLeftChild = index - low > 0;
        if (hasLeftChild) {
            outputWriter.print("(");
            printKuPellaKeSBST(values, outputWriter, prefixSum, low, index - 1);
            openedParenthesis = true;
        }
        if (high - index > 0) {
            if (hasLeftChild) {
                outputWriter.print(",");
            } else {
                outputWriter.print("(");
                openedParenthesis = true;
            }
            printKuPellaKeSBST(values, outputWriter, prefixSum, index + 1, high);
        }

        if (openedParenthesis) {
            outputWriter.print(")");
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
