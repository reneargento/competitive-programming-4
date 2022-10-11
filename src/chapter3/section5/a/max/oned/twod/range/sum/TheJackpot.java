package chapter3.section5.a.max.oned.twod.range.sum;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 21/08/22.
 */
public class TheJackpot {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int sequenceLength = FastReader.nextInt();

        while (sequenceLength != 0) {
            long maxSum = 0;
            long currentSum = 0;

            for (int i = 0; i < sequenceLength; i++) {
                int value = FastReader.nextInt();
                currentSum += value;
                if (currentSum > 0) {
                    maxSum = Math.max(maxSum, currentSum);
                } else {
                    currentSum = 0;
                }
            }

            if (maxSum > 0) {
                outputWriter.printLine(String.format("The maximum winning streak is %d.", maxSum));
            } else {
                outputWriter.printLine("Losing streak.");
            }
            sequenceLength = FastReader.nextInt();
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