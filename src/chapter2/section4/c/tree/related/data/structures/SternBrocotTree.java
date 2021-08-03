package chapter2.section4.c.tree.related.data.structures;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/08/21.
 */
public class SternBrocotTree {

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
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String path = FastReader.next();
            Fraction fraction = getFraction(path);
            outputWriter.printLine(fraction.numerator + "/" + fraction.denominator);
        }
        outputWriter.flush();
    }

    private static Fraction getFraction(String path) {
        return getFraction(0, path, new Fraction(0, 1), new Fraction(1, 0));
    }

    private static Fraction getFraction(int index, String path, Fraction leftFraction, Fraction rightFraction) {
        long newNumerator = leftFraction.numerator + rightFraction.numerator;
        long newDenominator = leftFraction.denominator + rightFraction.denominator;
        Fraction newFraction = new Fraction(newNumerator, newDenominator);

        if (index == path.length()) {
            return newFraction;
        }

        if (path.charAt(index) == 'L') {
            return getFraction(index + 1, path, leftFraction, newFraction);
        } else {
            return getFraction(index + 1, path, newFraction, rightFraction);
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
