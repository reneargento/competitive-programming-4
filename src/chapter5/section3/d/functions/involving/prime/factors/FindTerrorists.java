package chapter5.section3.d.functions.involving.prime.factors;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 13/07/25.
 */
public class FindTerrorists {

    private static final int MAX_VALUE = 10001;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        boolean[] numbersWithPrimeFactors = computeNumbersWithPrimeFactors();

        for (int t = 0; t < tests; t++) {
            int low = FastReader.nextInt();
            int high = FastReader.nextInt();

            boolean isFirstNumber = true;
            for (int number = low; number <= high; number++) {
                if (numbersWithPrimeFactors[number]) {
                    if (!isFirstNumber) {
                        outputWriter.print(" ");
                    }

                    outputWriter.print(number);
                    isFirstNumber = false;
                }
            }

            if (isFirstNumber) {
                outputWriter.printLine("-1");
            } else {
                outputWriter.printLine();
            }
        }
        outputWriter.flush();
    }

    private static boolean[] computeNumbersWithPrimeFactors() {
        boolean[] numbersWithPrimeFactors = new boolean[MAX_VALUE];
        boolean[] isPrime = eratosthenesSieve();

        for (int number = 1; number < MAX_VALUE; number++) {
            int factorsCount = countFactors(number);
            if (isPrime[factorsCount]) {
                numbersWithPrimeFactors[number] = true;
            }
        }
        return numbersWithPrimeFactors;
    }

    private static boolean[] eratosthenesSieve() {
        boolean[] isPrime = new boolean[MAX_VALUE];
        for (int i = 2; i < isPrime.length; i++) {
            isPrime[i] = true;
        }

        for (long i = 2; i < isPrime.length; i++) {
            if (isPrime[(int) i]) {
                for (long j = i * i; j < isPrime.length; j += i) {
                    isPrime[(int) j] = false;
                }
            }
        }
        return isPrime;
    }

    private static int countFactors(long number) {
        int factorsCount = 0;
        int upperLimit = (int) Math.sqrt(number);

        for (int i = 1; i <= upperLimit; i++) {
            if (number % i == 0) {
                factorsCount++;

                if (i != number / i) {
                    factorsCount++;
                }
            }
        }
        return factorsCount;
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
