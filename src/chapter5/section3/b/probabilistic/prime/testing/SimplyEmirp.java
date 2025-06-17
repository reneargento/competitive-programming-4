package chapter5.section3.b.probabilistic.prime.testing;

import java.io.*;
import java.math.BigInteger;

/**
 * Created by Rene Argento on 02/06/25.
 */
public class SimplyEmirp {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String number = FastReader.getLine();
        while (number != null) {
            checkNumber(number, outputWriter);
            number = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static void checkNumber(String number, OutputWriter outputWriter) {
        int certainty = 10;
        outputWriter.print(number + " ");

        BigInteger numberBI = new BigInteger(number);
        if (numberBI.isProbablePrime(certainty)) {
            String reverseNumber = new StringBuilder(number).reverse().toString();
            BigInteger reverseNumberBI = new BigInteger(reverseNumber);

            if (!reverseNumber.equals(number)
                    && reverseNumberBI.isProbablePrime(certainty)) {
                outputWriter.printLine("is emirp.");
            } else {
                outputWriter.printLine("is prime.");
            }
        } else {
            outputWriter.printLine("is not prime.");
        }
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