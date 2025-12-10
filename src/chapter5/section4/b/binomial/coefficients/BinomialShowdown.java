package chapter5.section4.b.binomial.coefficients;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 07/12/25.
 */
public class BinomialShowdown {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int n = FastReader.nextInt();
        int k = FastReader.nextInt();

        while (n != 0 || k != 0) {
            long binomialCoefficient = binomialCoefficient(n, k);
            outputWriter.printLine(binomialCoefficient);

            n = FastReader.nextInt();
            k = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long binomialCoefficient(int totalNumbers, int numbersToChoose) {
        long result = 1;
        long denominator = 2;

        long minNumerator = Math.max(totalNumbers - numbersToChoose, numbersToChoose) + 1;
        long maxDenominator = Math.min(totalNumbers - numbersToChoose, numbersToChoose);

        for (long numerator = minNumerator; numerator <= totalNumbers; numerator++) {
            while (denominator <= maxDenominator) {
                if (result % denominator == 0) {
                    result /= denominator;
                } else {
                    break;
                }
                denominator++;
            }
            result *= numerator;
        }

        while (denominator <= maxDenominator) {
            result /= denominator;
            denominator++;
        }
        return result;
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
