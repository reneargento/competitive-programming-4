package chapter5.section2.i.fraction;

import java.io.*;

/**
 * Created by Rene Argento on 30/04/25.
 */
public class TemperatureConfusion {

    private static class Fraction {
        long nominator;
        long denominator;

        public Fraction(long nominator, long denominator) {
            this.nominator = nominator;
            this.denominator = denominator;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String[] data = FastReader.getLine().split("/");
        int nominator = Integer.parseInt(data[0]);
        int denominator = Integer.parseInt(data[1]);

        Fraction fraction = convertToCelsius(nominator, denominator);
        outputWriter.printLine(fraction.nominator + "/" + fraction.denominator);
        outputWriter.flush();
    }

    // C = ((F - 32) * 5) / 9
    private static Fraction convertToCelsius(long nominator, long denominator) {
        Fraction fractionMinus32 = subtractFraction(nominator, denominator, 32, 1);
        Fraction fractionTimes5 = multiplyFraction(fractionMinus32.nominator, fractionMinus32.denominator,
                5, 1);
        Fraction celsiusFraction = divideFraction(fractionTimes5.nominator, fractionTimes5.denominator,
                9, 1);

        long gcd = gcd(celsiusFraction.nominator, celsiusFraction.denominator);
        return new Fraction(celsiusFraction.nominator / gcd, celsiusFraction.denominator / gcd);
    }

    private static Fraction sumFraction(long nominator1, long denominator1, long nominator2, long denominator2) {
        long lcm = lcm(denominator1, denominator2);
        long multiplier1 = lcm / denominator1;
        long multiplier2 = lcm / denominator2;

        long nominator = (nominator1 * multiplier1) + (nominator2 * multiplier2);
        return new Fraction(nominator, lcm);
    }

    private static Fraction subtractFraction(long nominator1, long denominator1, long nominator2, long denominator2) {
        return sumFraction(nominator1, denominator1, -nominator2, denominator2);
    }

    private static Fraction multiplyFraction(long nominator1, long denominator1, long nominator2, long denominator2) {
        return new Fraction(nominator1 * nominator2, denominator1 * denominator2);
    }

    private static Fraction divideFraction(long nominator1, long denominator1, long nominator2, long denominator2) {
        return multiplyFraction(nominator1, denominator1, denominator2, nominator2);
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
