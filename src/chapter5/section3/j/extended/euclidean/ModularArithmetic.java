package chapter5.section3.j.extended.euclidean;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 15/10/25.
 */
public class ModularArithmetic {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        long n = FastReader.nextLong();
        int operations = FastReader.nextInt();

        while (n != 0 || operations != 0) {

            for (int o = 0; o < operations; o++) {
                long number1 = FastReader.nextLong();
                char operation = FastReader.next().charAt(0);
                long number2 = FastReader.nextLong();

                long result = computeResult(n, number1, operation, number2);
                outputWriter.printLine(result);
            }
            n = FastReader.nextInt();
            operations = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long computeResult(long n, long number1, char operation, long number2) {
        long result;

        if (operation == '+') {
            result = number1 + number2;
        } else if (operation == '-') {
            result = number1 - number2;
        } else if (operation == '*') {
            result = number1 * number2;
        } else {
            return divMod(number1, number2, n);
        }
        return mod(result, n);
    }

    private static long divMod(long a, long b, long m) {
        long modInverseB = (modularMultiplicativeInverse(b, m));
        if (modInverseB == -1) {
            return -1;
        }
        return mod(mod(a, m) * modInverseB, m);
    }

    // Computes b^(-1) % m
    private static long modularMultiplicativeInverse(long b, long m) {
        extendedEuclid(b, m);
        if (gcd != 1) {
            return -1;
        }
        return mod(bezoutCoefficient1, m);
    }

    private static long mod(long number, long mod) {
        return ((number % mod) + mod) % mod;
    }

    private static long bezoutCoefficient1;
    private static long bezoutCoefficient2;
    private static long gcd;

    private static void extendedEuclid(long number1, long number2) {
        if (number2 == 0) {
            bezoutCoefficient1 = 1;
            bezoutCoefficient2 = 0;
            gcd = number1;
            return;
        }

        extendedEuclid(number2, number1 % number2);

        long nextBezoutCoefficient1 = bezoutCoefficient2;
        long nextBezoutCoefficient2 = bezoutCoefficient1 - (number1 / number2) * bezoutCoefficient2;

        bezoutCoefficient1 = nextBezoutCoefficient1;
        bezoutCoefficient2 = nextBezoutCoefficient2;
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
