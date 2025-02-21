package chapter5.section2.e.number.systems.or.sequences;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 17/02/25.
 */
public class PermutedArithmeticSequence {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int[] sequence = new int[FastReader.nextInt()];
            for (int i = 0; i < sequence.length; i++) {
                sequence[i] = FastReader.nextInt();
            }

            String sequenceType = analyzeSequence(sequence);
            outputWriter.printLine(sequenceType);
        }
        outputWriter.flush();
    }

    private static String analyzeSequence(int[] sequence) {
        if (isArithmetic(sequence)) {
            return "arithmetic";
        }

        Arrays.sort(sequence);
        if (isArithmetic(sequence)) {
            return "permuted arithmetic";
        }
        return "non-arithmetic";
    }

    private static boolean isArithmetic(int[] sequence) {
        int difference = sequence[1] - sequence[0];

        for (int i = 2; i < sequence.length; i++) {
            if (sequence[i] - sequence[i - 1] != difference) {
                return false;
            }
        }
        return true;
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
