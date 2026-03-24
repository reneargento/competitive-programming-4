package chapter5.section6;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by Rene Argento on 22/03/26.
 */
public class FibonacciCycles {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        BigInteger[] fibonacci = computeFibonacci();

        for (int t = 0; t < tests; t++) {
            BigInteger modulo = BigInteger.valueOf(FastReader.nextInt());
            int firstRepeatIndex = computeFirstRepeatIndex(fibonacci, modulo);
            outputWriter.printLine(firstRepeatIndex);
        }
        outputWriter.flush();
    }

    private static int computeFirstRepeatIndex(BigInteger[] fibonacci, BigInteger modulo) {
        Map<BigInteger, Integer> valuesSeen = new HashMap<>();
        for (int i = 2; i < fibonacci.length; i++) {
            BigInteger value = fibonacci[i].mod(modulo);
            if (valuesSeen.containsKey(value)) {
                return valuesSeen.get(value);
            }
            valuesSeen.put(value, i);
        }
        return -1;
    }

    private static BigInteger[] computeFibonacci() {
        BigInteger[] fibonacci = new BigInteger[200];
        fibonacci[0] = BigInteger.ONE;
        fibonacci[1] = BigInteger.ONE;
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
