package chapter5.section3.h.working.with.prime.factors;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 04/10/25.
 */
public class Benefit {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        Integer[] primeNumbers = eratosthenesSieve();

        for (int t = 0; t < tests; t++) {
            int number1 = FastReader.nextInt();
            int lcm = FastReader.nextInt();

            int number2 = computeNumber2(number1, lcm, primeNumbers);
            if (number2 == -1) {
                outputWriter.printLine("NO SOLUTION");
            } else {
                outputWriter.printLine(number2);
            }
        }
        outputWriter.flush();
    }

    private static int computeNumber2(int number1, int lcm, Integer[] primeNumbers) {
        if (lcm % (double) number1 != 0) {
            return -1;
        }

        int number2 = 1;
        for (int prime : primeNumbers) {
            if (prime > lcm && prime > number1) {
                break;
            }

            int powerInNumber1 = 0;
            while (number1 % prime == 0) {
                powerInNumber1++;
                number1 /= prime;
            }

            int powerInLcm = 0;
            while (lcm % prime == 0) {
                lcm /= prime;
                powerInLcm++;
            }

            int difference = powerInLcm - powerInNumber1;
            if (difference > 0) {
                number2 *= (int) Math.pow(prime, powerInLcm);
            }
        }

        if (lcm > 1 && number1 == 1) {
            number2 *= lcm;
        }
        return number2;
    }

    private static Integer[] eratosthenesSieve() {
        List<Integer> primeNumbers = new ArrayList<>();
        boolean[] isPrime = new boolean[10000];
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
