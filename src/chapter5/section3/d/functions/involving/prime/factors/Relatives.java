package chapter5.section3.d.functions.involving.prime.factors;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 11/07/25.
 */
public class Relatives {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int number = FastReader.nextInt();
        List<Integer> primes = eratosthenesSieve();

        while (number != 0) {
            long euluerPhi = eulerPhi(primes, number);
            outputWriter.printLine(euluerPhi);
            number = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long eulerPhi(List<Integer> primes, long number) {
        if (number == 1) {
            return 0;
        }
        Iterator<Integer> primesIterator = primes.iterator();
        long currentPrime = primesIterator.next();
        long eulerPhi = number;

        while (number != 1 && currentPrime * currentPrime <= number) {
            if (number % currentPrime == 0) {
                eulerPhi -= eulerPhi / currentPrime;
            }

            while (number % currentPrime == 0) {
                number /= currentPrime;
            }
            currentPrime = primesIterator.next();
        }

        if (number != 1) {
            eulerPhi -= eulerPhi / number;
        }
        return eulerPhi;
    }

    private static List<Integer> eratosthenesSieve() {
        List<Integer> primeNumbers = new ArrayList<>();
        boolean[] isPrime = new boolean[100001];
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
        return primeNumbers;
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
