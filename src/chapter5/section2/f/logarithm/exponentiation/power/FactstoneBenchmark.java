package chapter5.section2.f.logarithm.exponentiation.power;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 20/03/25.
 */
public class FactstoneBenchmark {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int year = FastReader.nextInt();
        while (year != 0) {
            int rating = computeRating(year);
            outputWriter.printLine(rating);
            year = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeRating(int year) {
        int yearsRange = (year - 1960) / 10;
        int bitsAvailable = (int) Math.pow(2, yearsRange + 2);

        double logNFactorial = log2(2);
        int factorialNumber = 3;
        while (true) {
            double nextLogNFactorial = logNFactorial + log2(factorialNumber);

            if (nextLogNFactorial < bitsAvailable) {
                logNFactorial = nextLogNFactorial;
            } else {
                return factorialNumber - 1;
            }
            factorialNumber++;
        }
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
