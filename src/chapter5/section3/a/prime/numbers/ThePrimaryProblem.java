package chapter5.section3.a.prime.numbers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 01/06/25.
 */
public class ThePrimaryProblem {

    private static class Sum {
        int prime1;
        int prime2;

        public Sum(int prime1, int prime2) {
            this.prime1 = prime1;
            this.prime2 = prime2;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int number = FastReader.nextInt();
        Integer[] primeNumbers = eratosthenesSieve();

        while (number != 0) {
            Sum sum = computePrimeSum(primeNumbers, number);

            outputWriter.printLine(number + ":");
            if (sum == null) {
                outputWriter.printLine("NO WAY!");
            } else {
                outputWriter.printLine(String.format("%d+%d", sum.prime1, sum.prime2));
            }
            number = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Sum computePrimeSum(Integer[] primeNumbers, int number) {
        int endIndex = binarySearch(primeNumbers, number);

        for (int i = 0; i < endIndex; i++) {
            int prime = primeNumbers[i];
            int otherPrime = number - prime;

            if (isPrime[otherPrime]) {
                return new Sum(prime, otherPrime);
            }
        }
        return null;
    }

    private static boolean[] isPrime;

    private static Integer[] eratosthenesSieve() {
        long maxNumber = 1000000;
        List<Integer> primeNumbers = new ArrayList<>();
        isPrime = new boolean[(int) maxNumber + 1];
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

    private static int binarySearch(Integer[] values, int target) {
        int low = 0;
        int high = values.length - 1;
        int result = -1;

        while (low <= high) {
            int middle = low + (high - low) / 2;

            if (values[middle] > target) {
                high = middle - 1;
            } else {
                result = middle;
                low = middle + 1;
            }
        }
        return result;
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
