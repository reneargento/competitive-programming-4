package chapter5.section3.g.factorial;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 31/08/25.
 */
public class InverseFactorial {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String number = FastReader.next();
        int factorialNumber = computeFactorialNumber(number);
        outputWriter.printLine(factorialNumber);
        outputWriter.flush();
    }

    private static int computeFactorialNumber(String number) {
        if (number.equals("1")) {
            return 1;
        }
        if (number.length() <= 7) {
            long numberLong = Long.parseLong(number);
            long factorial = 1;
            int factorialNumber = 0;

            while (factorial != numberLong) {
                factorialNumber++;
                factorial *= factorialNumber;
            }
            return factorialNumber;
        }

        double log10Sums = 1;
        int factorialNumber = 0;

        while (log10Sums < number.length()) {
            factorialNumber++;
            log10Sums += Math.log10(factorialNumber);
        }
        return factorialNumber;
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
