package chapter5.section3.d.functions.involving.prime.factors;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Rene Argento on 14/07/25.
 */
public class NumberTheory {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        List<Integer> primes = eratosthenesSieve();

        String line = FastReader.getLine();
        while (line != null) {
            int number = Integer.parseInt(line);
            long eulerPhi = eulerPhi(primes, number);
            int factors = countFactors(primes, number);

            long result = number - eulerPhi - factors + 1;
            outputWriter.printLine(result);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static long eulerPhi(List<Integer> primes, long number) {
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

    private static int countFactors(List<Integer> primeNumbers, long number) {
        int factors = 1;

        for (int primeNumber : primeNumbers) {
            int sqrtNumber = (int) Math.sqrt(number);
            if (primeNumber > sqrtNumber) {
                break;
            }

            int power = 1;
            while (number % primeNumber == 0) {
                power++;
                number /= primeNumber;
            }
            factors *= power;
        }

        if (number > 1) {
            factors *= 2;
        }
        return factors;
    }

    private static List<Integer> eratosthenesSieve() {
        List<Integer> primeNumbers = new ArrayList<>();
        int maxNumber = 100000;
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
        return primeNumbers;
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
        }

        private static String getLine() throws IOException {
            return reader.readLine();
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
