package chapter2.section2.i.big.integer;

import java.io.*;
import java.math.BigInteger;

/**
 * Created by Rene Argento on 14/03/21.
 */
public class AutomorphicNumbers {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String numberString = FastReader.getLine();

        while (numberString != null) {
            BigInteger number = new BigInteger(numberString);
            BigInteger squaredNumber = number.pow(2);

            if (squaredNumber.toString().endsWith(numberString)) {
                int numberOfDigits = numberString.length();
                outputWriter.printLine("Automorphic number of " + numberOfDigits + "-digit.");
            } else {
                outputWriter.printLine("Not an Automorphic number.");
            }
            numberString = FastReader.getLine();
        }
        outputWriter.flush();
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
