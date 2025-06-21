package chapter5.section3.b.probabilistic.prime.testing;

import java.io.*;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 02/06/25.
 */
public class BlackboardNumbers {

    private static class Probability {
        int nominator;
        int denominator;

        public Probability(int nominator, int denominator) {
            this.nominator = nominator;
            this.denominator = denominator;
        }
    }

    private static final int FALSE = 0;
    private static final int TRUE = 1;
    private static final int CANNOT_REPRESENT = 2;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String number = FastReader.getLine();
            Probability probability = computePrimeProbability(number);
            outputWriter.printLine(probability.nominator + "/" + probability.denominator);
        }
        outputWriter.flush();
    }

    private static Probability computePrimeProbability(String number) {
        int totalRepresentations = 4;
        int primeMatches = 0;

        int binaryResult = isPrimeInBase(number, 2);
        int octalResult = isPrimeInBase(number, 8);
        int decimalResult = isPrimeInBase(number, 10);

        if (binaryResult == TRUE) {
            primeMatches++;
        } else if (binaryResult == CANNOT_REPRESENT) {
            totalRepresentations--;
        }

        if (octalResult == TRUE) {
            primeMatches++;
        } else if (octalResult == CANNOT_REPRESENT) {
            totalRepresentations--;
        }

        if (decimalResult == TRUE) {
            primeMatches++;
        } else if (decimalResult == CANNOT_REPRESENT) {
            totalRepresentations--;
        }
        primeMatches += isPrimeInBase(number, 16);

        return reduceFraction(primeMatches, totalRepresentations);
    }

    private static int isPrimeInBase(String number, int base) {
        try {
            long numberInBase = Long.parseLong(number, base);
            BigInteger numberBI = BigInteger.valueOf(numberInBase);
            return numberBI.isProbablePrime(10) ? TRUE : FALSE;
        } catch (NumberFormatException exception) {
            return CANNOT_REPRESENT;
        }
    }

    private static int gcd(int number1, int number2) {
        while (number2 > 0) {
            int temp = number2;
            number2 = number1 % number2;
            number1 = temp;
        }
        return number1;
    }

    private static Probability reduceFraction(int dividend, int divisor) {
        int gcd = gcd(dividend, divisor);
        int newNominator = dividend / gcd;
        int newDenominator = divisor / gcd;

        return new Probability(newNominator, newDenominator);
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