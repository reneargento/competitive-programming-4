package chapter3.section2.i.mathematical.simulation.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 04/01/22.
 */
public class ConsecutiveIntegers {

    private static class Result {
        long startNumber;
        long endNumber;

        public Result(long startNumber, long endNumber) {
            this.startNumber = startNumber;
            this.endNumber = endNumber;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int sum = FastReader.nextInt();

        while (sum != -1) {
            Result consecutiveIntegers = computeConsecutiveIntegers(sum);
            outputWriter.printLine(String.format("%d = %d + ... + %d", sum,
                    consecutiveIntegers.startNumber, consecutiveIntegers.endNumber));

            sum = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    // Based on https://byjus.com/sum-of-arithmetic-sequence-formula/
    // S = n / 2 * (2 * a + (n - 1) * d
    // sum = sequenceLength / 2 * (2 * startNumber + (sequenceLength - 1) * d) In this problem, d = 1
    // 2 * sum = sequenceLength * (2 * startNumber + sequenceLength - 1)
    // startNumber = (2 * sum + sequenceLength - sequenceLength^2) / 2 * sequenceLength
    private static Result computeConsecutiveIntegers(long sum) {
        long upperBound = (long) Math.sqrt(2 * sum);

        for (long sequenceLength = upperBound; sequenceLength >= 1; sequenceLength--) {
            long dividend = 2 * sum + sequenceLength - (sequenceLength * sequenceLength);
            long divisor = 2 * sequenceLength;

            if (dividend % divisor == 0) {
                long startNumber = dividend / divisor;
                long endNumber = startNumber + sequenceLength - 1;
                return new Result(startNumber, endNumber);
            }
        }
        return null;
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
