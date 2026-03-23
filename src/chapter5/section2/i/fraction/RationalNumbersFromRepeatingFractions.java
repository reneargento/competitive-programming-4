package chapter5.section2.i.fraction;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/04/25.
 */
public class RationalNumbersFromRepeatingFractions {

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

        int j = FastReader.nextInt();
        int caseNumber = 1;

        while (j != -1) {
            String decimalExpansionString = FastReader.next();
            Fraction rationalNumber = computeRationalNumber(j, decimalExpansionString);
            outputWriter.printLine(String.format("Case %d: %d/%d", caseNumber, rationalNumber.numerator,
                    rationalNumber.denominator));
            caseNumber++;
            j = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Fraction computeRationalNumber(int j, String decimalExpansionString) {
        long numerator;
        long denominator;
        int k = decimalExpansionString.length() - 2 - j;
        double decimalExpansion = Double.parseDouble(decimalExpansionString);

        int kJSum = k + j;
        long tenToK = (long) Math.pow(10, k);
        long tenToJ = (long) Math.pow(10, j);
        long tenToKJSum = (long) Math.pow(10, kJSum);
        long tenToKJSumTimesDecExp = Math.round(tenToKJSum * decimalExpansion);

        if (j == 0) {
            numerator = tenToKJSumTimesDecExp;
            denominator = tenToKJSum;
        } else {
            numerator = (tenToKJSumTimesDecExp - tenToKJSumTimesDecExp / tenToJ);
            denominator = (tenToKJSum - tenToK);
        }
        return reduceFraction(numerator, denominator);
    }

    private static long gcd(long number1, long number2) {
        while (number2 > 0) {
            long temp = number2;
            number2 = number1 % number2;
            number1 = temp;
        }
        return number1;
    }

    private static Fraction reduceFraction(long numerator, long denominator) {
        long gcd = gcd(numerator, denominator);
        long newNumerator = numerator / gcd;
        long newDenominator = denominator / gcd;
        return new Fraction(newNumerator, newDenominator);
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
