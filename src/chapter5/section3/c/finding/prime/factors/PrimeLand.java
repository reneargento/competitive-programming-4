package chapter5.section3.c.finding.prime.factors;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Rene Argento on 08/07/25.
 */
public class PrimeLand {

    private static class Term implements Comparable<Term> {
        long prime;
        int exponent;

        public Term(long prime, int exponent) {
            this.prime = prime;
            this.exponent = exponent;
        }

        @Override
        public int compareTo(Term other) {
            return Long.compare(other.prime, this.prime);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (!line.equals("0")) {
            String[] data = line.split(" ");
            List<Term> minusOneRepresentation = computeMinusOneRepresentation(data);

            outputWriter.print(minusOneRepresentation.get(0).prime + " " + minusOneRepresentation.get(0).exponent);
            for (int i = 1; i < minusOneRepresentation.size(); i++) {
                Term term = minusOneRepresentation.get(i);
                outputWriter.print(" " + term.prime + " " + term.exponent);
            }
            outputWriter.printLine();
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static List<Term> computeMinusOneRepresentation(String[] data) {
        long number = 1;
        for (int i = 0; i < data.length; i += 2) {
            int prime = Integer.parseInt(data[i]);
            int exponent = Integer.parseInt(data[i + 1]);
            number *= (long) Math.pow(prime, exponent);
        }

        number--;
        return primeFactors(number);
    }

    private static List<Term> primeFactors(long number) {
        List<Term> primeFactors = new ArrayList<>();

        for (long i = 2; i * i <= number; i++) {
            boolean hasFactor = false;
            int frequency = 0;

            while (number % i == 0) {
                hasFactor = true;
                frequency++;
                number /= i;
            }

            if (hasFactor) {
                primeFactors.add(new Term(i, frequency));
            }
        }

        if (number > 1) {
            primeFactors.add(new Term(number, 1));
        }
        Collections.sort(primeFactors);
        return primeFactors;
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
