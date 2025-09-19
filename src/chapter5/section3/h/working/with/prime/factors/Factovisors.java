package chapter5.section3.h.working.with.prime.factors;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 13/09/25.
 */
public class Factovisors {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();
        Integer[] primeNumbers = eratosthenesSieve();

        while (line != null) {
            String[] data = line.split(" ");
            int n = Integer.parseInt(data[0]);
            int m = Integer.parseInt(data[1]);

            boolean result = checkDivision(primeNumbers, n, m);
            if (result) {
                outputWriter.printLine(String.format("%d divides %d!", m, n));
            } else {
                outputWriter.printLine(String.format("%d does not divide %d!", m, n));
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static boolean checkDivision(Integer[] primeNumbers, int n, int m) {
        if (m == 0) {
            return false;
        }
        for (int prime : primeNumbers) {
            if (prime > m) {
                break;
            }

            int mPrimeDivisorsCount = 0;
            while (m > 0 && m % prime == 0) {
                mPrimeDivisorsCount++;
                m /= prime;
            }

            if (mPrimeDivisorsCount > 0) {
                if (!checkDivisorsWithPrime(mPrimeDivisorsCount, prime, n)) {
                    return false;
                }
            }
        }

        if (m > 1) {
            return checkDivisorsWithPrime(1, m, n);
        }
        return true;
    }

    private static boolean checkDivisorsWithPrime(int mPrimeDivisorsCount, int prime, int number) {
        int nPrimeDivisorsSum = 0;
        while (number > 0) {
            nPrimeDivisorsSum += number / prime;
            number /= prime;
        }
        return nPrimeDivisorsSum >= mPrimeDivisorsCount;
    }

    private static Integer[] eratosthenesSieve() {
        int maxNumber = 1000000;
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
