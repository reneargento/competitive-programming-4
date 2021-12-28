package chapter3.section2.h.mathematical.simulation.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 24/12/21.
 */
public class CodeRefactoring {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int number = FastReader.nextInt();
            factorNumber(number, t, outputWriter);
        }
        outputWriter.flush();
    }

    private static void factorNumber(int number, int caseId, OutputWriter outputWriter) {
        outputWriter.print(String.format("Case #%d: %d =", caseId, number));
        int maxValue = (int) Math.sqrt(number);
        int factorsFound = 0;

        for (int value = 2; value <= maxValue; value++) {
            if (number % value == 0) {
                int result = number / value;
                outputWriter.print(String.format(" %d * %d", value, result));

                factorsFound++;
                if (factorsFound == 1) {
                    outputWriter.print(" =");
                } else {
                    outputWriter.printLine();
                    return;
                }
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
