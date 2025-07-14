package chapter5.section3.d.functions.involving.prime.factors;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 12/07/25.
 */
public class FactorialFactors {

    private static final int MAX_VALUE = 1000001;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int[] maxFactorsPrefixSum = computeMaxFactorsPrefixSum();

        String line = FastReader.getLine();
        while (line != null) {
            int factorial = Integer.parseInt(line);
            int maxFactors = maxFactorsPrefixSum[factorial];
            outputWriter.printLine(maxFactors);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int[] computeMaxFactorsPrefixSum() {
        int[] prefixSum = new int[MAX_VALUE];
        int[] primeFactors = computePrimeFactors();

        prefixSum[0] = primeFactors[0];
        for (int i = 1; i < prefixSum.length; i++) {
            prefixSum[i] += prefixSum[i - 1] + primeFactors[i];
        }
        return prefixSum;
    }

    private static int[] computePrimeFactors() {
        int[] primeFactors = new int[MAX_VALUE];
        Integer[] primes = eratosthenesSieve();

        for (int number = 1; number < primeFactors.length; number++) {
            primeFactors[number] = computePrimeFactors(primes, number);
        }
        return primeFactors;
    }

    private static int computePrimeFactors(Integer[] primes, int number) {
        int factors = 0;
        int maxNumber = (int) Math.sqrt(number);

        for (Integer prime : primes) {
            if (prime > maxNumber) {
                break;
            }
            while (number % prime == 0) {
                factors++;
                number /= prime;
            }
        }

        if (number > 1) {
            factors++;
        }
        return factors;
    }

    private static Integer[] eratosthenesSieve() {
        List<Integer> primeNumbers = new ArrayList<>();
        boolean[] isPrime = new boolean[MAX_VALUE];
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
