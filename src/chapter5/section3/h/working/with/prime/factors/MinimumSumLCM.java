package chapter5.section3.h.working.with.prime.factors;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/10/25.
 */
public class MinimumSumLCM {

    private static final int MAX_VALUE = 100000;
    private static boolean[] isPrime;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int number = FastReader.nextInt();
        int caseId = 1;
        Integer[] primeNumbers = eratosthenesSieve();

        while (number != 0) {
            long lcm = computeLCM(primeNumbers, number);
            outputWriter.printLine(String.format("Case %d: %d", caseId, lcm));

            caseId++;
            number = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long computeLCM(Integer[] primeNumbers, int number) {
        if (number == 1 || isPrime(primeNumbers, number)) {
            return number + 1L;
        }

        List<Long> factors = computeFactors(number);
        if (factors.size() == 1) {
            return number + 1L;
        }
        long sum = 0;
        for (long factor : factors) {
            sum += factor;
        }
        return sum;
    }

    private static List<Long> computeFactors(long number) {
        List<Long> factors = new ArrayList<>();

        for (long i = 2; i * i <= number; i++) {
            int power = 0;

            while (number % i == 0) {
                power++;
                number /= i;
            }

            if (power > 0) {
                long factor = (long) Math.pow(i, power);
                factors.add(factor);
            }
        }

        // Special case where number is a prime number
        if (number > 1) {
            factors.add(number);
        }
        return factors;
    }

    public static Integer[] eratosthenesSieve() {
        List<Integer> primeNumbers = new ArrayList<>();
        isPrime = new boolean[MAX_VALUE + 1];
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

    private static boolean isPrime(Integer[] primeNumbers, long number) {
        if (number <= MAX_VALUE) {
            return isPrime[(int) number];
        } else {
            int numberSqrt = (int) Math.sqrt(number);

            for (int primeNumber : primeNumbers) {
                if (primeNumber > numberSqrt) {
                    break;
                }
                if (number % primeNumber == 0) {
                    return false;
                }
            }
            return true;
        }
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
