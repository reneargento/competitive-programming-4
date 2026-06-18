package chapter6.section2.d.output.formatting.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 17/06/2026.
 */
public class ASCIIMandelbrot {

    private static class ComplexNumber {
        double real;
        double imaginary;

        public ComplexNumber(double real, double imaginary) {
            this.real = real;
            this.imaginary = imaginary;
        }
    }

    private static final double EPSILON = .0000000000001;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String line = FastReader.getLine();
            String[] data = line.split(" ");
            String chars = data[0].replace("\"" , "");
            double minImaginary = Double.parseDouble(data[1]);
            double maxImaginary = Double.parseDouble(data[2]);
            double precisionImaginary = Double.parseDouble(data[3]);
            double minReal = Double.parseDouble(data[4]);
            double maxReal = Double.parseDouble(data[5]);
            double precisionReal = Double.parseDouble(data[6]);

            if (t > 0) {
                outputWriter.printLine();
            }
            printRegion(chars, minImaginary, maxImaginary, minReal, maxReal, precisionImaginary, precisionReal, outputWriter);
        }
        outputWriter.flush();
    }

    private static void printRegion(String chars, double minImaginary, double maxImaginary, double minReal,
                                    double maxReal, double precisionImaginary, double precisionReal,
                                    OutputWriter outputWriter) {
        double multiplierImaginary = 0;
        while (true) {
            ComplexNumber complexNumber = new ComplexNumber(minReal, minImaginary);
            complexNumber.imaginary += multiplierImaginary;
            if (complexNumber.imaginary - EPSILON > maxImaginary) {
                return;
            }

            while (true) {
                if (complexNumber.real - EPSILON > maxReal) {
                    outputWriter.printLine();
                    multiplierImaginary += precisionImaginary;
                    break;
                }
                char character = computeChar(chars, complexNumber);
                outputWriter.print(character);

                complexNumber.real += precisionReal;
            }
        }
    }

    private static char computeChar(String chars, ComplexNumber complexNumber) {
        ComplexNumber z = new  ComplexNumber(0, 0);

        for (int i = 0; i <= 12; i++) {
            double magnitude = Math.sqrt(z.real * z.real + z.imaginary * z.imaginary);
            if (magnitude - EPSILON > 2) {
                return chars.charAt(i - 1);
            }
            z = multiply(z, z);
            z = add(z, complexNumber);
        }
        return ' ';
    }

    private static ComplexNumber add(ComplexNumber number1, ComplexNumber number2) {
        return new ComplexNumber(number1.real + number2.real, number1.imaginary + number2.imaginary);
    }

    private static ComplexNumber multiply(ComplexNumber number1, ComplexNumber number2) {
        double real = number1.real * number2.real - number1.imaginary * number2.imaginary;
        double imaginary = number1.real * number2.imaginary + number1.imaginary * number2.real;
        return new ComplexNumber(real, imaginary);
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