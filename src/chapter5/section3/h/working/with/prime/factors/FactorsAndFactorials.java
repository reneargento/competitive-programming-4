package chapter5.section3.h.working.with.prime.factors;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/09/25.
 */
public class FactorsAndFactorials {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        boolean[] isPrime = new boolean[101];
        Integer[] primeNumbers = eratosthenesSieve(isPrime);
        int[][] primesCount = computePrimesCount(primeNumbers);

        int number = FastReader.nextInt();
        while (number != 0) {
            outputWriter.print(String.format("%3d! =", number));
            int lastIndex = getLastIndex(primesCount, number);
            int countsPrinted = 0;
            for (int i = 0; i <= lastIndex; i++) {
                if (!isPrime[i]) {
                    continue;
                }
                outputWriter.print(String.format("%3d", primesCount[number][i]));
                countsPrinted++;

                if (i != lastIndex && countsPrinted % 15 == 0) {
                    outputWriter.printLine();
                    outputWriter.print(String.format("%6s", " "));
                }
            }
            outputWriter.printLine();
            number = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int[][] computePrimesCount(Integer[] primeNumbers) {
        int[][] primesCount = new int[101][100];

        for (int i = 2; i < primesCount.length; i++) {
            primeFactorFrequencies(primesCount, primeNumbers, i);
        }
        return primesCount;
    }

    private static void primeFactorFrequencies(int[][] primesCount, Integer[] primeNumbers, int number) {
        int[] newPrimesCount = new int[primesCount[0].length];
        System.arraycopy(primesCount[number - 1], 0, newPrimesCount, 0, primesCount[number].length);
        int numberCopy = number;

        for (int primeNumber : primeNumbers) {
            if (primeNumber > number) {
                break;
            }

            int count = 0;
            while (number % primeNumber == 0) {
                count++;
                number /= primeNumber;
            }
            newPrimesCount[primeNumber] += count;
        }

        if (number > 1) {
            newPrimesCount[number] += 1;
        }
        primesCount[numberCopy] = newPrimesCount;
    }

    private static int getLastIndex(int[][] primesCount, int number) {
        for (int i = primesCount[number].length - 1; i >= 0; i--) {
            if (primesCount[number][i] != 0) {
                return i;
            }
        }
        return 0;
    }

    private static Integer[] eratosthenesSieve(boolean[] isPrime) {
        List<Integer> primeNumbers = new ArrayList<>();
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
