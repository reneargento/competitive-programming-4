package chapter5.section2.i.fraction;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 24/04/25.
 */
public class HarmonicMean {

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
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int[] numbers = new int[FastReader.nextInt()];
            for (int i = 0; i < numbers.length; i++) {
                numbers[i] = FastReader.nextInt();
            }

            Fraction harmonicMean = computeHarmonicMean(numbers);
            outputWriter.printLine(String.format("Case %d: %d/%d", t, harmonicMean.nominator, harmonicMean.denominator));
        }
        outputWriter.flush();
    }

    private static Fraction computeHarmonicMean(int[] numbers) {
        long nominator = 1;
        long denominator = numbers[0];

        for (int i = 1; i < numbers.length; i++) {
            long lcm = lcm(denominator, numbers[i]);
            long multiplier1 = lcm / denominator;
            long multiplier2 = lcm / numbers[i];

            nominator = nominator * multiplier1 + multiplier2;
            denominator = lcm;
        }

        long newNominator = denominator * numbers.length;
        long gcd = gcd(newNominator, nominator);
        return new Fraction(newNominator / gcd, nominator / gcd);
    }

    private static long gcd(long number1, long number2) {
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
