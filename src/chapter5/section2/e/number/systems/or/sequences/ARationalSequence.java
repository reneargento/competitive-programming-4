package chapter5.section2.e.number.systems.or.sequences;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 18/02/25.
 */
// Based on https://github.com/BrandonTang89/Competitive_Programming_4_Solutions/blob/main/Chapter_5_Mathematics/Adhoc_Math_Problems/kattis_rationalsequence.cpp
public class ARationalSequence {

    private static class Fraction {
        int nominator;
        int denominator;

        public Fraction(int nominator, int denominator) {
            this.nominator = nominator;
            this.denominator = denominator;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int dataSetNumber = FastReader.nextInt();
            String[] data = FastReader.next().split("/");
            int nominator = Integer.parseInt(data[0]);
            int denominator = Integer.parseInt(data[1]);
            Fraction fraction = new Fraction(nominator, denominator);

            Fraction nextNumber = computeNextNumber(fraction);
            outputWriter.printLine(String.format("%d %d/%d", dataSetNumber, nextNumber.nominator, nextNumber.denominator));
        }
        outputWriter.flush();
    }

    private static Fraction computeNextNumber(Fraction fraction) {
        if (fraction.denominator == 1) {
            return new Fraction(1, fraction.nominator + 1);
        }
        int parentNominator = fraction.nominator;
        int parentDenominator = fraction.denominator;
        int levelsAbove = (int) Math.ceil(fraction.nominator / (double) fraction.denominator - 1);

        parentNominator -= fraction.denominator * levelsAbove;

        Fraction parent = getParentFromRight(parentNominator, parentDenominator);
        Fraction rightChild = getRightChild(parent.nominator, parent.denominator);

        rightChild.denominator += rightChild.nominator * levelsAbove;
        return rightChild;
    }

    private static Fraction getParentFromRight(int nominator, int denominator) {
        int parentDenominator = denominator - nominator;
        return new Fraction(nominator, parentDenominator);
    }

    private static Fraction getRightChild(int nominator, int denominator) {
        int childNominator = nominator + denominator;
        return new Fraction(childNominator, denominator);
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
