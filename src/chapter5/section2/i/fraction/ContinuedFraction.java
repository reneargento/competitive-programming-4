package chapter5.section2.i.fraction;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 24/04/25.
 */
public class ContinuedFraction {

    private static class PartialQuotients {
        List<Long> values;

        public PartialQuotients(List<Long> values) {
            this.values = values;
        }
    }

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

        int[] partialQuotients1 = new int[FastReader.nextInt()];
        int[] partialQuotients2 = new int[FastReader.nextInt()];
        readPartialQuotients(partialQuotients1);
        readPartialQuotients(partialQuotients2);

        List<PartialQuotients> partialQuotientsList = computeResultingPartialQuotients(partialQuotients1,
                partialQuotients2);
        for (PartialQuotients partialQuotients : partialQuotientsList) {
            printPartialQuotients(outputWriter, partialQuotients);
        }
        outputWriter.flush();
    }

    private static void readPartialQuotients(int[] partialQuotients) throws IOException {
        for (int i = 0; i < partialQuotients.length; i++) {
            partialQuotients[i] = FastReader.nextInt();
        }
    }

    private static void printPartialQuotients(OutputWriter outputWriter, PartialQuotients partialQuotients) {
        List<Long> values = partialQuotients.values;
        outputWriter.print(values.get(0));
        for (int i = 1; i < values.size(); i++) {
            outputWriter.print(" " + values.get(i));
        }
        outputWriter.printLine();
    }

    private static List<PartialQuotients> computeResultingPartialQuotients(int[] partialQuotients1,
                                                                           int[] partialQuotients2) {
        Fraction fraction1 = getFractionFromPartialQuotients(partialQuotients1);
        Fraction fraction2 = getFractionFromPartialQuotients(partialQuotients2);

        Fraction sum = sumFraction(fraction1.nominator, fraction1.denominator, fraction2.nominator,
                fraction2.denominator);
        Fraction subtraction = sumFraction(fraction1.nominator, fraction1.denominator, -fraction2.nominator,
                fraction2.denominator);
        Fraction multiplication = multiplyFraction(fraction1.nominator, fraction1.denominator, fraction2.nominator,
                fraction2.denominator);
        Fraction division = multiplyFraction(fraction1.nominator, fraction1.denominator, fraction2.denominator,
                fraction2.nominator);

        List<PartialQuotients> partialQuotients = new ArrayList<>();
        partialQuotients.add(getPartialQuotientsFromFraction(sum));
        partialQuotients.add(getPartialQuotientsFromFraction(subtraction));
        partialQuotients.add(getPartialQuotientsFromFraction(multiplication));
        partialQuotients.add(getPartialQuotientsFromFraction(division));
        return partialQuotients;
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
        return result;
    }

    private static PartialQuotients getPartialQuotientsFromFraction(Fraction fraction) {
        List<Long> values = new ArrayList<>();
        long number1 = fraction.nominator;
        long number2 = fraction.denominator;

        while (number2 > 0) {
            long partialQuotient = number1 / number2;
            values.add(partialQuotient);

            long temp = number2;
            number2 = number1 % number2;
            number1 = temp;
        }
        return new PartialQuotients(values);
    }

    private static Fraction sumFraction(long nominator1, long denominator1, long nominator2, long denominator2) {
        long lcm = lcm(denominator1, denominator2);
        long multiplier1 = lcm / denominator1;
        long multiplier2 = lcm / denominator2;

        long resultDenominator = (nominator1 * multiplier1) + (nominator2 * multiplier2);
        return new Fraction(resultDenominator, lcm);
    }

    private static Fraction multiplyFraction(long nominator1, long denominator1, long nominator2, long denominator2) {
        return new Fraction(nominator1 * nominator2, denominator1 * denominator2);
    }

    private static long lcm(long number1, long number2) {
        return number1 * (number2 / gcd(number1, number2));
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
