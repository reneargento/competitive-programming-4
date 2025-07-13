package chapter5.section3.d.functions.involving.prime.factors;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 11/07/25.
 */
public class DivisorsKattis {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int[] primeNumbers = eratosthenesSieve();

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int totalNumbers = Integer.parseInt(data[0]);
            int numbersToChoose = Integer.parseInt(data[1]);

            long uniqueDivisors = countUniqueDivisors(primeNumbers, totalNumbers, numbersToChoose);
            outputWriter.printLine(uniqueDivisors);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static long countUniqueDivisors(int[] primeNumbers, int totalNumbers, int numbersToChoose) {
        int[] primeFactorsFrequency = new int[10001];
        for (int value = totalNumbers - numbersToChoose + 1; value <= totalNumbers; value++) {
            computePrimeFactors(primeFactorsFrequency, primeNumbers, value, 1);
        }
        for (int value = 1; value <= numbersToChoose; value++) {
            computePrimeFactors(primeFactorsFrequency, primeNumbers, value, -1);
        }

        long uniqueDivisors = 1;
        for (int frequency : primeFactorsFrequency) {
            if (frequency == 0) {
                continue;
            }
            uniqueDivisors *= frequency + 1;
        }
        return uniqueDivisors;
    }

    private static void computePrimeFactors(int[] primeFactorsFrequency, int[] primeNumbers, int number,
                                            int deltaValue) {
        int maxValueToCheck = (int) Math.sqrt(number);

        for (int prime : primeNumbers) {
            if (prime > maxValueToCheck) {
                break;
            }
            while (number % prime == 0) {
                primeFactorsFrequency[prime] += deltaValue;
                number /= prime;
            }
        }

        if (number > 1) {
            primeFactorsFrequency[number] += deltaValue;
        }
    }

    private static int[] eratosthenesSieve() {
        List<Integer> primeNumbers = new ArrayList<>();
        int maxValue = 10000;
        boolean[] isPrime = new boolean[maxValue];
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
        Integer[] primesInteger = primeNumbers.toArray(new Integer[0]);
        int[] primes = new int[primesInteger.length];
        for (int i = 0; i < primes.length; i++) {
            primes[i] = primesInteger[i];
        }
        return primes;
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
