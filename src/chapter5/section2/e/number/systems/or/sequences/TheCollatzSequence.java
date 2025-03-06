package chapter5.section2.e.number.systems.or.sequences;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 22/02/25.
 */
public class TheCollatzSequence {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int initialValue = FastReader.nextInt();
        int limitingValue = FastReader.nextInt();
        int caseNumber = 1;

        while (initialValue >= 0 || limitingValue >= 0) {
            int sequenceLength = computeSequenceLength(initialValue, limitingValue);
            outputWriter.printLine(String.format("Case %d: A = %d, limit = %d, number of terms = %d",
                    caseNumber, initialValue, limitingValue, sequenceLength));

            caseNumber++;
            initialValue = FastReader.nextInt();
            limitingValue = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeSequenceLength(int initialValue, int limitingValue) {
        int sequenceLength = 0;
        long value = initialValue;

        while (value != 1 && value <= limitingValue) {
            sequenceLength++;

            if (value % 2 == 0) {
                value /= 2;
            } else {
                value = value * 3 + 1;
            }
        }

        if (value == 1) {
            sequenceLength++;
        }
        return sequenceLength;
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

        public void flush() {
            writer.flush();
        }
    }
}
