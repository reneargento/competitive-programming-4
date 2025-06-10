package chapter5.section3.a.prime.numbers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 29/05/25.
 */
public class GoldbachAndEuler {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        int[] primeNumbers = eratosthenesSieve();

        while (line != null) {
            int number = Integer.parseInt(line);
            int prime = compute2Sum(primeNumbers, number);

            if (prime == -1) {
                outputWriter.printLine(number + " is not the sum of two primes!");
            } else {
                int otherPrime = number - prime;
                outputWriter.printLine(String.format("%d is the sum of %d and %d.", number, prime, otherPrime));
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int compute2Sum(int[] primeNumbers, int number) {
        if (number >= 2 && number % 2 == 1) {
            int otherPrimeNumber = number - 2;
            if (isPrime(primeNumbers, otherPrimeNumber)) {
                return 2;
            } else {
                return -1;
            }
        }

        for (int candidateNumber = number / 2 - 1; candidateNumber >= 0; candidateNumber--) {
            int otherPrimeNumber = number - candidateNumber;

            if (isPrime(primeNumbers, candidateNumber) && isPrime(primeNumbers, otherPrimeNumber)) {
                return candidateNumber;
            }
        }
        return -1;
    }

    private static final long MAX_VALUE = 10001;

    private static int[] eratosthenesSieve() {
        List<Integer> primeNumbersList = new ArrayList<>();
        boolean[] isPrime = new boolean[(int) MAX_VALUE];
        for (int i = 2; i < isPrime.length; i++) {
            isPrime[i] = true;
        }

        for (long i = 2; i < MAX_VALUE; i++) {
            if (isPrime[(int) i]) {
                for (long j = i * i; j < isPrime.length; j += i) {
                    isPrime[(int) j] = false;
                }
                primeNumbersList.add((int) i);
            }
        }

        int[] primeNumbers = new int[primeNumbersList.size()];
        for (int i = 0; i < primeNumbersList.size(); i++) {
            primeNumbers[i] = primeNumbersList.get(i);
        }
        return primeNumbers;
    }

    private static boolean isPrime(int[] primeNumbers, int number) {
        if (number < 2) {
            return false;
        }
        int sqrtN = (int) Math.sqrt(number);

        for (int primeNumber : primeNumbers) {
            if (primeNumber > sqrtN) {
                break;
            }
            if (number % primeNumber == 0) {
                return false;
            }
        }
        return true;
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
