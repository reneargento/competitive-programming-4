package chapter5.section2.i.fraction;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 29/04/25.
 */
public class RationalArithmetic {

    public static class Fraction {
        long numerator;
        long denominator;

        public Fraction(long numerator, long denominator) {
            this.numerator = numerator;
            this.denominator = denominator;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String[] data = FastReader.getLine().split(" ");
            int numerator1 = Integer.parseInt(data[0]);
            int denominator1 = Integer.parseInt(data[1]);
            int numerator2 = Integer.parseInt(data[3]);
            int denominator2 = Integer.parseInt(data[4]);

            Fraction result = performOperation(numerator1, denominator1, numerator2, denominator2, data[2].charAt(0));
            outputWriter.printLine(result.numerator + " / " + result.denominator);
        }
        outputWriter.flush();
    }

    private static Fraction performOperation(long numerator1, long denominator1, long numerator2, long denominator2,
                                             char operation) {
        if (denominator1 < 0) {
            numerator1 *= -1;
            denominator1 *= -1;
        }
        if (denominator2 < 0) {
            numerator2 *= -1;
            denominator2 *= -1;
        }

        Fraction result;
        switch (operation) {
            case '+': result = sumFraction(numerator1, denominator1, numerator2, denominator2); break;
            case '-': result = subtractFraction(numerator1, denominator1, numerator2, denominator2); break;
            case '*': result = multiplyFraction(numerator1, denominator1, numerator2, denominator2); break;
            default: result = divideFraction(numerator1, denominator1, numerator2, denominator2);
        }

        long gcd = gcd(result.numerator, result.denominator);
        result.numerator /= gcd;
        result.denominator /= gcd;

        if (result.denominator < 0) {
            result.numerator *= -1;
            result.denominator *= -1;
        }
        return result;
    }

    private static Fraction sumFraction(long numerator1, long denominator1, long numerator2, long denominator2) {
        long lcm = lcm(denominator1, denominator2);
        long multiplier1 = lcm / denominator1;
        long multiplier2 = lcm / denominator2;

        long numerator = (numerator1 * multiplier1) + (numerator2 * multiplier2);
        return new Fraction(numerator, lcm);
    }

    private static Fraction subtractFraction(long numerator1, long denominator1, long numerator2, long denominator2) {
        return sumFraction(numerator1, denominator1, -numerator2, denominator2);
    }

    private static Fraction multiplyFraction(long numerator1, long denominator1, long numerator2, long denominator2) {
        return new Fraction(numerator1 * numerator2, denominator1 * denominator2);
    }

    private static Fraction divideFraction(long numerator1, long denominator1, long numerator2, long denominator2) {
        return multiplyFraction(numerator1, denominator1, denominator2, numerator2);
    }

    private static long gcd(long number1, long number2) {
        number1 = Math.abs(number1);
        number2 = Math.abs(number2);

        while (number2 > 0) {
            long temp = number2;
            number2 = number1 % number2;
            number1 = temp;
        }
        return number1;
    }

    private static long lcm(long number1, long number2) {
        return number1 * (number2 / gcd(number1, number2));
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
