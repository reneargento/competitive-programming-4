package chapter3.section2.h.mathematical.simulation.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 20/12/21.
 */
public class AckermannFunctions {

    private static class Result {
        int value;
        int sequenceLength;

        public Result(int value, int sequenceLength) {
            this.value = value;
            this.sequenceLength = sequenceLength;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int start = FastReader.nextInt();
        int end = FastReader.nextInt();

        while (start != 0 || end != 0) {
            int min = Math.min(start, end);
            int max = Math.max(start, end);

            Result result = computeLongestSequence(min, max);
            outputWriter.printLine(String.format("Between %d and %d, %d generates the longest sequence of %d values.",
                    min, max, result.value, result.sequenceLength));

            start = FastReader.nextInt();
            end = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Result computeLongestSequence(int start, int end) {
        int maxLength = 0;
        int bestValue = 0;

        for (int value = start; value <= end; value++) {
            int length = computeSequenceLength(value);
            if (length > maxLength) {
                maxLength = length;
                bestValue = value;
            }
        }
        return new Result(bestValue, maxLength);
    }

    private static int computeSequenceLength(long number) {
        if (number == 1) {
            return 3;
        }

        int length = 0;

        while (number != 1) {
            if (number % 2 == 0) {
                number /= 2;
            } else {
                number = number * 3 + 1;
            }
            length++;
        }
        return length;
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
