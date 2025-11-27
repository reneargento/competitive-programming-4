package chapter5.section4.a.fibonacci.numbers;

import java.io.*;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 25/11/25.
 */
public class HowManyFibs {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        BigInteger[] fibonacci = computeFibonacci();

        BigInteger low = new BigInteger(FastReader.next());
        BigInteger high = new BigInteger(FastReader.next());

        while (low.compareTo(BigInteger.ZERO) != 0 || high.compareTo(BigInteger.ZERO) != 0) {
            int fibonacciInRange = countFibonacciInRange(fibonacci, low, high);
            outputWriter.printLine(fibonacciInRange);

            low = new BigInteger(FastReader.next());
            high = new BigInteger(FastReader.next());
        }
        outputWriter.flush();
    }

    private static int countFibonacciInRange(BigInteger[] fibonacci, BigInteger low, BigInteger high) {
        int fibonacciInRange = 0;
        for (BigInteger value : fibonacci) {
            if (low.compareTo(value) <= 0 && value.compareTo(high) <= 0) {
                fibonacciInRange++;
            }
        }
        return fibonacciInRange;
    }

    private static BigInteger[] computeFibonacci() {
        BigInteger[] fibonacci = new BigInteger[500];
        fibonacci[0] = BigInteger.ONE;
        fibonacci[1] = new BigInteger("2");

        for (int i = 2; i < fibonacci.length; i++) {
            fibonacci[i] = fibonacci[i - 1].add(fibonacci[i - 2]);
        }
        return fibonacci;
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
