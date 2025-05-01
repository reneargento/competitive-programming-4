package chapter5.section2.i.fraction;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 29/04/25.
 */
public class JointAttack {

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

        int[] quotients = new int[FastReader.nextInt()];
        for (int i = 0; i < quotients.length; i++) {
            quotients[i] = FastReader.nextInt();
        }
        Fraction fraction = getFractionFromPartialQuotients(quotients);
        outputWriter.printLine(fraction.nominator + "/" + fraction.denominator);
        outputWriter.flush();
    }

    private static Fraction getFractionFromPartialQuotients(int[] partialQuotients) {
        long nominator = 1;
        long denominator = partialQuotients[partialQuotients.length - 1];
        Fraction result = new Fraction(denominator,1);

        for (int i = partialQuotients.length - 2; i >= 0; i--) {
            result = sumFraction(partialQuotients[i], 1, nominator, denominator);
            nominator = result.denominator;
            denominator = result.nominator;
        }

        long gcd = gcd(nominator, denominator);
        result.nominator /= gcd;
        result.denominator /= gcd;
        return result;
    }

    private static Fraction sumFraction(long nominator1, long denominator1, long nominator2, long denominator2) {
        long lcm = lcm(denominator1, denominator2);
        long multiplier1 = lcm / denominator1;
        long multiplier2 = lcm / denominator2;

        long nominator = (nominator1 * multiplier1) + (nominator2 * multiplier2);
        return new Fraction(nominator, lcm);
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
