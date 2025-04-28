package chapter5.section2.i.fraction;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 24/04/25.
 */
public class DeadFraction {

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

        String numberString = FastReader.next();
        while (!numberString.equals("0")) {
            Fraction fraction = computeFraction(numberString);
            outputWriter.printLine(fraction.nominator + "/" + fraction.denominator);
            numberString = FastReader.next();
        }
        outputWriter.flush();
    }

    private static Fraction computeFraction(String numberString) {
        Fraction bestFraction = null;
        String trimmedNumberString = numberString.substring(2, numberString.length() - 3);
        long number = Long.parseLong(trimmedNumberString);
        long length = trimmedNumberString.length();

        for (int lastDigitsRepeating = 1; lastDigitsRepeating <= length; lastDigitsRepeating++) {
            long nominator = number - number / (long) Math.pow(10, lastDigitsRepeating);
            long denominator = (long) (Math.pow(10, length) - Math.pow(10, length - lastDigitsRepeating));

            long gcd = gcd(nominator, denominator);
            nominator /= gcd;
            denominator /= gcd;

            if (bestFraction == null || denominator < bestFraction.denominator) {
                bestFraction = new Fraction(nominator, denominator);
            }
        }
        return bestFraction;
    }

    private static long gcd(long number1, long number2) {
        while (number2 > 0) {
            long temp = number2;
            number2 = number1 % number2;
            number1 = temp;
        }
        return number1;
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
