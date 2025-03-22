package chapter5.section2.f.logarithm.exponentiation.power;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 20/03/25.
 */
public class Cokolada {

    private static class Result {
        int smallestBarSize;
        int smallestNumberOfBreaks;

        public Result(int smallestBarSize, int smallestNumberOfBreaks) {
            this.smallestBarSize = smallestBarSize;
            this.smallestNumberOfBreaks = smallestNumberOfBreaks;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int requiredSquares = FastReader.nextInt();
        Result result = breakBars(requiredSquares);
        outputWriter.printLine(result.smallestBarSize + " " + result.smallestNumberOfBreaks);

        outputWriter.flush();
    }

    private static Result breakBars(int requiredSquares) {
        int log2Ceil = (int) Math.ceil(log2(requiredSquares));
        int smallestBarSize = (int) Math.pow(2, log2Ceil);
        int smallestNumberOfBreaks = computeBreaks(smallestBarSize, requiredSquares);
        return new Result(smallestBarSize, smallestNumberOfBreaks);
    }

    private static int computeBreaks(int currentSize, int targetSize) {
        if (targetSize == currentSize || targetSize == 0) {
            return 0;
        }

        int newSize = currentSize / 2;
        if (newSize <= targetSize) {
            targetSize -= newSize;
        }
        return 1 + computeBreaks(newSize, targetSize);
    }

    private static double log2(int number) {
        return Math.log(number) / Math.log(2);
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
