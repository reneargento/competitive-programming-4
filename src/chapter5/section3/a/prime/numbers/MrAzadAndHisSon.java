package chapter5.section3.a.prime.numbers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 30/05/25.
 */
public class MrAzadAndHisSon {

    private static class Result {
        long perfectNumber;
        boolean isPrime;

        public Result(long perfectNumber, boolean isPrime) {
            this.perfectNumber = perfectNumber;
            this.isPrime = isPrime;
        }
    }

    private static boolean[] isPrime;
    private static final int MAX_NUMBER = (int) Math.pow(2, 31);
    private static final int MAX_NUMBER_SQRT = (int) Math.sqrt(MAX_NUMBER);

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int number = FastReader.nextInt();
        Integer[] primeNumbers = eratosthenesSieve();

        while (number > 0) {
            Result result = checkNumber(primeNumbers, number);
            if (result.perfectNumber != -1) {
                outputWriter.printLine(String.format("Perfect: %d!", result.perfectNumber));
            } else {
                if (result.isPrime) {
                    outputWriter.printLine("Given number is prime. But, NO perfect number is available.");
                } else {
                    outputWriter.printLine("Given number is NOT prime! NO perfect number is available.");
                }
            }
            number = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Result checkNumber(Integer[] primeNumbers, int number) {
        long perfectNumber = -1;
        long twoPowerNMinus1 = (long) (Math.pow(2, number) - 1);
        boolean isNumberPrime = isPrime(primeNumbers, number);

        if (isNumberPrime && isPrime(primeNumbers, twoPowerNMinus1)) {
            perfectNumber = (long) (Math.pow(2, number - 1) * twoPowerNMinus1);
        }
        return new Result(perfectNumber, isNumberPrime);
    }

    private static Integer[] eratosthenesSieve() {
        List<Integer> primeNumbers = new ArrayList<>();
        isPrime = new boolean[MAX_NUMBER_SQRT];
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
        if (number < MAX_NUMBER_SQRT) {
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
