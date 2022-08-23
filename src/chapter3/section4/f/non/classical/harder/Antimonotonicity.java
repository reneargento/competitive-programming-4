package chapter3.section4.f.non.classical.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 07/08/22.
 */
public class Antimonotonicity {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int sequenceLength = FastReader.nextInt();
            int longestSubsequenceLength = 0;
            int currentNumber = 0;
            boolean shouldIncrease = false;
            boolean isFirst = true;

            for (int i = 0; i < sequenceLength; i++) {
                int value = FastReader.nextInt();
                if (isFirst) {
                    currentNumber = value;
                    longestSubsequenceLength++;
                    isFirst = false;
                } else {
                    if ((shouldIncrease && value > currentNumber)
                            || (!shouldIncrease && value < currentNumber)) {
                        currentNumber = value;
                        shouldIncrease = !shouldIncrease;
                        longestSubsequenceLength++;
                    } else {
                        if ((shouldIncrease && value < currentNumber)
                                || (!shouldIncrease && value > currentNumber)) {
                            currentNumber = value;
                        }
                    }
                }
            }
            outputWriter.printLine(longestSubsequenceLength);
        }
        outputWriter.flush();
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

        private static String getLine() throws IOException {
            return reader.readLine();
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
