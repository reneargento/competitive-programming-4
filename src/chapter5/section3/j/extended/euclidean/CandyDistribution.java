package chapter5.section3.j.extended.euclidean;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 15/10/25.
 */
public class CandyDistribution {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int kids = FastReader.nextInt();
            int candiesPerBag = FastReader.nextInt();

            int candyBags = computeCandyBags(kids, candiesPerBag);
            if (candyBags == -1) {
                outputWriter.printLine("IMPOSSIBLE");
            } else {
                outputWriter.printLine(candyBags);
            }
        }
        outputWriter.flush();
    }

    private static int computeCandyBags(int kids, int candiesPerBag) {
        if (candiesPerBag == 1) {
            if (kids == Math.pow(10, 9)) {
                return -1;
            }
            return kids + 1;
        }
        if (kids == 1) {
            return 1;
        }
        return modularMultiplicativeInverse(candiesPerBag, kids);
    }

    // Computes b^(-1) % m
    private static int modularMultiplicativeInverse(int b, int m) {
        extendedEuclid(b, m);
        if (gcd != 1) {
            // b and m are not relatively prime, there is no solution
            return -1;
        }
        // b * bezoutCoefficient1 + m * bezoutCoefficient2 = 1, now apply mod(m) to get b * bezoutCoefficient1 = 1 % m
        return mod(bezoutCoefficient1, m);
    }

    private static int mod(int number, int mod) {
        return ((number % mod) + mod) % mod;
    }

    private static int bezoutCoefficient1;
    private static int bezoutCoefficient2;
    private static int gcd;

    private static void extendedEuclid(int number1, int number2) {
        if (number2 == 0) {
            bezoutCoefficient1 = 1;
            bezoutCoefficient2 = 0;
            gcd = number1;
            return;
        }

        extendedEuclid(number2, number1 % number2);

        int nextBezoutCoefficient1 = bezoutCoefficient2;
        int nextBezoutCoefficient2 = bezoutCoefficient1 - (number1 / number2) * bezoutCoefficient2;

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

        public void flush() {
            writer.flush();
        }
    }
}
