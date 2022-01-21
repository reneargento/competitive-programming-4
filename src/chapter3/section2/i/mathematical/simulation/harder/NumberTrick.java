package chapter3.section2.i.mathematical.simulation.harder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 20/01/22.
 */
// Based on https://nordic.icpc.io/ncpc2013/ncpc2013slides.pdf
public class NumberTrick {

    private static class Fraction {
        long a;
        long b;

        public Fraction(long a, long b) {
            this.a = a;
            this.b = b;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String multiplier = FastReader.getLine();
        Fraction fraction = getFractionFromDouble(multiplier);

        List<Integer> numbers = computeNumbers(fraction);
        if (numbers.isEmpty()) {
            outputWriter.printLine("No solution");
        } else {
            for (Integer number : numbers) {
                outputWriter.printLine(number);
            }
        }
        outputWriter.flush();
    }

    private static Fraction getFractionFromDouble(String multiplier) {
        String[] numbers = multiplier.split("\\.");
        String numbersConcatenated = numbers[0] + numbers[1];
        long dividend = Long.parseLong(numbersConcatenated);
        long divisor = (long) Math.pow(10, numbers[1].length());

        long gcd = gcd(dividend, divisor);
        return new Fraction(dividend / gcd, divisor / gcd);
    }

    private static long gcd(long number1, long number2) {
        while (number2 > 0) {
            long temp = number2;
            number2 = number1 % number2;
            number1 = temp;
        }
        return number1;
    }

    private static List<Integer> computeNumbers(Fraction fraction) {
        List<Integer> numbers = new ArrayList<>();

        for (int digitsLength = 0; digitsLength <= 7; digitsLength++) {
            for (int firstDigit = 1; firstDigit <= 9; firstDigit++) {
                int number = getNumber(fraction, digitsLength, firstDigit);
                if (number != -1) {
                    numbers.add(number);
                }
            }
        }
        return numbers;
    }

    // A = number
    // A' = number with first digit moved to the end
    // A0 = first digit
    // n = number of digits - 1
    // x = multiplier
    // A' = (A − 10^n * A0) * 10 + A0
    // We also know that
    // A' = A * x
    // Equating both
    // A * x = (A − 10^n * A0) * 10 + A0
    // A * x = 10A - (10^(n + 1) - 1) * A0
    // A * x - 10A = - (10^(n + 1) - 1) * A0
    // A (x - 10) = - (10^(n + 1) - 1) * A0
    // A (-x + 10) = (10^(n + 1) - 1) * A0
    // A = (10^(n + 1) - 1) * A0 / (10 - x)
    // x is a fraction a / b
    // A = (10^(n + 1) - 1) * A0 / (10 - (a / b))
    // A = b * (10^(n + 1) - 1) * A0 / (b * 10 - a)
    private static int getNumber(Fraction fraction, int digitsLength, long firstDigit) {
        long dividend = fraction.b * (int) (Math.pow(10, digitsLength + 1) - 1) * firstDigit;
        double divisor = fraction.b * 10 - fraction.a;
        if (divisor == 0) {
            return -1;
        }

        if (isTrickNumber(dividend, divisor, digitsLength, firstDigit)) {
            return (int) (dividend / divisor);
        }
        return -1;
    }

    private static boolean isTrickNumber(long dividend, double divisor, int digitsLength, long firstDigit) {
        if (dividend % divisor != 0) {
            return false;
        }
        long result = (long) (dividend / divisor);
        long powerOf10 = (long) Math.pow(10, digitsLength);
        return result / powerOf10 == firstDigit;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
