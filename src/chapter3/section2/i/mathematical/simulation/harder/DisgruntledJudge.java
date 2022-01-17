package chapter3.section2.i.mathematical.simulation.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 14/01/22.
 */
// Based on https://github.com/Kevinwen1999/UVa-solutions/blob/master/12169%20-%20Disgruntled%20Judge.cpp
public class DisgruntledJudge {

    private static class Solution {
        long a;
        long b;

        public Solution(long a, long b) {
            this.a = a;
            this.b = b;
        }
    }

    private static long bezoutCoefficient1;
    private static long bezoutCoefficient2;
    private static long gcd;
    private static final int MOD = 10001;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] testsCases = new int[FastReader.nextInt()];

        for (int i = 0; i < testsCases.length; i++) {
            testsCases[i] = FastReader.nextInt();
        }
        Solution solution = computeSolution(testsCases);

        for (long testsCase : testsCases) {
            long result = getNextNumber(testsCase, solution.a, solution.b);
            outputWriter.printLine(result);
        }
        outputWriter.flush();
    }

    // x2 = (a * x1 + b) % 10001
    // x3 = (a * x2 + b) % 10001

    // x3 = (a * (a * x1 + b) % 10001 + b) % 10001
    // x3 = (a * (a * x1 + b) + b) % 10001
    // x3 + 10001 * k = a^2 * x1 + a * b + b
    // x3 + 10001 * k = a^2 * x1 + (a + 1) * b
    // x3 + 10001 * k - a^2 * x1 = (a + 1) * b
    // x3 - a^2 * x1 = (a + 1) * b + 10001 * (-k)
    // Now use extended euclid to compute b and -k
    private static Solution computeSolution(int[] testsCases) {
        for (long a = 0; a < MOD; a++) {
            long c = testsCases[1] - a * a * testsCases[0];

            extendedEuclid(MOD, a + 1);

            if (c % gcd != 0) {
                // c must be divisible by gcd
                continue;
            }
            long b = bezoutCoefficient2 * c / gcd;
            if (isValidSolution(testsCases, a, b)) {
                return new Solution(a, b);
            }
        }
        return null;
    }

    private static long getNextNumber(long number, long a, long b) {
        return (a * number + b) % MOD;
    }

    private static boolean isValidSolution(int[] testsCases, long a, long b) {
        for (int i = 0; i < testsCases.length - 1; i++) {
            long output = getNextNumber(testsCases[i], a, b);
            long nextInput = getNextNumber(output, a, b);

            if (testsCases[i + 1] != nextInput) {
                return false;
            }
        }
        return true;
    }

    private static  void extendedEuclid(long number1, long number2) {
        // Base case
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
