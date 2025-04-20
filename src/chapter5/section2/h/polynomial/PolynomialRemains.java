package chapter5.section2.h.polynomial;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 18/04/25.
 */
// Based on https://www.mathsisfun.com/algebra/polynomials-division-long.html
public class PolynomialRemains {

    private static class Term {
        int coefficient;
        int exponent;

        public Term(int coefficient, int exponent) {
            this.coefficient = coefficient;
            this.exponent = exponent;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int coefficientsNumber = FastReader.nextInt();
        int k = FastReader.nextInt();

        while (coefficientsNumber != -1 || k != -1) {
            Term[] terms = new Term[coefficientsNumber + 1];
            for (int i = 0; i < terms.length; i++) {
                Term term = new Term(FastReader.nextInt(), i);
                terms[i] = term;
            }

            List<Integer> remainder = computeRemainder(terms, k);
            outputWriter.print(remainder.get(0));
            for (int i = 1; i < remainder.size(); i++) {
                outputWriter.print(" " + remainder.get(i));
            }
            outputWriter.printLine();

            coefficientsNumber = FastReader.nextInt();
            k = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static List<Integer> computeRemainder(Term[] terms, int k) {
        int termsEndIndex = terms.length - 1;

        while (termsEndIndex >= 0 && terms[termsEndIndex].exponent >= k) {
            Term lastTerm = terms[termsEndIndex];
            Term resultSection = new Term(lastTerm.coefficient, lastTerm.exponent - k);

            for (int i = 0; i < termsEndIndex; i++) {
                Term currentTerm = terms[i];
                if (currentTerm.exponent == resultSection.exponent) {
                    int newCoefficient = currentTerm.coefficient - resultSection.coefficient;
                    terms[i] = new Term(newCoefficient, currentTerm.exponent);
                }
            }
            termsEndIndex--;
        }

        boolean isRemainderZero = true;
        List<Integer> remainder = new ArrayList<>();
        for (int i = 0; i <= termsEndIndex; i++) {
            if (terms[i].coefficient != 0) {
                isRemainderZero = false;
            }
            remainder.add(terms[i].coefficient);
        }

        if (isRemainderZero) {
            remainder = new ArrayList<>();
            remainder.add(0);
        }
        return remainder;
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
