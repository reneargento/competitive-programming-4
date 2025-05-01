package chapter5.section2.i.fraction;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 29/04/25.
 */
public class RationalArithmetic {

    public static class Fraction {
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
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String[] data = FastReader.getLine().split(" ");
            int nominator1 = Integer.parseInt(data[0]);
            int denominator1 = Integer.parseInt(data[1]);
            int nominator2 = Integer.parseInt(data[3]);
            int denominator2 = Integer.parseInt(data[4]);

            Fraction result = performOperation(nominator1, denominator1, nominator2, denominator2, data[2].charAt(0));
            outputWriter.printLine(result.nominator + " / " + result.denominator);
        }
        outputWriter.flush();
    }

    private static Fraction performOperation(long nominator1, long denominator1, long nominator2, long denominator2,
                                             char operation) {
        if (denominator1 < 0) {
            nominator1 *= -1;
            denominator1 *= -1;
        }
        if (denominator2 < 0) {
            nominator2 *= -1;
            denominator2 *= -1;
        }

        Fraction result;
        switch (operation) {
            case '+': result = sumFraction(nominator1, denominator1, nominator2, denominator2); break;
            case '-': result = subtractFraction(nominator1, denominator1, nominator2, denominator2); break;
            case '*': result = multiplyFraction(nominator1, denominator1, nominator2, denominator2); break;
            default: result = divideFraction(nominator1, denominator1, nominator2, denominator2);
        }

        long gcd = gcd(result.nominator, result.denominator);
        result.nominator /= gcd;
        result.denominator /= gcd;

        if (result.denominator < 0) {
            result.nominator *= -1;
            result.denominator *= -1;
        }
        return result;
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
