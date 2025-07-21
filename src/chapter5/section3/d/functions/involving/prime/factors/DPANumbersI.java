package chapter5.section3.d.functions.involving.prime.factors;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 17/07/25.
 */
public class DPANumbersI {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int number = FastReader.nextInt();
            String category = computeCategory(number);
            outputWriter.printLine(category);
        }
        outputWriter.flush();
    }

    private static String computeCategory(int number) {
        int factorsSum = 0;
        int upperLimit = (int) Math.sqrt(number);

        for (int i = 1; i <= upperLimit; i++) {
            if (number % i == 0) {
                factorsSum += i;

                int complement = number / i;
                if (i != complement
                        && complement != number) {
                    factorsSum += complement;
                }
            }
        }

        if (factorsSum < number) {
            return "deficient";
        } else if (factorsSum == number) {
            return "perfect";
        }
        return "abundant";
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
