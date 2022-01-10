package chapter3.section2.i.mathematical.simulation.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 09/01/22.
 */
public class The12NKProblem {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            if (t > 0) {
                outputWriter.printLine();
            }
            int sum = Math.abs(FastReader.nextInt());
            int termNumbers = computeTermNumbers(sum);
            outputWriter.printLine(termNumbers);
        }
        outputWriter.flush();
    }

    private static int computeTermNumbers(int sum) {
        for (int termNumbers = 1; true; termNumbers++) {
            int maxSum = termNumbers * (termNumbers + 1) / 2;
            if (maxSum >= sum
                    && (maxSum - sum) % 2 == 0) {
                return termNumbers;
            }
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
