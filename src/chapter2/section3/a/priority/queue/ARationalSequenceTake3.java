package chapter2.section3.a.priority.queue;

import java.io.*;
import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 06/05/21.
 */
public class ARationalSequenceTake3 {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int dataSetNumber = FastReader.nextInt();
            int sequenceNumber = FastReader.nextInt();

            LinkedList<Integer> sequence = getSequence(sequenceNumber);
            String element = getElement(sequence);
            outputWriter.printLine(dataSetNumber + " " + element);
        }
        outputWriter.flush();
    }

    private static LinkedList<Integer> getSequence(int sequenceNumber) {
        LinkedList<Integer> sequence = new LinkedList<>();

        while (sequenceNumber != 1) {
            int child = sequenceNumber;
            sequenceNumber /= 2;

            if (sequenceNumber * 2 == child) {
                sequence.addFirst(0);
            } else {
                sequence.addFirst(1);
            }
        }
        return sequence;
    }

    private static String getElement(LinkedList<Integer> sequence) {
        long nominator = 1;
        long denominator = 1;

        for (int move : sequence) {
            if (move == 0) {
                denominator = nominator + denominator;
            } else {
                nominator = nominator + denominator;
            }
        }
        return nominator + "/" + denominator;
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
