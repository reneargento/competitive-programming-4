package chapter5.section3.c.finding.prime.factors;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/06/25.
 */
public class PrimeReduction {

    private static class Result {
        int finalPrime;
        int processTimes;

        public Result(int finalPrime, int processTimes) {
            this.finalPrime = finalPrime;
            this.processTimes = processTimes;
        }
    }

    private static final int MAX_VALUE = 10000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int number = FastReader.nextInt();

        Integer[] primeNumbers = eratosthenesSieve();

        while (number != 4) {
            Result result = reducePrime(primeNumbers, number);
            outputWriter.printLine(result.finalPrime + " " + result.processTimes);
            number = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Result reducePrime(Integer[] primeNumbers, int number) {
        int processTimes = 1;

        while (!isPrime(primeNumbers, number)) {
            processTimes++;
            number = primeFactorsSum(number);
        }
        return new Result(number, processTimes);
    }

    private static boolean isPrime(Integer[] primeNumbers, int number) {
        if (number <= MAX_VALUE) {
            return isPrime[number];
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

    private static boolean[] isPrime;

    private static Integer[] eratosthenesSieve() {
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

    private static int primeFactorsSum(int number) {
        int primeFactorsSum = 0;

        for (int i = 2; i * i <= number; i++) {
            while (number % i == 0) {
                primeFactorsSum += i;
                number /= i;
            }
        }

        if (number > 1) {
            primeFactorsSum += number;
        }
        return primeFactorsSum;
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
