package chapter5.section3.h.working.with.prime.factors;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 17/09/25.
 */
public class SigmaFunction {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        long number = FastReader.nextLong();
        while (number != 0) {
            long numbersWithEvenSigma = countNumbersWithEvenSigma(number);
            outputWriter.printLine(numbersWithEvenSigma);
            number = FastReader.nextLong();
        }
        outputWriter.flush();
    }

    private static long countNumbersWithEvenSigma(long number) {
        long sqrt = (long) Math.sqrt(number);
        long sqrtHalfNumber = (long) Math.sqrt(number / 2.0);
        return number - sqrt - sqrtHalfNumber;
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

        private static long nextLong() throws IOException {
            return Long.parseLong(next());
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
