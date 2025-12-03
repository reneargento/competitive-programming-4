package chapter5.section4.a.fibonacci.numbers;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 01/12/25.
 */
public class FibonacciSum {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        int[] pisanoPeriod = computePisanoPeriod();
        long completePeriodSum = computePartialSum(pisanoPeriod, 0, pisanoPeriod.length - 1);

        for (int t = 0; t < tests; t++) {
            long startIndex = FastReader.nextLong() - 1;
            long endIndex = FastReader.nextLong() - 1;

            long sum = computeSum(pisanoPeriod, completePeriodSum, startIndex, endIndex);
            outputWriter.printLine(sum);
        }
        outputWriter.flush();
    }

    private static int[] computePisanoPeriod() {
        int[] pisanoPeriod = new int[300];
        pisanoPeriod[0] = 1;
        pisanoPeriod[1] = 1;

        for (int i = 2; i < pisanoPeriod.length; i++) {
            pisanoPeriod[i] = pisanoPeriod[i - 1] + pisanoPeriod[i - 2];
            pisanoPeriod[i] %= 100;
        }
        return pisanoPeriod;
    }

    private static long computeSum(int[] pisanoPeriod, long completePeriodSum, long startIndex, long endIndex) {
        long totalNumbers = endIndex - startIndex;
        long totalPeriods = totalNumbers / 300;
        long totalPeriodsSum = completePeriodSum * totalPeriods;

        int adjustedStartIndex = (int) (startIndex % 300);
        int adjustedEndIndex = (int) (endIndex % 300);

        if (adjustedStartIndex <= adjustedEndIndex) {
            return totalPeriodsSum + computePartialSum(pisanoPeriod, adjustedStartIndex, adjustedEndIndex);
        } else {
            long startSum = computePartialSum(pisanoPeriod, adjustedStartIndex, pisanoPeriod.length - 1);
            long endSum = computePartialSum(pisanoPeriod, 0, adjustedEndIndex);
            return totalPeriodsSum + startSum + endSum;
        }
    }

    private static long computePartialSum(int[] pisanoPeriod, int startIndex, int endIndex) {
        long sum = 0;
        for (int i = startIndex; i <= endIndex; i++) {
            sum += pisanoPeriod[i];
        }
        return sum;
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

        private static long nextLong() throws IOException {
            return Long.parseLong(next());
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
