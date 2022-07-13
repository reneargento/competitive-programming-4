package chapter3.section4.c.involving.sorting.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 12/07/22.
 */
public class DASort {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int setId = FastReader.nextInt();
            int[] values = new int[FastReader.nextInt()];
            for (int i = 0; i < values.length; i++) {
                values[i] = FastReader.nextInt();
            }
            int minimumDeleteAppendOperations = computeMinimumDeleteAppendOperations(values);
            outputWriter.printLine(setId + " " + minimumDeleteAppendOperations);
        }
        outputWriter.flush();
    }

    private static int computeMinimumDeleteAppendOperations(int[] values) {
        boolean[] deleteAppendRequired = new boolean[values.length];
        int currentMinimum = Integer.MAX_VALUE;

        for (int i = values.length - 1; i >= 0; i--) {
            if (values[i] > currentMinimum) {
                deleteAppendRequired[i] = true;
            }
            currentMinimum = Math.min(currentMinimum, values[i]);
        }

        int minimumElementToMove = Integer.MAX_VALUE;
        for (int i = 0; i < values.length; i++) {
            if (deleteAppendRequired[i]) {
                minimumElementToMove = Math.min(minimumElementToMove, values[i]);
            }
            if (values[i] > minimumElementToMove) {
                deleteAppendRequired[i] = true;
            }
        }

        int minimumDeleteAppendOperations = 0;
        for (boolean moveRequired : deleteAppendRequired) {
            if (moveRequired) {
                minimumDeleteAppendOperations++;
            }
        }
        return minimumDeleteAppendOperations;
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
