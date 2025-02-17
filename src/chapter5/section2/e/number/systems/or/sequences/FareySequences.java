package chapter5.section2.e.number.systems.or.sequences;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 16/02/25.
 */
public class FareySequences {

    private static class Fraction implements Comparable<Fraction> {
        int nominator;
        int denominator;
        double value;

        public Fraction(int nominator, int denominator) {
            this.nominator = nominator;
            this.denominator = denominator;
            value = nominator / (double) denominator;
        }

        @Override
        public int compareTo(Fraction other) {
            return Double.compare(value, other.value);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int n = Integer.parseInt(data[0]);
            int k = Integer.parseInt(data[1]);

            Fraction kThElement = computeKthElement(n, k);
            outputWriter.printLine(kThElement.nominator + "/" + kThElement.denominator);

            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static Fraction computeKthElement(int n, int k) {
        List<Fraction> fractionList = new ArrayList<>();

        for (int nominator = 1; nominator <= n; nominator++) {
            for (int denominator = 1; denominator <= n; denominator++) {
                int gcd = gcd(nominator, denominator);
                if (gcd == 1) {
                    Fraction fraction = new Fraction(nominator, denominator);
                    fractionList.add(fraction);
                }
            }
        }
        Collections.sort(fractionList);
        return fractionList.get(k - 1);
    }

    private static int gcd(int number1, int number2) {
        while (number2 > 0) {
            int temp = number2;
            number2 = number1 % number2;
            number1 = temp;
        }
        return number1;
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
