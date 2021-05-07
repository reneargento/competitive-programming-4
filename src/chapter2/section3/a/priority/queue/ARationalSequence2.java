package chapter2.section3.a.priority.queue;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 06/05/21.
 */
public class ARationalSequence2 {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int dataSetNumber = FastReader.nextInt();
            String[] values = FastReader.next().split("/");
            int numerator = Integer.parseInt(values[0]);
            int denominator = Integer.parseInt(values[1]);

            int sequenceNumber = getSequenceNumber(numerator, denominator);
            outputWriter.printLine(dataSetNumber + " " + sequenceNumber);
        }
        outputWriter.flush();
    }

    private static int getSequenceNumber(int numerator, int denominator) {
        if (numerator == 1 && denominator == 1) {
            return 1;
        }
        if (numerator > denominator) {
            return 2 * getSequenceNumber(numerator - denominator, denominator) + 1;
        } else {
            return 2 * getSequenceNumber(numerator, denominator - numerator);
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
