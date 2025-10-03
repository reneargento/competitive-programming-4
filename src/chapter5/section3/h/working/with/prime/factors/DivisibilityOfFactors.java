package chapter5.section3.h.working.with.prime.factors;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 25/09/25.
 */
public class DivisibilityOfFactors {

    private static final int[] PRIMES =
            { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97 };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int number = FastReader.nextInt();
        int divisor = FastReader.nextInt();

        while (number != 0 || divisor != 0) {
            long divisorsCount = countDivisors(number, divisor);
            outputWriter.printLine(divisorsCount);
            number = FastReader.nextInt();
            divisor = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long countDivisors(int number, int divisor) {
        long divisorsCount = 1;

        for (int prime : PRIMES) {
            int powerNumber = getPrimePower(number, prime);
            int powerDivisor = 0;

            while (divisor % prime == 0) {
                divisor /= prime;
                powerDivisor++;
            }

            if (powerDivisor > powerNumber) {
                return 0;
            }
            divisorsCount *= (powerNumber - powerDivisor + 1);
        }

        if (divisor != 1) {
            return 0;
        }
        return divisorsCount;
    }

    private static int getPrimePower(int number, int prime) {
        int power = 0;
        while (number > 0) {
            number /= prime;
            power += number;
        }
        return power;
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
