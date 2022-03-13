package chapter3.section3.a.binary.search;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 12/03/22.
 */
public class OutOfSorts {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        long[] sequence = new long[FastReader.nextInt()];
        int mod = FastReader.nextInt();
        long a = FastReader.nextInt();
        int c = FastReader.nextInt();
        long previousElement = FastReader.nextInt();

        for (int i = 0; i < sequence.length; i++) {
            long element = (a * previousElement + c) % mod;
            sequence[i] = element;
            previousElement = element;
        }

        int valuesFound = countValuesFound(sequence);
        outputWriter.printLine(valuesFound);
        outputWriter.flush();
    }

    private static int countValuesFound(long[] sequence) {
        int valuesFound = 0;

        for (long value : sequence) {
            int low = 0;
            int high = sequence.length - 1;
            boolean found = false;

            while (low <= high) {
                int middle = low + (high - low) / 2;
                if (sequence[middle] < value) {
                    low = middle + 1;
                } else if (sequence[middle] > value) {
                    high = middle - 1;
                } else {
                    found = true;
                    break;
                }
            }

            if (found) {
                valuesFound++;
            }
        }
        return valuesFound;
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
