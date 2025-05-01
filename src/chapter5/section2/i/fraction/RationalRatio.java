package chapter5.section2.i.fraction;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 29/04/25.
 */
public class RationalRatio {

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
        int repeatingNumbers = FastReader.nextInt();

        Fraction fraction = computeRationalNumber(numberString, repeatingNumbers);
        outputWriter.printLine(fraction.nominator + "/" + fraction.denominator);
        outputWriter.flush();
    }

    private static Fraction computeRationalNumber(String numberString, int j) {
        long nominator;
        long denominator;
        int decimalPointIndex = numberString.indexOf('.');
        int k = numberString.length() - (decimalPointIndex + 1) - j;
        double decimalExpansion = Double.parseDouble(numberString);

        int kJSum = k + j;
        long tenToK = (long) Math.pow(10, k);
        long tenToJ = (long) Math.pow(10, j);
        long tenToKJSum = (long) Math.pow(10, kJSum);
        long tenToKJSumTimesDecExp = Math.round(tenToKJSum * decimalExpansion);

        if (j == 0) {
            nominator = tenToKJSumTimesDecExp;
            denominator = tenToKJSum;
        } else {
            nominator = (tenToKJSumTimesDecExp - tenToKJSumTimesDecExp / tenToJ);
            denominator = (tenToKJSum - tenToK);
        }
        return reduceFraction(nominator, denominator);
    }

    private static Fraction reduceFraction(long nominator, long denominator) {
        long gcd = gcd(nominator, denominator);
        return new Fraction(nominator / gcd, denominator / gcd);
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
