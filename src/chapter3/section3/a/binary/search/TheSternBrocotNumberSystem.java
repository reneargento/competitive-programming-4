package chapter3.section3.a.binary.search;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 14/03/22.
 */
public class TheSternBrocotNumberSystem {

    private static class Fraction {
        int numerator;
        int denominator;
        double value;

        public Fraction(int numerator, int denominator) {
            this.numerator = numerator;
            this.denominator = denominator;
            value = numerator / (double) denominator;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int numerator = FastReader.nextInt();
        int denominator = FastReader.nextInt();

        while (numerator != 1 || denominator != 1) {
            Fraction left = new Fraction(0, 1);
            Fraction right = new Fraction(1, 0);
            Fraction fraction = new Fraction(numerator, denominator);
            String representation = getRepresentation(left, right, fraction, new StringBuilder());
            outputWriter.printLine(representation);

            numerator = FastReader.nextInt();
            denominator = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static String getRepresentation(Fraction left, Fraction right, Fraction target,
                                            StringBuilder representation) {
        int numerator = left.numerator + right.numerator;
        int denominator = left.denominator + right.denominator;
        Fraction fraction = new Fraction(numerator, denominator);

        if (numerator == target.numerator && denominator == target.denominator) {
            return representation.toString();
        }

        double value = numerator / (double) denominator;
        if (target.value < value) {
            representation.append("L");
            return getRepresentation(left, fraction, target, representation);
        } else {
            representation.append("R");
            return getRepresentation(fraction, right, target, representation);
        }
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
