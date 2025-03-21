package chapter5.section2.f.logarithm.exponentiation.power;

import java.io.*;

/**
 * Created by Rene Argento on 18/03/25.
 */
// Based on the explanation on https://codingstrife.wordpress.com/2013/07/28/solution-uva701-pc110503-the-archaeologists-dilemma/
public class TheArcheologistsDilemma {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String number = FastReader.getLine();

        while (number != null) {
            int smallestExponent = computeSmallestExponent(number);
            outputWriter.printLine(smallestExponent);
            number = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeSmallestExponent(String number) {
        int minimumLength = number.length() + 1;
        double numberDouble = Double.parseDouble(number);
        double start = 0;
        double end = 0;

        while ((int) start == (int) end) {
            start = log2(numberDouble) + minimumLength * log2(10);
            end = log2(numberDouble + 1) + minimumLength * log2(10);
            minimumLength++;
        }
        return (int) Math.ceil(start);
    }

    private static double log2(double number) {
        return Math.log(number) / Math.log(2);
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
