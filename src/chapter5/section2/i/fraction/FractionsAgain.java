package chapter5.section2.i.fraction;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 26/04/25.
 */
public class FractionsAgain {

    private static class FractionPair {
        long denominator1;
        long denominator2;

        public FractionPair(long denominator1, long denominator2) {
            this.denominator1 = denominator1;
            this.denominator2 = denominator2;
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

        String line = FastReader.getLine();
        while (line != null) {
            int k = Integer.parseInt(line);
            List<FractionPair> pairs = computePairs(k);

            outputWriter.printLine(pairs.size());
            for (FractionPair pair : pairs) {
                outputWriter.printLine(String.format("1/%d = 1/%d + 1/%d", k, pair.denominator1, pair.denominator2));
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static List<FractionPair> computePairs(int k) {
        List<FractionPair> pairs = new ArrayList<>();

        for (int y = k + 1; y <= 2 * k; y++) {
            Fraction x = subtractFraction(1, k, 1, y);
            long gcd = gcd(x.nominator, x.denominator);
            x.nominator /= gcd;
            x.denominator /= gcd;

            if (x.nominator == 1) {
                pairs.add(new FractionPair(x.denominator, y));
            }
        }
        return pairs;
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

    private static long gcd(long number1, long number2) {
        while (number2 > 0) {
            long temp = number2;
            number2 = number1 % number2;
            number1 = temp;
        }
        return number1;
    }

    public static long lcm(long number1, long number2) {
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
