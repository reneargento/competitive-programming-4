package chapter5.section3.a.prime.numbers;

import java.io.*;
import java.util.BitSet;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 21/05/25.
 */
public class PrimeSieve {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int n = FastReader.nextInt();
        int queries = FastReader.nextInt();

        int primeNumbersCount = eratosthenesSieve(n);
        outputWriter.printLine(primeNumbersCount);

        for (int q = 0; q < queries; q++) {
            int x = FastReader.nextInt();
            if (isPrime.get(x)) {
                outputWriter.printLine("1");
            } else {
                outputWriter.printLine("0");
            }
        }
        outputWriter.flush();
    }

    private static BitSet isPrime;
    private static final int MAX_VALUE = 100000000;

    private static int eratosthenesSieve(long number) {
        int primeNumbersCount = 0;
        isPrime = new BitSet(MAX_VALUE + 1);

        for (int i = 2; i < isPrime.size(); i++) {
            isPrime.set(i);
        }

        for (long i = 2; i <= number; i++) {
            if (isPrime.get((int) i)) {
                for (long j = i * i; j < isPrime.size(); j += i) {
                    isPrime.clear((int) j);
                }
                primeNumbersCount++;
            }
        }
        return primeNumbersCount;
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
