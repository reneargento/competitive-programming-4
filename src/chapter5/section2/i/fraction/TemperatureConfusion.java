package chapter5.section2.i.fraction;

import java.io.*;

/**
 * Created by Rene Argento on 30/04/25.
 */
public class TemperatureConfusion {

    private static class Fraction {
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

        String[] data = FastReader.getLine().split("/");
        int numerator = Integer.parseInt(data[0]);
        int denominator = Integer.parseInt(data[1]);

        Fraction fraction = convertToCelsius(numerator, denominator);
        outputWriter.printLine(fraction.numerator + "/" + fraction.denominator);
        outputWriter.flush();
    }

    // C = ((F - 32) * 5) / 9
    private static Fraction convertToCelsius(long numerator, long denominator) {
        Fraction fractionMinus32 = subtractFraction(numerator, denominator, 32, 1);
        Fraction fractionTimes5 = multiplyFraction(fractionMinus32.numerator, fractionMinus32.denominator,
                5, 1);
        Fraction celsiusFraction = divideFraction(fractionTimes5.numerator, fractionTimes5.denominator,
                9, 1);

        long gcd = gcd(celsiusFraction.numerator, celsiusFraction.denominator);
        return new Fraction(celsiusFraction.numerator / gcd, celsiusFraction.denominator / gcd);
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

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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
