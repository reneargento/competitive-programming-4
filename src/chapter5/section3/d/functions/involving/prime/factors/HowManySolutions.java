package chapter5.section3.d.functions.involving.prime.factors;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 13/07/25.
 */
public class HowManySolutions {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        Integer[] primeNumbers = eratosthenesSieve();

        int caseId = 1;
        int m = FastReader.nextInt();
        int n = FastReader.nextInt();
        int p = FastReader.nextInt();

        while (m != 0 || n != 0 || p != 0) {
            int solutions = computeSolutions(primeNumbers, m, n, p);
            outputWriter.printLine("Case " + caseId + ": " + solutions);

            caseId++;
            m = FastReader.nextInt();
            n = FastReader.nextInt();
            p = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeSolutions(Integer[] primeNumbers, long m, long n, long p) {
        long total = Math.abs(n * m * p * p);
        return 2 * countFactors(primeNumbers, total) - 1;
    }

    private static int countFactors(Integer[] primeNumbers, long number) {
        int factors = 1;

        for (int primeNumber : primeNumbers) {
            int sqrtNumber = (int) Math.sqrt(number);
            if (primeNumber > sqrtNumber) {
                break;
            }

            int power = 1;
            while (number % primeNumber == 0) {
                power++;
                number /= primeNumber;
            }
            factors *= power;
        }

        // Special case where number is a prime number
        if (number > 1) {
            factors *= 2;
        }
        return factors;
    }

    private static Integer[] eratosthenesSieve() {
        List<Integer> primeNumbers = new ArrayList<>();
        int maxNumber = 1000001;
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
