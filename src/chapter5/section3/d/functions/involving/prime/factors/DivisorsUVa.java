package chapter5.section3.d.functions.involving.prime.factors;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 09/07/25.
 */
public class DivisorsUVa {

    private static class Result {
        int numberWithMostDivisors;
        int divisorsCount;

        public Result(int numberWithMostDivisors, int divisorsCount) {
            this.numberWithMostDivisors = numberWithMostDivisors;
            this.divisorsCount = divisorsCount;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int low = FastReader.nextInt();
            int high = FastReader.nextInt();

            Result result = analyzeRange(low, high);
            outputWriter.printLine(String.format("Between %d and %d, %d has a maximum of %d divisors.",
                    low, high, result.numberWithMostDivisors, result.divisorsCount));
        }
        outputWriter.flush();
    }

    private static Result analyzeRange(int low, int high) {
        int numberWithMostDivisors = 0;
        int highestDivisorsCount = 0;

        for (int number = low; number <= high; number++) {
            int divisorsCount = countDivisors(number);

            if (divisorsCount > highestDivisorsCount) {
                highestDivisorsCount = divisorsCount;
                numberWithMostDivisors = number;
            }
        }
        return new Result(numberWithMostDivisors, highestDivisorsCount);
    }

    private static int countDivisors(int number) {
        int divisorsCount = 0;
        int upperLimit = (int) Math.sqrt(number);

        for (int i = 1; i <= upperLimit; i++) {
            if (number % i == 0) {
                divisorsCount++;

                if (i != number / i) {
                    divisorsCount++;
                }
            }
        }
        return divisorsCount;
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
