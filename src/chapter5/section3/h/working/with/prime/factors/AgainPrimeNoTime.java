package chapter5.section3.h.working.with.prime.factors;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/10/25.
 */
public class AgainPrimeNoTime {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        Integer[] primeNumbers = eratosthenesSieve();

        for (int t = 1; t <= tests; t++) {
            int m = FastReader.nextInt();
            int factorial = FastReader.nextInt();

            int highestPowerOfM = computeHighestPowerOfM(primeNumbers, m, factorial);
            outputWriter.printLine(String.format("Case %d:", t));
            if (highestPowerOfM == -1) {
                outputWriter.printLine("Impossible to divide");
            } else {
                outputWriter.printLine(highestPowerOfM);
            }
        }
        outputWriter.flush();
    }

    private static int computeHighestPowerOfM(Integer[] primeNumbers, int m, int factorial) {
        if (factorial == 1) {
            return 0;
        }
        int highestPowerOfM = Integer.MAX_VALUE;
        boolean divisible = false;

        for (int prime : primeNumbers) {
            if (prime > factorial && prime > m) {
                break;
            }

            int mPrimeDivisorsCount = 0;
            while (m % prime == 0) {
                mPrimeDivisorsCount++;
                m /= prime;
            }

            if (mPrimeDivisorsCount > 0) {
                int nPrimeDivisorsCount = 0;

                for (int i = 2; i <= factorial; i++) {
                    int factorCopy = i;
                    while (factorCopy % prime == 0) {
                        nPrimeDivisorsCount++;
                        factorCopy /= prime;
                    }
                }

                if (nPrimeDivisorsCount >= mPrimeDivisorsCount) {
                    int difference = nPrimeDivisorsCount / mPrimeDivisorsCount;
                    highestPowerOfM = Math.min(highestPowerOfM, difference);
                    divisible = true;
                } else {
                    return -1;
                }
            }
        }

        if (!divisible) {
            return -1;
        }
        return highestPowerOfM;
    }

    private static Integer[] eratosthenesSieve() {
        int maxNumber = 10000;
        List<Integer> primeNumbers = new ArrayList<>();
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
