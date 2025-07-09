package chapter5.section3.c.finding.prime.factors;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 08/07/25.
 */
public class FactoringLargeNumbers {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        Integer[] primes = eratosthenesSieve();

        long number = FastReader.nextLong();
        while (number > 0) {
            List<Long> factors = factorizeNumber(primes, number);
            for (Long factor : factors) {
                outputWriter.printLine("    " + factor);
            }
            outputWriter.printLine();
            number = FastReader.nextLong();
        }
        outputWriter.flush();
    }

    private static List<Long> factorizeNumber(Integer[] primes, long number) {
        List<Long> factors = new ArrayList<>();
        for (Integer prime : primes) {
            while (number % prime == 0) {
                factors.add((long) prime);
                number /= prime;
            }
        }

        if (number != 1) {
            factors.add(number);
        }
        return factors;
    }

    private static Integer[] eratosthenesSieve() {
        List<Integer> primeNumbers = new ArrayList<>();
        int maxNumber = 1000001;
        boolean[] isPrime = new boolean[maxNumber];
        for (int i = 2; i < isPrime.length; i++) {
            isPrime[i] = true;
        }

        for (long i = 2; i < isPrime.length; i++) {
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
