package chapter5.section2.h.polynomial;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 20/04/25.
 */
public class PolynomialShowdown {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            List<String> coefficients = getWords(line);
            String formattedPolynomial = formatPolynomial(coefficients);
            outputWriter.printLine(formattedPolynomial);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static String formatPolynomial(List<String> coefficients) {
        boolean isZero = true;
        boolean isFirstTerm = true;
        StringBuilder formattedPolynomial = new StringBuilder();

        for (int i = 0; i < coefficients.size(); i++) {
            String coefficient = coefficients.get(i);
            boolean isConstant = i == coefficients.size() - 1;
            int value = Integer.parseInt(coefficient);

            if (value == 0) {
                continue;
            }
            isZero = false;
            boolean isNegative = value < 0;
            int exponent = 8 - i;

            if (isNegative) {
                if (isFirstTerm) {
                    formattedPolynomial.append('-');
                } else {
                    formattedPolynomial.append(" - ");
                }
            } else if (!isFirstTerm) {
                formattedPolynomial.append(" + ");
            }

            int absoluteValue = Math.abs(value);
            if (absoluteValue != 1 || isConstant) {
                formattedPolynomial.append(absoluteValue);
            }

            if (!isConstant) {
                formattedPolynomial.append('x');
            }
            if (exponent > 1) {
                formattedPolynomial.append('^').append(exponent);
            }
            isFirstTerm = false;
        }

        if (isZero) {
            return "0";
        }
        return formattedPolynomial.toString();
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
