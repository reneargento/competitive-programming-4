package chapter5.section6;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 26/03/26.
 */
public class HappyNumber {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int number = FastReader.nextInt();
            outputWriter.print(String.format("Case #%d: %d is ", t, number));
            boolean isHappyNumber = isHappyNumber(number);
            if (isHappyNumber) {
                outputWriter.print("a Happy ");
            } else {
                outputWriter.print("an Unhappy ");
            }
            outputWriter.printLine("number.");
        }
        outputWriter.flush();
    }

    private static boolean isHappyNumber(int number) {
        long tortoise = computeNextNumber(number);
        long hare = computeNextNumber(computeNextNumber(number));

        while (tortoise != hare && hare != 1) {
            tortoise = computeNextNumber(tortoise);
            hare = computeNextNumber(computeNextNumber(hare));
        }
        return hare == 1;
    }

    private static long computeNextNumber(long number) {
        long nextNumber = 0;
        while (number != 0) {
            long digit = number % 10;
            nextNumber += digit * digit;
            number /= 10;
        }
        return nextNumber;
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

        public void flush() {
            writer.flush();
        }
    }
}
