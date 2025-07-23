package chapter5.section3.d.functions.involving.prime.factors;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 17/07/25.
 */
public class DPANumbersII {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        Integer[] primeNumbers = eratosthenesSieve();

        for (int t = 0; t < tests; t++) {
            long number = FastReader.nextLong();
            String category = computeCategory(primeNumbers, number);
            outputWriter.printLine(category);
        }
        outputWriter.flush();
    }

    private static String computeCategory(Integer[] primeNumbers, long number) {
        long factorsSum = 1;
        long numberCopy = number;

        for (long primeNumber : primeNumbers) {
            if (primeNumber * primeNumber > numberCopy) {
                break;
            }

            if (numberCopy % primeNumber != 0) {
                continue;
            }
            long power = primeNumber;
            while (numberCopy % primeNumber == 0) {
                numberCopy /= primeNumber;
                power *= primeNumber;
            }

            if (factorsSum > 2 * number) {
                break;
            }
            factorsSum *= (power - 1) / (primeNumber - 1);
        }
        if (numberCopy != 1) {
            factorsSum *= (numberCopy + 1);
        }

        factorsSum /= 2;

        if (factorsSum < number) {
            return "deficient";
        } else if (factorsSum == number) {
            return "perfect";
        }
        return "abundant";
    }

    private static Integer[] eratosthenesSieve() {
        List<Integer> primeNumbers = new ArrayList<>();
        boolean[] isPrime = new boolean[1000001];
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

        private static long nextLong() throws IOException {
            return Long.parseLong(next());
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
