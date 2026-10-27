package chapter6.section7.palindrome.generating;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 21/10/2026.
 */
public class ReverseAndAdd {

    private static class Result {
        int minimumIterations;
        String result;

        public Result(int minimumIterations, String result) {
            this.minimumIterations = minimumIterations;
            this.result = result;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String number = FastReader.getLine();
            Result result = reverseAdd(number);
            outputWriter.printLine(result.minimumIterations + " " + result.result);
        }
        outputWriter.flush();
    }

    private static Result reverseAdd(String number) {
        String result = number;
        int minimumIterations = 0;

        while (!isPalindrome(number)) {
            minimumIterations++;
            long numberLong = Long.parseLong(number);
            String reverseNumber = new StringBuilder(number).reverse().toString();
            long reverseNumberLong = Long.parseLong(reverseNumber);

            number = String.valueOf(numberLong + reverseNumberLong);
            result = number;
        }
        return new Result(minimumIterations, result);
    }

    private static boolean isPalindrome(String number) {
        for (int i = 0; i < number.length() / 2; i++) {
            if (number.charAt(i) != number.charAt(number.length() - 1 - i)) {
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