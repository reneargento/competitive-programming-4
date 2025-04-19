package chapter5.section2.h.polynomial;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 17/04/25.
 */
public class FourNineEightBis {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            long x = Long.parseLong(line);
            List<String> values = getWords(FastReader.getLine());
            long[] coefficients = new long[values.size()];
            for (int i = 0; i < values.size(); i++) {
                coefficients[i] = Long.parseLong(values.get(i));
            }

            long derivative = computeDerivative(x, coefficients);
            outputWriter.printLine(derivative);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static long computeDerivative(long x, long[] coefficients) {
        long derivative = 0;
        int coefficientIndex = 0;
        for (int i = coefficients.length - 1; i > 0; i--, coefficientIndex++) {
            derivative += coefficients[coefficientIndex] * i * fastExponentiation(x, i - 1);
        }
        return derivative;
    }

    private static long fastExponentiation(long base, long exponent) {
        if (exponent == 0) {
            return 1;
        }

        long baseSquared = base * base;
        long result = fastExponentiation(baseSquared, exponent / 2);
        if (exponent % 2 == 0) {
            return result;
        } else {
            return (base * result);
        }
    }

    private static List<String> getWords(String line) {
        List<String> words = new ArrayList<>();
        StringBuilder word = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char character = line.charAt(i);

            if (character == ' ' || character == '\t') {
                if (word.length() > 0) {
                    words.add(word.toString());
                    word = new StringBuilder();
                }
            } else {
                word.append(character);
            }
        }
        if (word.length() > 0) {
            words.add(word.toString());
        }
        return words;
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
