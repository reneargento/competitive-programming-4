package chapter5.section3.d.functions.involving.prime.factors;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 15/07/25.
 */
public class CompositePrime {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            int setSize = Integer.parseInt(line);
            int[] numbers = new int[setSize];
            for (int i = 0; i < numbers.length; i++) {
                numbers[i] = FastReader.nextInt();
            }

            int compositePrimes = countCompositePrimes(numbers);
            outputWriter.printLine(compositePrimes);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int countCompositePrimes(int[] numbers) {
        int compositePrimes = 0;
        for (int number : numbers) {
            int primeFactors = countPrimeFactors(number);
            if (primeFactors == 2) {
                compositePrimes++;
            }
        }
        return compositePrimes;
    }

    private static int countPrimeFactors(int number) {
        int primeFactors = 0;

        for (int i = 2; i * i <= number; i++) {
            while (number % i == 0) {
                primeFactors++;
                number /= i;
            }
        }

        if (number > 1) {
            primeFactors++;
        }
        return primeFactors;
    }

    private static class FastReader {private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens() ) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
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
