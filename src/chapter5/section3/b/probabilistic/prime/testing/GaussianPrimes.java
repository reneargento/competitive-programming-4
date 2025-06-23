package chapter5.section3.b.probabilistic.prime.testing;

import java.io.*;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 11/06/25.
 */
public class GaussianPrimes {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int real = FastReader.nextInt();
            int imaginary = FastReader.nextInt();

            boolean isGaussianPrime = isPrime(real, imaginary);
            outputWriter.printLine(isGaussianPrime ? "P" : "C");
        }
        outputWriter.flush();
    }

    private static boolean isPrime(long real, long imaginary) {
        if (real == 0) {
            return Math.abs(imaginary) % 4 == 3;
        }
        if (imaginary == 0) {
            return Math.abs(real) % 4 == 3;
        }
        if (real + imaginary != 0) {
            long squareSum = real * real + imaginary * imaginary;
            BigInteger squareSumBI = BigInteger.valueOf(squareSum);
            return squareSumBI.isProbablePrime(10);
        }
        return false;
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
