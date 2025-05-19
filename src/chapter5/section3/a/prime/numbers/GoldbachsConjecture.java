package chapter5.section3.a.prime.numbers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 18/05/25.
 */
public class GoldbachsConjecture {

    private static class Result {
        int prime1;
        int prime2;

        public Result(int prime1, int prime2) {
            this.prime1 = prime1;
            this.prime2 = prime2;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        List<Integer> primeNumbers = eratosthenesSieve(1000000);

        int number = FastReader.nextInt();

        while (number != 0) {
            Result primeSum = computePrimeSum(primeNumbers, number);
            outputWriter.printLine(String.format("%d = %d + %d", number, primeSum.prime1, primeSum.prime2));
            number = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Result computePrimeSum(List<Integer> primeNumbers, int number) {
        for (int prime1 : primeNumbers) {
            if (prime1 == 2) {
                continue;
            }

            int prime2 = number - prime1;
            if (isPrime[prime2]) {
                return new Result(prime1, prime2);
            }
        }
        return null;
    }

    private static boolean[] isPrime;

    private static List<Integer> eratosthenesSieve(long number) {
        List<Integer> primeNumbers = new ArrayList<>();
        isPrime = new boolean[(int) number + 1];

        for (int i = 2; i < isPrime.length; i++) {
            isPrime[i] = true;
        }

        for (long i = 2; i <= number; i++) {
            if (isPrime[(int) i]) {
                for (long j = i * i; j < isPrime.length; j += i) {
                    isPrime[(int) j] = false;
                }
                primeNumbers.add((int) i);
            }
        }
        return primeNumbers;
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
