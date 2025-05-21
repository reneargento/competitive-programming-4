package chapter5.section3.a.prime.numbers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 19/05/25.
 */
public class PrimeGap {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int number = FastReader.nextInt();
        Integer[] primes = eratosthenesSieve();

        while (number != 0) {
            int primeGap = computePrimeGap(primes, number);
            outputWriter.printLine(primeGap);
            number = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computePrimeGap(Integer[] primes, int number) {
        if (isPrime[number]) {
            return 0;
        }

        int lowIndex = binarySearch(primes, number, false);
        int highIndex = binarySearch(primes, number, true);
        return primes[highIndex] - primes[lowIndex];
    }

    private static int binarySearch(Integer[] values, int target, boolean isLowerBound) {
        int low = 0;
        int high = values.length - 1;
        int result = -1;

        while (low <= high) {
            int middle = low + (high - low) / 2;

            if (isLowerBound) {
                if (values[middle] < target) {
                    low = middle + 1;
                } else {
                    result = middle;
                    high = middle - 1;
                }
            } else {
                if (values[middle] > target) {
                    high = middle - 1;
                } else {
                    result = middle;
                    low = middle + 1;
                }
            }
        }
        return result;
    }

    private static boolean[] isPrime;
    private static final int MAX_VALUE = 1299709;

    private static Integer[] eratosthenesSieve() {
        List<Integer> primeNumbers = new ArrayList<>();
        isPrime = new boolean[MAX_VALUE + 1];

        for (int i = 2; i < isPrime.length; i++) {
            isPrime[i] = true;
        }

        for (long i = 2; i <= MAX_VALUE; i++) {
            if (isPrime[(int) i]) {
                for (long j = i * i; j < isPrime.length; j += i) {
                    isPrime[(int) j] = false;
                }
                primeNumbers.add((int) i);
            }
        }
        return primeNumbers.toArray(new Integer[0]);
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
