package chapter6.section2.b.input.parsing.recursive;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 01/05/26.
 */
public class Otpor {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        double[] resistors = new double[FastReader.nextInt()];
        for (int i = 0; i < resistors.length; i++) {
            resistors[i] = FastReader.nextDouble();
        }
        String circuit = FastReader.getLine();
        double resistance = computeResistance(circuit, resistors, 0, circuit.length() - 1);
        outputWriter.printLine(String.format("%.5f", resistance));
        outputWriter.flush();
    }

    private static double computeResistance(String circuit, double[] resistors, int start, int end) {
        double resistance = 0;
        boolean isSeries = true;

        for (int i = start; i <= end; i++) {
            char symbol = circuit.charAt(i);
            if (symbol == '|') {
                if (isSeries) {
                    isSeries = false;
                    resistance = 1 / resistance;
                }
            } else if (symbol == '(') {
                int endSectionIndex = computeEndSectionIndex(circuit, i + 1, end);
                double sectionResistance = computeResistance(circuit, resistors, i + 1, endSectionIndex - 1);
                resistance = updateResistance(resistance, isSeries, sectionResistance);
                i = endSectionIndex;
            } else if (symbol == 'R') {
                int index = Character.getNumericValue(circuit.charAt(i + 1)) - 1;
                resistance = updateResistance(resistance, isSeries, resistors[index]);
                i++;
            }
        }

        if (!isSeries) {
            resistance = 1 / resistance;
        }
        return resistance;
    }

    private static int computeEndSectionIndex(String circuit, int start, int end) {
        int openParenthesis = 1;
        for (int i = start; i <= end; i++) {
            if (circuit.charAt(i) == '(') {
                openParenthesis++;
            } else if (circuit.charAt(i) == ')') {
                openParenthesis--;
                if (openParenthesis == 0) {
                    return i;
                }
            }
        }
        return -1;
    }

    private static double updateResistance(double resistance, boolean isSeries, double resistanceToAdd) {
        if (isSeries) {
            return resistance + resistanceToAdd;
        } else {
            return resistance + (1 / resistanceToAdd);
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

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
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
