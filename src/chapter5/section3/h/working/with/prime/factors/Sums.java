package chapter5.section3.h.working.with.prime.factors;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 18/09/25.
 */
public class Sums {

    private static class Result {
        int firstTerm;
        int numberOfTerms;

        public Result(int firstTerm, int numberOfTerms) {
            this.firstTerm = firstTerm;
            this.numberOfTerms = numberOfTerms;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int number = FastReader.nextInt();

            Result result = computeSequenceData(number);
            if (result == null) {
                outputWriter.printLine("IMPOSSIBLE");
            } else {
                boolean isFirstTerm = true;
                outputWriter.print(number + " = ");
                for (int i = result.firstTerm; i < result.firstTerm + result.numberOfTerms; i++) {
                    if (isFirstTerm) {
                        isFirstTerm = false;
                    } else {
                        outputWriter.print(" + ");
                    }
                    outputWriter.print(i);
                }
                outputWriter.printLine();
            }
        }
        outputWriter.flush();
    }

    private static Result computeSequenceData(int number) {
        number *= 2;
        int maxNumber = (int) Math.ceil(Math.sqrt(number));

        for (int i = 2; i < maxNumber; i++) {
            if (number % i == 0) {
                int equation = number / i - i + 1;
                if (equation % 2 == 0) {
                    return new Result(equation / 2, i);
                }
            }
        }
        return null;
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
